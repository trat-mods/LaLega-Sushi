package net.la.lega.mod.recipe.abstraction;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;


public abstract class AbstractMonoRecipe implements Recipe<Inventory> 
{
    private final Ingredient input;
    private final ItemStack outputStack;
    private final int processingTime;
    private final Identifier id;

    public AbstractMonoRecipe(Ingredient input, ItemStack outputStack, int processingTime, Identifier id)
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

    public abstract RecipeSerializer<?> getSerializer();

    public abstract RecipeType<?> getType();
}