package net.la.lega.mod.recipe.serializer;

import java.util.Iterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import net.la.lega.mod.loader.LaLegaLoader;
import net.la.lega.mod.recipe.SushiCraftingRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.registry.Registry;

public class SushiCraftingRecipeSerializer implements RecipeSerializer<SushiCraftingRecipe> 
{
    private SushiCraftingRecipeSerializer() {}
    
    public static final SushiCraftingRecipeSerializer INSTANCE = new SushiCraftingRecipeSerializer();
    public static final Identifier ID = new Identifier(LaLegaLoader.MOD_ID, SushiCraftingRecipe.recipeID);

    @Override
    public SushiCraftingRecipe read(Identifier id, JsonObject json) 
    {
         DefaultedList<Ingredient> defaultedList = getIngredients(JsonHelper.getArray(json, "ingredients"));
         if (defaultedList.isEmpty()) 
         {
            throw new JsonParseException("No ingredients for sushi crafting recipe");
         } 
         else if (defaultedList.size() > 4) 
         {
            throw new JsonParseException("Too many ingredients for sushi crafting recipe");
         } 
         else 
         {
            ItemStack output = getItemStack(json);
            int processingTime = JsonHelper.getInt(json, "processingTime", 10);
            return new SushiCraftingRecipe(defaultedList, output, processingTime, id);
         }
    }

    @Override
    public SushiCraftingRecipe read(Identifier id, PacketByteBuf buf) 
    {
        int inputSize = buf.readVarInt();
        DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(inputSize, Ingredient.EMPTY);

        for(int j = 0; j < defaultedList.size(); ++j) 
        {
            defaultedList.set(j, Ingredient.fromPacket(buf));
        }
        ItemStack itemStack = buf.readItemStack();
        int processingTime = buf.readVarInt();
        return new SushiCraftingRecipe(defaultedList, itemStack, processingTime, id);
    }

    @Override
    public void write(PacketByteBuf buf, SushiCraftingRecipe recipe) 
    {
        buf.writeVarInt(recipe.getInput().size());

        Iterator<Ingredient> var3 = recipe.getInput().iterator();

        while(var3.hasNext()) 
        {
           Ingredient ingredient = (Ingredient)var3.next();
           ingredient.write(buf);
        }
        buf.writeItemStack(recipe.getOutput());
        buf.writeVarInt(recipe.getProcessingTime());
    }

    private static DefaultedList<Ingredient> getIngredients(JsonArray json) 
    {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();

        for(int i = 0; i < json.size(); ++i) {
           Ingredient ingredient = Ingredient.fromJson(json.get(i));
           if (!ingredient.isEmpty()) {
              defaultedList.add(ingredient);
           }
        }

        return defaultedList;
    }

    public static ItemStack getItemStack(JsonObject json) 
    {
        String string = JsonHelper.getString(json, "output");
        Item item = (Item)Registry.ITEM.getOrEmpty(new Identifier(string)).orElseThrow(() -> {
           return new JsonSyntaxException("Unknown item '" + string + "'");
        });
        if (json.has("data")) {
           throw new JsonParseException("Disallowed data tag found");
        } else {
           int i = JsonHelper.getInt(json, "outputAmount", 1);
           return new ItemStack(item, i);
        }
     }
    
}