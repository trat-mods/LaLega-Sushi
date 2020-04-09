package net.la.lega.mod.recipe;

import net.la.lega.mod.recipe.abstraction.AInjectiveProcessingRecipe;
import net.la.lega.mod.recipe.serializer.SteamCookingRecipeSerializer;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;

public class SteamCookingRecipe extends AInjectiveProcessingRecipe
{
    public static final String recipeID = "steam_cooking";
    
    public SteamCookingRecipe(Ingredient input, ItemStack outputStack, int processingTime, Identifier id)
    {
        super(input, outputStack, processingTime, id);
    }
    
    @Override public RecipeSerializer<?> getSerializer()
    {
        return SteamCookingRecipeSerializer.INSTANCE;
    }
    
    @Override public RecipeType<?> getType()
    {
        return SteamCookingRecipe.Type.INSTANCE;
    }
    
    public static class Type implements RecipeType<SteamCookingRecipe>
    {
        private Type()
        {
        }
        
        public static final SteamCookingRecipe.Type INSTANCE = new SteamCookingRecipe.Type();
        public static final String ID = "steam_cooking_recipe";
    }
}
