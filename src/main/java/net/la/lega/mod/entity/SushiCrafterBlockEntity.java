package net.la.lega.mod.entity;

import net.la.lega.mod.entity.abstraction.AbstractProcessingOutputterEntity;
import net.la.lega.mod.initializer.LEntities;
import net.la.lega.mod.initializer.LTags;
import net.la.lega.mod.initializer.LVillagerProfessions;
import net.la.lega.mod.item.Rice;
import net.la.lega.mod.recipe.SushiCraftingRecipe;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;

import java.util.List;

public class SushiCrafterBlockEntity extends AbstractProcessingOutputterEntity
{
    public static final int FISH_SLOT = 0;
    public static final int ING_SLOT = 1;
    public static final int ING2_SLOT = 2;
    public static final int RICE_SLOT = 3;
    public static final int OUTPUT_SLOT = 4;
    
    private static final int[] TOP_SLOTS = new int[]{FISH_SLOT, ING_SLOT, ING2_SLOT, RICE_SLOT};
    private static final int[] BOTTOM_SLOTS = new int[]{OUTPUT_SLOT};
    private static final int[] SIDE_SLOTS = new int[]{FISH_SLOT, ING_SLOT, ING2_SLOT, RICE_SLOT};
    
    private SushiCraftingRecipe currentRecipe;
    
    public SushiCrafterBlockEntity()
    {
        super(LEntities.SUSHI_CRAFTER_BLOCK_ENTITY, 5);
    }
    
    @Override
    public int[] getInvAvailableSlots(Direction side)
    {
        switch(side)
        {
            case UP:
                return TOP_SLOTS;
            case DOWN:
                return BOTTOM_SLOTS;
            default:
                return SIDE_SLOTS;
        }
    }
    
    @Override
    public boolean isValidInvStack(int slot, ItemStack stack)
    {
        Item stackItem = stack.getItem();
        if(slot == OUTPUT_SLOT)
        {
            return false;
        }
        else if(slot == RICE_SLOT)
        {
            return stackItem.asItem() instanceof Rice;
        }
        else if(slot == FISH_SLOT)
        {
            return stackItem.isIn(LTags.SUSHI_FISH);
        }
        if(slot == ING_SLOT)
        {
            return stackItem.isIn(LTags.SUSHI_INGREDIENT) && (items.get(ING2_SLOT).isEmpty() || items.get(ING2_SLOT).getItem() != stackItem);
        }
        else if(slot == ING2_SLOT)
        {
            return stackItem.isIn(LTags.SUSHI_INGREDIENT) && (items.get(ING_SLOT).isEmpty() || items.get(ING_SLOT).getItem() != stackItem);
        }
        else
        {
            return false;
        }
    }
    
    @Override
    public boolean canInsertInvStack(int slot, ItemStack stack, Direction dir)
    {
        return slot != OUTPUT_SLOT;
    }
    
    @Override
    public boolean canExtractInvStack(int slot, ItemStack stack, Direction dir)
    {
        return slot == OUTPUT_SLOT && dir == Direction.DOWN;
    }
    
    @Override
    protected boolean canAcceptRecipeOutput(Recipe<?> recipe)
    {
        SushiCraftingRecipe bcRecipe = (SushiCraftingRecipe) recipe;
        if(bcRecipe != null && areRequiredSlotNotEmpty())
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
                else if(currentOutputStack.getCount() + bcRecipe.getOutputAmount() <= this.getInvMaxStackAmount() && currentOutputStack.getCount() + bcRecipe.getOutputAmount() <= currentOutputStack.getMaxCount())
                {
                    return true;
                }
                else
                {
                    return currentOutputStack.getCount() < outputStack.getMaxCount();
                }
            }
        }
        else
        {
            return false;
        }
    }
    
    @Override
    protected void craftRecipe(Recipe<?> recipe)
    {
        SushiCraftingRecipe bcRecipe = (SushiCraftingRecipe) recipe;
        if(bcRecipe != null && this.canAcceptRecipeOutput(bcRecipe))
        {
            ItemStack ing2Slot = (ItemStack) this.items.get(ING2_SLOT);
            ItemStack ingSlot = (ItemStack) this.items.get(ING_SLOT);
            ItemStack fishSlot = (ItemStack) this.items.get(FISH_SLOT);
            ItemStack riceSlot = (ItemStack) this.items.get(RICE_SLOT);
            
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
            if(!ingSlot.isEmpty())
            {
                ingSlot.decrement(1);
            }
            if(!ing2Slot.isEmpty())
            {
                ing2Slot.decrement(1);
            }
            fishSlot.decrement(1);
            riceSlot.decrement(1);
        }
    }
    
    @Override
    public void tick()
    {
        if(!this.world.isClient)
        {
            if(isSushiManNear())
            {
                BasicInventory craftingInventory = new BasicInventory(items.get(FISH_SLOT), items.get(ING_SLOT), items.get(ING2_SLOT), items.get(RICE_SLOT));
                SushiCraftingRecipe match = world.getRecipeManager().getFirstMatch(SushiCraftingRecipe.Type.INSTANCE, craftingInventory, world).orElse(null);
                if(currentRecipe == null)
                {
                    currentRecipe = match;
                }
                else
                {
                    if(currentRecipe != match)
                    {
                        resetProcessing();
                        currentRecipe = match;
                    }
                }
                if(!this.isProcessing())
                {
                    if(this.canAcceptRecipeOutput(currentRecipe))
                    {
                        initializeProcessing(currentRecipe.getProcessingTime());
                    }
                }
                
                if(this.isProcessing())
                {
                    processStep();
                    if(isProcessingCompleted())
                    {
                        this.craftRecipe(currentRecipe);
                        resetProcessing();
                    }
                }
            }
            else
            {
                resetProcessing();
            }
        }
    }
    
    private boolean areRequiredSlotNotEmpty()
    {
        return !((ItemStack) items.get(FISH_SLOT)).isEmpty() && !((ItemStack) items.get(RICE_SLOT)).isEmpty();
    }
    
    private boolean isSushiManNear()
    {
        List<VillagerEntity> villagers = world.getNonSpectatingEntities(VillagerEntity.class, (new Box(getPos())).expand(3D, 3D, 3D));
        for(VillagerEntity villager : villagers)
        {
            if(villager.getVillagerData().getProfession() == LVillagerProfessions.SUSHI_MAN_PROFESSION)
            {
                return true;
            }
        }
        return false;
    }
}
