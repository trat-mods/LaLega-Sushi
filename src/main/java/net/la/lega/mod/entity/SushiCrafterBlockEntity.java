package net.la.lega.mod.entity;

import java.util.List;
import java.util.function.Predicate;

import net.la.lega.mod.block.SushiCrafterBlock;
import net.la.lega.mod.entity.abstraction.AbstractProcessingOutputterEntity;
import net.la.lega.mod.item.RiceItem;
import net.la.lega.mod.loader.LaLegaLoader;
import net.la.lega.mod.recipe.SushiCraftingRecipe;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;

public class SushiCrafterBlockEntity extends AbstractProcessingOutputterEntity 
{
    public static final int FISH_SLOT = 0;
    public static final int ING_SLOT = 1;
    public static final int ING2_SLOT = 2;
    public static final int RICE_SLOT = 3;
    public static final int OUTPUT_SLOT = 4;

    private static final int[] TOP_SLOTS = new int[] { 3 };
    private static final int[] BOTTOM_SLOTS = new int[] { 4 };
    private static final int[] SIDE_SLOTS = new int[] { 0, 1, 2 };


    public SushiCrafterBlockEntity() 
    {
        super(LaLegaLoader.SUSHI_CRAFTER_BLOCK_ENTITY, 5);
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
            return stackItem.asItem() instanceof RiceItem;
        }
        else if(slot == FISH_SLOT)
        {
            return stackItem.isIn(LaLegaLoader.SUSHI_FISH);
        }
        else if(slot == ING_SLOT || slot == ING2_SLOT)
        {
            return stackItem.isIn(LaLegaLoader.SUSHI_INGREDIENT);
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean canInsertInvStack(int slot, ItemStack stack, Direction dir) 
    {
        Direction facing = getCachedState().get(SushiCrafterBlock.FACING);
        Item stackItem = stack.getItem();

        if(slot == FISH_SLOT)
        {
            return dir == facing.getOpposite() && stackItem.isIn(LaLegaLoader.SUSHI_FISH);
        }
        else if(slot == RICE_SLOT)
        {
            return dir == Direction.UP && (stackItem.asItem() instanceof RiceItem);
        }
        else if(facing == Direction.NORTH || facing == Direction.SOUTH)
        {
            if(slot == ING_SLOT || slot == ING2_SLOT)
            {
                return (dir == Direction.WEST || dir == Direction.EAST) && stackItem.isIn(LaLegaLoader.SUSHI_INGREDIENT);
            }
        }
        else if(facing == Direction.WEST || facing == Direction.EAST)
        {
            if(slot == ING_SLOT || slot == ING2_SLOT)
            {
                return (dir == Direction.NORTH || dir == Direction.SOUTH) && stackItem.isIn(LaLegaLoader.SUSHI_INGREDIENT);
            }
        }
        return false;
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
        if (bcRecipe != null)
        {
            if(areRequiredSlotNotEmpty())
            {
                ItemStack outputStack = bcRecipe.getOutput();
                if (outputStack.isEmpty()) 
                {
                        System.out.println("Output empty");
                        return false;
                } 
                else
                {
                        ItemStack currentOutputStack = (ItemStack)this.items.get(OUTPUT_SLOT);
                        if (currentOutputStack.isEmpty()) 
                        {
                            return true;
                        } 
                        else if (!currentOutputStack.isItemEqualIgnoreDamage(outputStack)) 
                        {
                            System.out.println("Not equal ignore damage");
                            return false;
                        } 
                        else if (currentOutputStack.getCount() + bcRecipe.getOutputAmount() <= this.getInvMaxStackAmount() && currentOutputStack.getCount() + bcRecipe.getOutputAmount() <= currentOutputStack.getMaxCount()) 
                        {
                            return true;
                        } 
                        else
                        {
                            System.out.println("Returning output < outputcraft");
                            return currentOutputStack.getCount() < outputStack.getMaxCount();
                        }
                    }
            }
            else
            {
                System.out.println("Required Slot empty");
                return false;
            }
        } 
        else 
        {
            System.out.println("recipe null");
            return false;
        }
    }

    @Override
    protected void craftRecipe(Recipe<?> recipe) 
    {
        SushiCraftingRecipe bcRecipe = (SushiCraftingRecipe) recipe;
        if(bcRecipe == null)
        {
            System.out.println("Recipe null");
        }
        if (bcRecipe != null && this.canAcceptRecipeOutput(bcRecipe)) 
        {
            ItemStack ing2Slot = (ItemStack)this.items.get(ING2_SLOT);
            ItemStack ingSlot = (ItemStack)this.items.get(ING_SLOT);
            ItemStack fishSlot = (ItemStack)this.items.get(FISH_SLOT);
            ItemStack riceSlot = (ItemStack)this.items.get(RICE_SLOT);

            ItemStack output = bcRecipe.craft(this);
            ItemStack outputSlot = (ItemStack)this.items.get(OUTPUT_SLOT);

            if (outputSlot.isEmpty()) 
            {
                System.out.println("Setting output");
                this.items.set(OUTPUT_SLOT, output);
            }
            else if (outputSlot.getItem() == output.getItem()) 
            {
                System.out.println("Incrementing 1");
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
        if(!this.world.isClient && isSushiVillagerNear())
        {
            BasicInventory craftingInventory = new BasicInventory(items.get(RICE_SLOT), items.get(FISH_SLOT), items.get(ING_SLOT), items.get(ING2_SLOT));
            SushiCraftingRecipe match = world.getRecipeManager().getFirstMatch(SushiCraftingRecipe.Type.INSTANCE, craftingInventory, world).orElse(null);
            if (!this.isProcessing())
            {
                if(this.canAcceptRecipeOutput(match)) 
                {
                    initializeProcessing(match.getProcessingTime());
                }
            }

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
    }

    private boolean areRequiredSlotNotEmpty()
    {
        return !((ItemStack)items.get(FISH_SLOT)).isEmpty() && !((ItemStack)items.get(RICE_SLOT)).isEmpty();
    }

    private boolean isSushiVillagerNear()
    {
        Box checkBox = new Box(pos);
        checkBox.expand(10);
        List<VillagerEntity> sushiVillagers = world.getEntities(VillagerEntity.class, checkBox, Predicate.isEqual(VillagerEntity.class));
        System.out.println(sushiVillagers.size());
        return sushiVillagers.size() > 0;
    }
}
