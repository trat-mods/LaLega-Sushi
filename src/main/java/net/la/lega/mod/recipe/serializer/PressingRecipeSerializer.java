package net.la.lega.mod.recipe.serializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.la.lega.mod.loader.LLoader;
import net.la.lega.mod.recipe.PressingRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.registry.Registry;

import java.util.Iterator;

public class PressingRecipeSerializer implements RecipeSerializer<PressingRecipe>
{
    private PressingRecipeSerializer()
    {
    }
    
    public static final PressingRecipeSerializer INSTANCE = new PressingRecipeSerializer();
    public static final Identifier ID = new Identifier(LLoader.MOD_ID, PressingRecipe.recipeID);
    
    @Override
    public PressingRecipe read(Identifier id, JsonObject json)
    {
        DefaultedList<Ingredient> defaultedList = getIngredients(JsonHelper.getArray(json, "ingredients"));
        if(defaultedList.isEmpty())
        {
            throw new JsonParseException("No ingredients for sushi crafting recipe");
        }
        else if(defaultedList.size() > 2)
        {
            throw new JsonParseException("Too many ingredients for sushi crafting recipe");
        }
        else
        {
            ItemStack output = getItemStack(json);
            int processingTime = JsonHelper.getInt(json, "processingTime", 10);
            return new PressingRecipe(defaultedList, output, processingTime, id);
        }
    }
    
    @Override
    public PressingRecipe read(Identifier id, PacketByteBuf buf)
    {
        int inputSize = buf.readVarInt();
        DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(inputSize, Ingredient.EMPTY);
        
        for(int j = 0; j < defaultedList.size(); ++j)
        {
            defaultedList.set(j, Ingredient.fromPacket(buf));
        }
        ItemStack itemStack = buf.readItemStack();
        int processingTime = buf.readVarInt();
        return new PressingRecipe(defaultedList, itemStack, processingTime, id);
    }
    
    @Override
    public void write(PacketByteBuf buf, PressingRecipe recipe)
    {
        buf.writeVarInt(recipe.getPreviewInputs().size());
        
        Iterator<Ingredient> var3 = recipe.getPreviewInputs().iterator();
        
        while(var3.hasNext())
        {
            Ingredient ingredient = (Ingredient) var3.next();
            ingredient.write(buf);
        }
        buf.writeItemStack(recipe.getOutput());
        buf.writeVarInt(recipe.getProcessingTime());
    }
    
    private static DefaultedList<Ingredient> getIngredients(JsonArray json)
    {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        
        for(int i = 0; i < json.size(); ++i)
        {
            Ingredient ingredient = Ingredient.fromJson(json.get(i));
            if(!ingredient.isEmpty())
            {
                defaultedList.add(ingredient);
            }
        }
        
        return defaultedList;
    }
    
    public static ItemStack getItemStack(JsonObject json)
    {
        String string = JsonHelper.getString(json, "output");
        Item item = (Item) Registry.ITEM.getOrEmpty(new Identifier(string)).orElseThrow(() ->
        {
            return new JsonSyntaxException("Unknown item '" + string + "'");
        });
        if(json.has("data"))
        {
            throw new JsonParseException("Disallowed data tag found");
        }
        else
        {
            int i = JsonHelper.getInt(json, "outputAmount", 1);
            return new ItemStack(item, i);
        }
    }
}