package net.la.lega.mod.entity;

import io.github.cottonmc.cotton.gui.PropertyDelegateHolder;
import net.la.lega.mod.api.LimitedQueue;
import net.la.lega.mod.api.ProcessableRecipeObject;
import net.la.lega.mod.block.SteamCookerBlock;
import net.la.lega.mod.entity.abstraction.AInventoryEntity;
import net.la.lega.mod.initializer.LEntities;
import net.la.lega.mod.recipe.SteamCookingRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.container.PropertyDelegate;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;

public class SteamCookerBlockEntity extends AInventoryEntity implements PropertyDelegateHolder, Tickable
{
    public static final int OUTPUT_SLOT = 0;
    public static final int PROCESSING_SLOT = 1;
    public static final int INPUT_SLOT = 2;
    public static final int MAX_WATER_LEVEL = 160;
    
    public static final int CURRENT_WATER_LEVEL = 0;
    public static final int MAX_LEVEL = 1;
    private int currentWaterLevel;
    private int maxWaterLevel;
    private int inverseWaterLevel;
    
    private LimitedQueue<ProcessableRecipeObject<SteamCookingRecipe>> processingBatch;
    
    //#region Property Delegate
    private final PropertyDelegate propertyDelegate = new PropertyDelegate()
    {
        public int get(int key)
        {
            switch(key)
            {
                case CURRENT_WATER_LEVEL:
                    if(getWaterFillLevel() == 0) return 0;
                    return inverseWaterLevel;
                case MAX_LEVEL:
                    return maxWaterLevel;
                default:
                    return 0;
            }
        }
        
        public void set(int key, int value)
        {
            switch(key)
            {
                case CURRENT_WATER_LEVEL:
                    inverseWaterLevel = value;
                    break;
                case MAX_LEVEL:
                    maxWaterLevel = value;
                    break;
            }
        }
        
        public int size()
        {
            return 2;
        }
    };
    //#endregion
    
    public SteamCookerBlockEntity()
    {
        super(LEntities.STEAM_COOKER_BLOCK_ENTITY, 3);
        processingBatch = new LimitedQueue<>(3);
        maxWaterLevel = MAX_WATER_LEVEL;
    }
    
    @Override public int[] getInvAvailableSlots(Direction side)
    {
        if(side.equals(Direction.DOWN))
        {
            return new int[]{OUTPUT_SLOT};
        }
        else
        {
            return new int[]{INPUT_SLOT};
        }
    }
    
    @Override public boolean isValidInvStack(int slot, ItemStack stack)
    {
        return slot == INPUT_SLOT;
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
        SteamCookingRecipe bcRecipe = (SteamCookingRecipe) recipe;
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
                    return currentOutputStack.getCount() + bcRecipe.getOutputAmount() + processingBatch.size() <= this.getInvMaxStackAmount();
                }
            }
        }
        else
        {
            return false;
        }
    }
    
    private boolean canProcessingSlotAcceptInput(Item item)
    {
        if(item.equals(null))
        {
            return true;
        }
        ProcessableRecipeObject peek = processingBatch.head();
        if(peek == null)
        {
            return true;
        }
        if(processingBatch.canAdd() && peek.getInputType().equals(item))
        {
            return true;
        }
        return false;
    }
    
    private void addProcessingElement(Item item)
    {
        ItemStack processingItemStack = items.get(PROCESSING_SLOT);
        if(processingItemStack.isEmpty())
        {
            items.set(PROCESSING_SLOT, new ItemStack(item));
        }
        else if(item.equals(processingItemStack.getItem()))
        {
            processingItemStack.increment(1);
        }
    }
    
    @Override protected void craftRecipe(Recipe<?> recipe)
    {
        SteamCookingRecipe fRecipe = (SteamCookingRecipe) recipe;
        if(fRecipe != null && this.canAcceptRecipeOutput(fRecipe))
        {
            ItemStack output = fRecipe.craft(this);
            ItemStack outputSlot = (ItemStack) this.items.get(OUTPUT_SLOT);
            if(outputSlot.isEmpty())
            {
                this.items.set(OUTPUT_SLOT, output);
            }
            else if(outputSlot.getItem() == output.getItem())
            {
                outputSlot.increment(fRecipe.getOutputAmount());
            }
            items.get(PROCESSING_SLOT).decrement(1);
        }
    }
    
    private void setOn(boolean state)
    {
        if(!world.isClient)
        {
            this.world.setBlockState(this.pos, (BlockState) this.world.getBlockState(this.pos).with(SteamCookerBlock.ON, state), 0B1011);
        }
    }
    
    private boolean isOn()
    {
        return this.world.getBlockState(this.pos).get(SteamCookerBlock.ON);
    }
    
    private void setWaterFilled(int state)
    {
        if(state < 0) state = 0;
        if(state > 5) state = 5;
        if(!world.isClient)
        {
            this.world.setBlockState(this.pos, (BlockState) this.world.getBlockState(this.pos).with(SteamCookerBlock.WATER_FILL_LEVEL, state), 0B1011);
        }
    }
    
    public int getWaterFillLevel()
    {
        return this.world.getBlockState(this.pos).get(SteamCookerBlock.WATER_FILL_LEVEL);
    }
    
    public void fillWater()
    {
        if(!world.isClient)
        {
            setWaterFilled(5);
            currentWaterLevel = 0;
            world.updateHorizontalAdjacent(pos, world.getBlockState(pos).getBlock());
        }
    }
    
    public void drainWater()
    {
        if(!world.isClient)
        {
            setWaterFilled(0);
            currentWaterLevel = MAX_WATER_LEVEL;
            world.updateHorizontalAdjacent(pos, world.getBlockState(pos).getBlock());
        }
    }
    
    private boolean isWaterLevelSufficient()
    {
        return currentWaterLevel < MAX_WATER_LEVEL && getWaterFillLevel() != 0;
    }
    
    public boolean isWaterNew()
    {
        return currentWaterLevel == 0 && getWaterFillLevel() == 5;
    }
    
    @Override public void fromTag(CompoundTag tag)
    {
        super.fromTag(tag);
        currentWaterLevel = tag.getInt("waterLevel");
        inverseWaterLevel = MAX_WATER_LEVEL - currentWaterLevel;
    }
    
    @Override public CompoundTag toTag(CompoundTag tag)
    {
        tag.putInt("waterLevel", currentWaterLevel);
        return super.toTag(tag);
    }
    
    @Override public PropertyDelegate getPropertyDelegate()
    {
        return propertyDelegate;
    }
    
    @Override public void tick()
    {
        if(!world.isClient)
        {
            inverseWaterLevel = MAX_WATER_LEVEL - currentWaterLevel;
            SteamCookingRecipe match = world.getRecipeManager().getFirstMatch(SteamCookingRecipe.Type.INSTANCE, new BasicInventory(items.get(INPUT_SLOT)), world).orElse(null);
            if(match != null)
            {
                if(isWaterLevelSufficient())
                {
                    Item inputItem = items.get(INPUT_SLOT).getItem();
                    if(canProcessingSlotAcceptInput(inputItem) && canAcceptRecipeOutput(match))
                    {
                        processingBatch.enqueue(new ProcessableRecipeObject<>(inputItem, match));
                        items.get(INPUT_SLOT).decrement(1);
                        addProcessingElement(inputItem);
                    }
                }
            }
            
            if(!processingBatch.isEmpty())
            {
                int size = processingBatch.size();
                for(int i = 0; i < size; i++)
                {
                    processingBatch.at(i).processStep();
                }
                while(!processingBatch.isEmpty() && processingBatch.head().isCompleted())
                {
                    craftRecipe(processingBatch.poll().getRecipe());
                    currentWaterLevel++;
                }
                if(!isOn())
                {
                    setOn(true);
                }
                int newLevel;
                if(getWaterFillLevel() != (newLevel = getWaterFillEquivalent()))
                {
                    setWaterFilled(newLevel);
                }
            }
            else if(isOn())
            {
                setOn(false);
            }
        }
    }
    
    private int getWaterFillEquivalent()
    {
        return 5 - ((int) (((float) currentWaterLevel / MAX_WATER_LEVEL) * 5));
    }
}
