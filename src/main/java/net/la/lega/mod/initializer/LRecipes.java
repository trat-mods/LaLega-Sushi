package net.la.lega.mod.initializer;

import net.la.lega.mod.loader.LLoader;
import net.la.lega.mod.recipe.SushiCraftingRecipe;
import net.la.lega.mod.recipe.InjectiveProcessingRecipe;
import net.la.lega.mod.recipe.serializer.InjectiveProcessingRecipeSerializer;
import net.la.lega.mod.recipe.serializer.SushiCraftingRecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public abstract class LRecipes
{
    public static void initialize()
    {
        Registry.register(Registry.RECIPE_SERIALIZER, InjectiveProcessingRecipeSerializer.ID, InjectiveProcessingRecipeSerializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(LLoader.MOD_ID, InjectiveProcessingRecipe.Type.ID), InjectiveProcessingRecipe.Type.INSTANCE);

        Registry.register(Registry.RECIPE_SERIALIZER, SushiCraftingRecipeSerializer.ID, SushiCraftingRecipeSerializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(LLoader.MOD_ID, SushiCraftingRecipe.Type.ID), SushiCraftingRecipe.Type.INSTANCE);
    }
}