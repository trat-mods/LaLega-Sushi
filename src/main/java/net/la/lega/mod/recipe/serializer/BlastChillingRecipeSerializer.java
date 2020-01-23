package net.la.lega.mod.recipe.serializer;

import net.la.lega.mod.loader.LaLegaLoader;
import net.la.lega.mod.recipe.BlastChillingRecipe;
import net.la.lega.mod.recipe.serializer.abstraction.AbstractMonoRecipeSerializer;
import net.minecraft.util.Identifier;

public class BlastChillingRecipeSerializer extends AbstractMonoRecipeSerializer 
{
    private BlastChillingRecipeSerializer() {}
    public static final BlastChillingRecipeSerializer INSTANCE = new BlastChillingRecipeSerializer();
    public static final Identifier ID = new Identifier(LaLegaLoader.MOD_ID + ":" + BlastChillingRecipe.recipeID);
}