package net.la.lega.mod.recipe.serializer;

import net.la.lega.mod.loader.LaLegaLoader;
import net.la.lega.mod.recipe.ThreadCuttingRecipe;
import net.la.lega.mod.recipe.serializer.abstraction.AbstractMonoRecipeSerializer;
import net.minecraft.util.Identifier;

public class ThreadCuttingRecipeSerializer extends AbstractMonoRecipeSerializer
{
    private ThreadCuttingRecipeSerializer() {}
    public static final ThreadCuttingRecipeSerializer INSTANCE = new ThreadCuttingRecipeSerializer();
    public static final Identifier ID = new Identifier(LaLegaLoader.MOD_ID + ":" + ThreadCuttingRecipe.recipeID);
}