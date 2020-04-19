package net.la.lega.mod.entity;

import net.la.lega.mod.entity.abstraction.AProcessingEntity;
import net.la.lega.mod.initializer.LEntities;
import net.la.lega.mod.recipe.PressingRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.math.Direction;

public class PressBlockEntity extends AProcessingEntity
{
    public static final int OUTPUT_SLOT = 0;
    public static final int INPUT_SLOT = 1;
    public static final int INPUT2_SLOT = 2;
    
    public PressBlockEntity()
    {
        super(LEntities.PRESS_BLOCK_ENTITY, 3);
    }
    
    @Override public int[] getInvAvailableSlots(Direction side)
    {
        if(side.equals(Direction.DOWN))
        {
            return new int[]{OUTPUT_SLOT};
        }
        else
        {
            return new int[]{INPUT_SLOT, INPUT2_SLOT};
        }
    }
    
    @Override public boolean isValidInvStack(int slot, ItemStack stack)
    {
        return slot != OUTPUT_SLOT;
    }
    
    @Override public boolean canInsertInvStack(int slot, ItemStack stack, Direction dir)
    {
        return isValidInvStack(slot, stack);
    }
    
    @Override public boolean canExtractInvStack(int slot, ItemStack stack, Direction dir)
    {
        return slot == OUTPUT_SLOT && dir == Direction.DOWN;
    }
    
    @Override protected boolean canAcceptRecipeOutput(Recipe<?> recipe)
    {
        PressingRecipe bcRecipe = (PressingRecipe) recipe;
        if(bcRecipe != null)
        {
            ItemStack outputStack = bcRecipe.getOutput();
            if(outputStack.isEmpty())
            {
                return false;
            }
            else
            {
                ItemStack currentOutputStack = (ItemStack) this.items.get(OUTPUT_SLOT);
                if(currentOutputStack.isEmpty())
                {
                    return true;
                }
                else if(!currentOutputStack.isItemEqualIgnoreDamage(outputStack))
                {
                    return false;
                }
                else
                {
                    return currentOutputStack.getCount() + bcRecipe.getOutputAmount() <= this.getInvMaxStackAmount();
                }
            }
        }
        else
        {
            return false;
        }
    }
    
    @Override protected void craftRecipe(Recipe<?> recipe)
    {
        PressingRecipe bcRecipe = (PressingRecipe) recipe;
        if(bcRecipe != null && this.canAcceptRecipeOutput(bcRecipe))
        {
            ItemStack output = bcRecipe.craft(this);
            ItemStack outputSlot = (ItemStack) this.items.get(OUTPUT_SLOT);
            
            if(outputSlot.isEmpty())
            {
                this.items.set(OUTPUT_SLOT, output);
            }
            else if(outputSlot.getItem() == output.getItem())
            {
                outputSlot.increment(bcRecipe.getOutputAmount());
            }
            
            if(!items.get(INPUT_SLOT).isEmpty())
            {
                items.get(INPUT_SLOT).decrement(1);
            }
            if(!items.get(INPUT2_SLOT).isEmpty())
            {
                items.get(INPUT2_SLOT).decrement(1);
            }
        }
    }
    
    @Override public void tick()
    {
        PressingRecipe match = world.getRecipeManager().getFirstMatch(PressingRecipe.Type.INSTANCE, this, world).orElse(null);
        checkCurrentRecipe(match);
        processCurrentRecipe();
    }
}
