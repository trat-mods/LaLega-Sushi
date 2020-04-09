package net.la.lega.mod.recipe.serializer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.la.lega.mod.loader.LLoader;
import net.la.lega.mod.recipe.SteamCookingRecipe;
import net.la.lega.mod.recipe.jsonformat.InjectiveProcessingRecipeJsonFormat;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.registry.Registry;

public class SteamCookingRecipeSerializer implements RecipeSerializer<SteamCookingRecipe>
{
    public static final SteamCookingRecipeSerializer INSTANCE = new SteamCookingRecipeSerializer();
    public static final Identifier ID = new Identifier(LLoader.MOD_ID, SteamCookingRecipe.recipeID);
    
    @Override public SteamCookingRecipe read(Identifier id, JsonObject json)
    {
        InjectiveProcessingRecipeJsonFormat recipeJson = new Gson().fromJson(json, InjectiveProcessingRecipeJsonFormat.class);
        
        if(recipeJson.getInput() == null || recipeJson.getOutput() == null)
        {
            throw new JsonSyntaxException("A required attribute is missing!");
        }
        
        if(recipeJson.getProcessingTime() == 0)
            recipeJson.setProcessingTime(10);
        
        // Ingredient easily turns JsonObjects of the correct format into Ingredients
        Ingredient input = Ingredient.fromJson(recipeJson.getInput());
        int processingTime = JsonHelper.getInt(json, "processingTime", 10);
        
        if(recipeJson.getOutputAmount() == 0)
            recipeJson.setOutputAmount(1);
        if(processingTime == 0)
            recipeJson.setProcessingTime(10);
        // The json will specify the item ID. We can get the Item instance based off of that from the Item registry.
        Item outputItem = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.getOutput())).orElseThrow(
              () -> new JsonSyntaxException("No such item " + recipeJson.getOutput())
                                                                                                      );
        
        ItemStack output = new ItemStack(outputItem, recipeJson.getOutputAmount());
        
        return new SteamCookingRecipe(input, output, processingTime, id);
    }
    
    @Override public SteamCookingRecipe read(Identifier id, PacketByteBuf buf)
    {
        // Make sure the read in the same order you have written!
        Ingredient input = Ingredient.fromPacket(buf);
        int processingTime = buf.readVarInt();
        ItemStack output = buf.readItemStack();
        return new SteamCookingRecipe(input, output, processingTime, id);
    }
    
    @Override public void write(PacketByteBuf buf, SteamCookingRecipe recipe)
    {
        recipe.getInput().write(buf);
        buf.writeVarInt(recipe.getProcessingTime());
        buf.writeItemStack(recipe.getOutput());
    }
}
