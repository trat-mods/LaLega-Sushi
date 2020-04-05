package net.la.lega.mod.recipe;

import net.la.lega.mod.recipe.abstraction.AInjectiveProcessingRecipe;
import net.la.lega.mod.recipe.serializer.FryingRecipeSerializer;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;

public class FryingRecipe extends AInjectiveProcessingRecipe
{
    public static final String recipeID = "frying";
    
    public FryingRecipe(Ingredient input, ItemStack outputStack, int processingTime, Identifier id)
    {
        super(input, outputStack, processingTime, id);
    }
    
    @Override public RecipeSerializer<?> getSerializer()
    {
        return FryingRecipeSerializer.INSTANCE;
    }
    
    @Override
    public RecipeType<?> getType()
    {
        return FryingRecipe.Type.INSTANCE;
    }
    
    public static class Type implements RecipeType<FryingRecipe>
    {
        private Type()
        {
        }
        
        public static final FryingRecipe.Type INSTANCE = new FryingRecipe.Type();
        public static final String ID = "frying_recipe";
    }
}
