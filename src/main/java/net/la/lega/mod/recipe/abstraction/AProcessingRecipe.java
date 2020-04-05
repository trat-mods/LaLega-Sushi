package net.la.lega.mod.recipe.abstraction;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;

public abstract class AProcessingRecipe implements Recipe<Inventory>
{
    private final int processingTime;
    private final ItemStack outputStack;
    private final Identifier id;
    
    public AProcessingRecipe(ItemStack outputStack, int processingTime, Identifier id)
    {
        this.outputStack = outputStack;
        this.processingTime = processingTime;
        this.id = id;
    }
    
    public int getProcessingTime()
    {
        return processingTime;
    }
    
    @Override
    public ItemStack getOutput()
    {
        return outputStack;
    }
    
    @Override
    public ItemStack craft(Inventory inv)
    {
        return this.outputStack.copy();
    }
    
    @Override
    public Identifier getId()
    {
        return id;
    }
    
    public int getOutputAmount()
    {
        return outputStack.getCount();
    }
    
    @Override
    public abstract RecipeSerializer<?> getSerializer();
    
    @Override
    public abstract RecipeType<?> getType();
}
