package net.la.lega.mod.recipe;

import net.la.lega.mod.recipe.serializer.InjectiveProcessingRecipeSerializer;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;


public class InjectiveProcessingRecipe implements Recipe<Inventory> 
{
    public static final String recipeID = "injective_processing";

    private final Ingredient input;
    private final ItemStack outputStack;
    private final int processingTime;
    private final Identifier id;

    public InjectiveProcessingRecipe(Ingredient input, ItemStack outputStack, int processingTime, Identifier id)
    {
        this.input = input;
        this.outputStack = outputStack;
        this.processingTime = processingTime;
        this.id = id;
    }

    public Ingredient getInput() { return input; }
    public int getProcessingTime() { return processingTime; }
    public int getOutputAmount() { return outputStack.getCount(); }

    @Override
    public boolean matches(Inventory inv, World world) 
    {
        if (inv.getInvSize() < 1) return false;
        return input.test(inv.getInvStack(0));
    }

    @Override
    public ItemStack craft(Inventory inv) 
    {
        return this.outputStack.copy();
    }

    @Override
    public boolean fits(int width, int height) 
    {
        return false;
    }

    @Override
    public ItemStack getOutput() 
    {
        return outputStack;
    }

    @Override
    public Identifier getId() 
    {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() 
    {
        return InjectiveProcessingRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() 
    {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<InjectiveProcessingRecipe>
    {
       private Type() {}
       public static final Type INSTANCE = new Type();
       public static final String ID = "injective_processing_recipe";
    }
}