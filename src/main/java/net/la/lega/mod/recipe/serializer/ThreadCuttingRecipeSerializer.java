package net.la.lega.mod.recipe.serializer;

import net.la.lega.mod.loader.LaLegaLoader;
import net.la.lega.mod.recipe.ThreadCuttingRecipe;
import net.minecraft.util.Identifier;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.la.lega.mod.recipe.BlastChillingRecipe;
import net.la.lega.mod.recipe.abstraction.AbstractMonoRecipe;
import net.la.lega.mod.recipe.jsonformat.AbstractMonoInputRecipeJsonFormat;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.registry.Registry;

public class ThreadCuttingRecipeSerializer implements RecipeSerializer<AbstractMonoRecipe> 
{
    private ThreadCuttingRecipeSerializer() {}
    public static final ThreadCuttingRecipeSerializer INSTANCE = new ThreadCuttingRecipeSerializer();
    public static final Identifier ID = new Identifier(LaLegaLoader.MOD_ID + ":" + ThreadCuttingRecipe.recipeID);

    @Override
    public AbstractMonoRecipe read(Identifier id, JsonObject json) {
        AbstractMonoInputRecipeJsonFormat recipeJson = new Gson().fromJson(json, AbstractMonoInputRecipeJsonFormat.class);

        if (recipeJson.getInput() == null || recipeJson.getOutput() == null) {
            throw new JsonSyntaxException("A required attribute is missing!");
        }
        // We'll allow to not specify the output, and default it to 1.
        if (recipeJson.getOutputAmount() == 0) 
            recipeJson.setOutputAmount(1);
        if(recipeJson.getProcessingTime() == 0)
            recipeJson.setProcessingTime(10);

        // Ingredient easily turns JsonObjects of the correct format into Ingredients
        Ingredient input = Ingredient.fromJson(recipeJson.getInput());
        int processingTime = JsonHelper.getInt(json, "processingTime", 0);
        // The json will specify the item ID. We can get the Item instance based off of that from the Item registry.
        Item outputItem = Registry.ITEM.getOrEmpty( new Identifier(recipeJson.getOutput()) ).orElseThrow( 
            () -> new JsonSyntaxException("No such item " + recipeJson.getOutput())
            );
        ItemStack output = new ItemStack(outputItem, recipeJson.getOutputAmount());

        return new ThreadCuttingRecipe(input, output, processingTime, id);
    }

    @Override
    public AbstractMonoRecipe read(Identifier id, PacketByteBuf buf) 
    {
        // Make sure the read in the same order you have written!
        Ingredient input = Ingredient.fromPacket(buf);
        int processingTime = buf.readVarInt();
        ItemStack output = buf.readItemStack();
        return new BlastChillingRecipe(input, output, processingTime, id);
    }

    @Override
    public void write(PacketByteBuf buf, AbstractMonoRecipe recipe) 
    {
        recipe.getInput().write(buf);
        buf.writeVarInt(recipe.getProcessingTime());
        buf.writeItemStack(recipe.getOutput());

    }
}