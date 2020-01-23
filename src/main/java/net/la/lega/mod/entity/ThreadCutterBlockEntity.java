package net.la.lega.mod.entity;

import net.la.lega.mod.entity.abstraction.AbstractOutputterEntity;
import net.la.lega.mod.loader.LaLegaLoader;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.math.Direction;

public class ThreadCutterBlockEntity extends AbstractOutputterEntity 
{

    public ThreadCutterBlockEntity() 
    {
        super(LaLegaLoader.THREAD_CUTTER_BLOCK_ENTITY, 1);
    }

    @Override
    public void tick() 
    {

    }

    @Override
    public int[] getInvAvailableSlots(Direction side) 
    {
        return null;
    }

    @Override
    public boolean isValidInvStack(int slot, ItemStack stack) 
    {
        return false;
    }

    @Override
    public boolean canInsertInvStack(int slot, ItemStack stack, Direction dir) 
    {
        return false;
    }

    @Override
    public boolean canExtractInvStack(int slot, ItemStack stack, Direction dir) 
    {
        return false;
    }

    @Override
    protected boolean canAcceptRecipeOutput(Recipe<?> recipe) 
    {
        return false;
    }

    @Override
    protected void craftRecipe(Recipe<?> recipe) 
    {

    }
    
}