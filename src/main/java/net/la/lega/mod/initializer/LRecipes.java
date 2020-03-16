package net.la.lega.mod.initializer;

import net.la.lega.mod.loader.LLoader;
import net.la.lega.mod.recipe.BlastChillingRecipe;
import net.la.lega.mod.recipe.SushiCraftingRecipe;
import net.la.lega.mod.recipe.ThreadCuttingRecipe;
import net.la.lega.mod.recipe.serializer.BlastChillingRecipeSerializer;
import net.la.lega.mod.recipe.serializer.SushiCraftingRecipeSerializer;
import net.la.lega.mod.recipe.serializer.ThreadCuttingRecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public abstract class LRecipes
{
    public static void initialize()
    {
        Registry.register(Registry.RECIPE_SERIALIZER, BlastChillingRecipeSerializer.ID, BlastChillingRecipeSerializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(LLoader.MOD_ID, BlastChillingRecipe.Type.ID), BlastChillingRecipe.Type.INSTANCE);
        
        Registry.register(Registry.RECIPE_SERIALIZER, ThreadCuttingRecipeSerializer.ID, ThreadCuttingRecipeSerializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(LLoader.MOD_ID, ThreadCuttingRecipe.Type.ID), ThreadCuttingRecipe.Type.INSTANCE);
        
        Registry.register(Registry.RECIPE_SERIALIZER, SushiCraftingRecipeSerializer.ID, SushiCraftingRecipeSerializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(LLoader.MOD_ID, SushiCraftingRecipe.Type.ID), SushiCraftingRecipe.Type.INSTANCE);
    }
}