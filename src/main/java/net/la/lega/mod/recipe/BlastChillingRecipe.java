package net.la.lega.mod.recipe;

import net.la.lega.mod.recipe.serializer.BlastChillingRecipeSerializer;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;

public class BlastChillingRecipe extends InjectiveProcessingRecipe
{
    public static final String recipeID = "blast_chilling";
    
    public BlastChillingRecipe(Ingredient input, ItemStack outputStack, int processingTime, Identifier id)
    {
        super(input, outputStack, processingTime, id);
    }
    
    @Override public RecipeSerializer<?> getSerializer()
    {
        return BlastChillingRecipeSerializer.INSTANCE;
    }
    
    @Override public RecipeType<?> getType()
    {
        return Type.INSTANCE;
    }
    
    public static class Type implements RecipeType<BlastChillingRecipe>
    {
        private Type()
        {
        }
        
        public static final BlastChillingRecipe.Type INSTANCE = new BlastChillingRecipe.Type();
        public static final String ID = "blast_chilling_recipe";
    }
}