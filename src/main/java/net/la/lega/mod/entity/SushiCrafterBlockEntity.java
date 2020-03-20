package net.la.lega.mod.entity;

import net.la.lega.mod.entity.abstraction.AbstractProcessingOutputterEntity;
import net.la.lega.mod.initializer.LEntities;
import net.la.lega.mod.initializer.LTags;
import net.la.lega.mod.initializer.LVillagerProfessions;
import net.la.lega.mod.recipe.SushiCraftingRecipe;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;

import java.util.Arrays;
import java.util.List;

public class SushiCrafterBlockEntity extends AbstractProcessingOutputterEntity
{
    public static final int OUTPUT_SLOT = 0;
    public static final int[] REQUIRED_SLOTS = new int[]{1, 2};
    public static final int[] INGREDIENTS_SLOTS = new int[]{3, 4, 5, 6, 7};
    
    private boolean isRequiredSlot(int slot)
    {
        return Arrays.stream(REQUIRED_SLOTS).anyMatch(p -> p == slot);
    }
    
    private boolean isRequiredTypeItem(Item item)
    {
        return item.isIn(LTags.SUSHI_REQUIRED);
    }
    
    private boolean isItemAlreadyPresent(Item item, int interestedSlot)
    {
        for(int i = 0; i < REQUIRED_SLOTS.length; i++)
        {
            if(i != interestedSlot && item == items.get(i).getItem())
            {
                return true;
            }
        }
        for(int i = 0; i < INGREDIENTS_SLOTS.length; i++)
        {
            if(i != interestedSlot && item == items.get(i).getItem())
            {
                return true;
            }
        }
        return false;
    }
    
    private boolean isIngredientSlot(int slot)
    {
        return Arrays.stream(INGREDIENTS_SLOTS).anyMatch(p -> p == slot);
    }
    
    private boolean isIngredient(Item item)
    {
        return item.isIn(LTags.SUSHI_INGREDIENT);
    }
    
    private int[] TOP_SLOTS;
    private int[] BOTTOM_SLOTS = new int[]{OUTPUT_SLOT};
    private int[] SIDE_SLOTS;
    
    private SushiCraftingRecipe currentRecipe;
    
    public SushiCrafterBlockEntity()
    {
        super(LEntities.SUSHI_CRAFTER_BLOCK_ENTITY, 8);
        calculateSlots();
    }
    
    private void calculateSlots()
    {
        TOP_SLOTS = Arrays.copyOf(REQUIRED_SLOTS, REQUIRED_SLOTS.length + INGREDIENTS_SLOTS.length);
        System.arraycopy(INGREDIENTS_SLOTS, 0, TOP_SLOTS, REQUIRED_SLOTS.length, INGREDIENTS_SLOTS.length);
        SIDE_SLOTS = TOP_SLOTS;
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
        else if(isRequiredSlot(slot) && isRequiredTypeItem(stackItem))
        {
            return !isItemAlreadyPresent(stackItem, slot);
        }
        else if(isIngredientSlot(slot) && isIngredient(stackItem))
        {
            return !isItemAlreadyPresent(stackItem, slot);
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
                else if(currentOutputStack.getCount() + bcRecipe.getOutputAmount() <= this.getInvMaxStackAmount())
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
            
            for(int i = 0; i < INGREDIENTS_SLOTS.length; i++)
            {
                ItemStack current = items.get(INGREDIENTS_SLOTS[i]);
                if(!current.isEmpty())
                {
                    current.decrement(1);
                }
            }
            for(int i = 0; i < REQUIRED_SLOTS.length; i++)
            {
                ItemStack current = items.get(REQUIRED_SLOTS[i]);
                if(!current.isEmpty())
                {
                    current.decrement(1);
                }
            }
        }
    }
    
    @Override
    public void tick()
    {
        if(!this.world.isClient)
        {
            System.out.println(getCurrentProcessingTime() + ", " + getCurrentUnitProcessingTime());
            if(isSushiManNear())
            {
                SushiCraftingRecipe match = world.getRecipeManager().getFirstMatch(SushiCraftingRecipe.Type.INSTANCE, calculateCurrentInventory(), world).orElse(null);
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
        }
    }
    
    private BasicInventory calculateCurrentInventory()
    {
        BasicInventory inv = new BasicInventory(items.size() - 1);
        for(int i = 0; i < REQUIRED_SLOTS.length; i++)
        {
            inv.add(items.get(REQUIRED_SLOTS[i]));
        }
        for(int i = 0; i < INGREDIENTS_SLOTS.length; i++)
        {
            inv.add(items.get(INGREDIENTS_SLOTS[i]));
        }
        return inv;
    }
    
    private boolean areRequiredSlotNotEmpty()
    {
        for(int i = 0; i < REQUIRED_SLOTS.length; i++)
        {
            if(items.get(REQUIRED_SLOTS[i]).isEmpty())
            {
                return false;
            }
        }
        return true;
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
