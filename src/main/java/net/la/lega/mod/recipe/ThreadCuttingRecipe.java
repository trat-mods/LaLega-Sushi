package net.la.lega.mod.recipe;

import net.la.lega.mod.recipe.abstraction.AbstractMonoRecipe;
import net.la.lega.mod.recipe.serializer.ThreadCuttingRecipeSerializer;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;

public class ThreadCuttingRecipe extends AbstractMonoRecipe 
{
    public static final String recipeID = "thread_cutting";

    public ThreadCuttingRecipe(Ingredient input, ItemStack outputStack, int processingTime, Identifier id) 
    {
        super(input, outputStack, processingTime, id);
    }

    @Override
    public RecipeSerializer<?> getSerializer() 
    {
        return ThreadCuttingRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() 
    {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<ThreadCuttingRecipe>
    {
       private Type() {}
       public static final Type INSTANCE = new Type();
       public static final String ID = "thread_cutting_recipe";
    }
}