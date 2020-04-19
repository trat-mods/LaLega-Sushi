package net.la.lega.mod.initializer;

import net.la.lega.mod.loader.LLoader;
import net.la.lega.mod.recipe.*;
import net.la.lega.mod.recipe.serializer.*;
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
        
        Registry.register(Registry.RECIPE_SERIALIZER, FryingRecipeSerializer.ID, FryingRecipeSerializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(LLoader.MOD_ID, FryingRecipe.Type.ID), FryingRecipe.Type.INSTANCE);
        
        Registry.register(Registry.RECIPE_SERIALIZER, SteamCookingRecipeSerializer.ID, SteamCookingRecipeSerializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(LLoader.MOD_ID, SteamCookingRecipe.Type.ID), SteamCookingRecipe.Type.INSTANCE);
        
        Registry.register(Registry.RECIPE_SERIALIZER, PressingRecipeSerializer.ID, PressingRecipeSerializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(LLoader.MOD_ID, PressingRecipe.Type.ID), PressingRecipe.Type.INSTANCE);
    }
}