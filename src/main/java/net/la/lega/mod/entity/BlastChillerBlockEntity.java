package net.la.lega.mod.entity;

import blue.endless.jankson.annotation.Nullable;
import net.la.lega.mod.block.BlastChillerBlock;
import net.la.lega.mod.entity.abstraction.AbstractProcessingOutputterEntity;
import net.la.lega.mod.loader.LaLegaLoader;
import net.la.lega.mod.recipe.BlastChillingRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.math.Direction;

public class BlastChillerBlockEntity extends AbstractProcessingOutputterEntity
{
    private static final int[] TOP_SLOTS = new int[] { 0 };
    private static final int[] BOTTOM_SLOTS = new int[] { 1 };
    private static final int[] SIDE_SLOTS = new int[] { 0 };

    public BlastChillerBlockEntity() 
    {
        super(LaLegaLoader.BLAST_CHILLER_BLOCK_ENTITY, 2);
    }

    @Override
    public int[] getInvAvailableSlots(Direction side)
    {
        if(side == Direction.DOWN)
        {
            return BOTTOM_SLOTS;
        }
        else 
        {
            return side == Direction.UP ? TOP_SLOTS : SIDE_SLOTS;
        }
    }

    @Override
    public boolean isValidInvStack(int slot, ItemStack stack) 
    {
        return slot != 1;
    }

    @Override
    public boolean canInsertInvStack(int slot, ItemStack stack, Direction dir) 
    {
        return dir != Direction.DOWN && this.isValidInvStack(slot, stack);
    }

    @Override
    public boolean canExtractInvStack(int slot, ItemStack stack, Direction dir) 
    {
        if(slot == 1)
        {
            return (dir == Direction.DOWN);
        }
        return false;
    }

    @Override
    public void setInvStack(int slot, ItemStack stack) 
    {
        super.setInvStack(slot, stack);
        ItemStack itemStack = (ItemStack)this.getItems().get(slot);
        boolean bl = !stack.isEmpty() && stack.isItemEqualIgnoreDamage(itemStack) && ItemStack.areTagsEqual(stack, itemStack);
        if (slot == 0 && !bl) 
        {
            resetProcessing();
            this.markDirty();
         }
    }

    @Override
    public void tick() 
    {
        if(!this.world.isClient)
        {
            BlastChillingRecipe match = world.getRecipeManager().getFirstMatch(BlastChillingRecipe.Type.INSTANCE, this, world).orElse(null);
            if (!this.isProcessing())
            {
                if(this.canAcceptRecipeOutput(match)) 
                {
                    initializeProcessing(match.getProcessingTime());
                }
            }

            this.world.setBlockState(this.pos, (BlockState)this.world.getBlockState(this.pos).with(BlastChillerBlock.ON, isProcessing()), 3);

            if(this.isProcessing())
            {
                processStep();

                if(isProcessingCompleted())
                {
                    this.craftRecipe(match);
                    resetProcessing();
                }
            }
        }
        this.markDirty();
    }

    @Override
    protected boolean canAcceptRecipeOutput(Recipe<?> recipe) 
    {
        BlastChillingRecipe bcRecipe = (BlastChillingRecipe) recipe;
        if (!((ItemStack)this.items.get(0)).isEmpty() && recipe != null) 
        {
           ItemStack itemStack = bcRecipe.getOutput();
           if (itemStack.isEmpty()) 
           {
                return false;
           } 
           else
           {
                ItemStack itemStack2 = (ItemStack)this.items.get(1);
                if (itemStack2.isEmpty()) 
                {
                    return true;
                } 
                else if (!itemStack2.isItemEqualIgnoreDamage(itemStack)) 
                {
                    return false;
                } 
                else if (itemStack2.getCount() + bcRecipe.getOutputAmount() <= this.getInvMaxStackAmount() && itemStack2.getCount() + bcRecipe.getOutputAmount() <= itemStack2.getMaxCount()) 
                {
                    return true;
                } 
                else
                {
                    return itemStack2.getCount() < itemStack.getMaxCount();
                }
            }
        } 
        else 
        {
           return false;
        }
    }

    @Override
    protected void craftRecipe(@Nullable Recipe<?> recipe) 
    {
        BlastChillingRecipe bcRecipe = (BlastChillingRecipe) recipe;
        if (recipe != null && this.canAcceptRecipeOutput(recipe)) 
        {
           ItemStack inputSlot = (ItemStack)this.items.get(0);
           ItemStack output = bcRecipe.getOutput().copy();
           ItemStack outputSlot = (ItemStack)this.items.get(1);
           if (outputSlot.isEmpty()) 
           {
              this.items.set(1, output.copy());
           }
           else if (outputSlot.getItem() == output.getItem()) 
           {
              outputSlot.increment(bcRecipe.getOutputAmount());
           }
           inputSlot.decrement(1);
        }
    }

}