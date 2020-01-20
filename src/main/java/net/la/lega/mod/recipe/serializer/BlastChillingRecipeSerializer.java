package net.la.lega.mod.recipe.serializer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.la.lega.mod.recipe.BlastChillingRecipe;
import net.la.lega.mod.recipe.jsonformat.BlastChillingRecipeJsonFormat;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.registry.Registry;


public class BlastChillingRecipeSerializer implements RecipeSerializer<BlastChillingRecipe> 
{

    private BlastChillingRecipeSerializer() {}

    public static final BlastChillingRecipeSerializer INSTANCE = new BlastChillingRecipeSerializer();

    public static final Identifier ID = new Identifier("lalegamod:blast_chilling");

    @Override
    public BlastChillingRecipe read(Identifier id, JsonObject json)
    {
        BlastChillingRecipeJsonFormat recipeJson = new Gson().fromJson(json, BlastChillingRecipeJsonFormat.class);

        if (recipeJson.getInput() == null || recipeJson.getOutput() == null) {
            throw new JsonSyntaxException("A required attribute is missing!");
        }
        // We'll allow to not specify the output, and default it to 1.
        if (recipeJson.getOutputAmount() == 0) 
            recipeJson.setOutputAmount(1);
        if(recipeJson.getChillTime() == 0)
            recipeJson.setChillTime(10);

        // Ingredient easily turns JsonObjects of the correct format into Ingredients
        Ingredient input = Ingredient.fromJson(recipeJson.getInput());
        int chillTime = JsonHelper.getInt(json, "chilltime", 40);
        // The json will specify the item ID. We can get the Item instance based off of that from the Item registry.
        Item outputItem = Registry.ITEM.getOrEmpty( new Identifier(recipeJson.getOutput()) ).orElseThrow( 
            () -> new JsonSyntaxException("No such item " + recipeJson.getOutput())
            );
        ItemStack output = new ItemStack(outputItem, recipeJson.getOutputAmount());

        return new BlastChillingRecipe(input, output, chillTime, id);
    }

    @Override
    public BlastChillingRecipe read(Identifier id, PacketByteBuf buf)
    {
        // Make sure the read in the same order you have written!
        Ingredient input = Ingredient.fromPacket(buf);
        int chillTime = buf.readVarInt();
        ItemStack output = buf.readItemStack();
        return new BlastChillingRecipe(input, output, chillTime, id);
    }

    @Override
    public void write(PacketByteBuf buf, BlastChillingRecipe recipe)
    {
        recipe.getInput().write(buf);
        buf.writeVarInt(recipe.getChillTime());
        buf.writeItemStack(recipe.getOutput());
    }
}