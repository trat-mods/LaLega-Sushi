package net.la.lega.mod.entity;

import io.github.cottonmc.cotton.gui.PropertyDelegateHolder;
import net.la.lega.mod.api.LimitedQueue;
import net.la.lega.mod.api.ProcessableRecipeObject;
import net.la.lega.mod.block.FryerBlock;
import net.la.lega.mod.entity.abstraction.AInventoryEntity;
import net.la.lega.mod.initializer.LEntities;
import net.la.lega.mod.model_enum.OilType;
import net.la.lega.mod.recipe.FryingRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.container.PropertyDelegate;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;

import java.util.function.Supplier;

public class FryerBlockEntity extends AInventoryEntity implements Tickable, PropertyDelegateHolder
{
    public static final int OUTPUT_SLOT = 0;
    public static final int PROCESSING_SLOT = 1;
    public static final int INPUT_SLOT = 2;
    private static final int MAX_OIL_USAGE = 30;//256;
    
    public static final int CURRENT_OIL_USAGE = 0;
    public static final int MAX_USAGE = 1;
    
    private int currentOilUsage;
    private int maxOilUsage;
    private int inverseOilUsage;
    private LimitedQueue<ProcessableRecipeObject<FryingRecipe>> fryingBatch;
    
    //#region Property Delegate
    private final PropertyDelegate propertyDelegate = new PropertyDelegate()
    {
        public int get(int key)
        {
            switch(key)
            {
                case CURRENT_OIL_USAGE:
                    if(getOilType() == OilType.NONE) return 0;
                    return inverseOilUsage;
                case MAX_USAGE:
                    return maxOilUsage;
                default:
                    return 0;
            }
        }
        
        public void set(int key, int value)
        {
            switch(key)
            {
                case CURRENT_OIL_USAGE:
                    inverseOilUsage = value;
                    break;
                case MAX_USAGE:
                    maxOilUsage = value;
                    break;
            }
        }
        
        public int size()
        {
            return 2;
        }
    };
    
    public final Supplier<String> oilTypeSupplier = () ->
    {
        OilType type = getOilType();
        switch(type)
        {
            case RICE_OIL:
                return "Rice Oil";
            case SUNFLOWER_OIL:
                return "Sunflower Oil";
            case NONE:
                return "None";
            default:
                return "";
        }
    };
    //#endregion
    
    public FryerBlockEntity()
    {
        super(LEntities.FRYER_BLOCK_ENTITY, 3);
        fryingBatch = new LimitedQueue<>(4);
        maxOilUsage = MAX_OIL_USAGE;
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
        FryingRecipe bcRecipe = (FryingRecipe) recipe;
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
                    return currentOutputStack.getCount() + bcRecipe.getOutputAmount() + fryingBatch.size() <= this.getInvMaxStackAmount();
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
        FryingRecipe fRecipe = (FryingRecipe) recipe;
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
    
    @Override public void tick()
    {
        if(!world.isClient)
        {
            inverseOilUsage = MAX_OIL_USAGE - currentOilUsage;
            FryingRecipe match = world.getRecipeManager().getFirstMatch(FryingRecipe.Type.INSTANCE, new BasicInventory(items.get(INPUT_SLOT)), world).orElse(null);
            if(match != null)
            {
                if(isOilValid())
                {
                    Item inputItem = items.get(INPUT_SLOT).getItem();
                    if(canProcessingSlotAcceptInput(inputItem) && canAcceptRecipeOutput(match))
                    {
                        fryingBatch.enqueue(new ProcessableRecipeObject<>(inputItem, match));
                        items.get(INPUT_SLOT).decrement(1);
                        addProcessingElement(inputItem);
                    }
                }
            }
            
            if(!fryingBatch.isEmpty())
            {
                int size = fryingBatch.size();
                for(int i = 0; i < size; i++)
                {
                    fryingBatch.at(i).processStep();
                }
                while(!fryingBatch.isEmpty() && fryingBatch.head().isCompleted())
                {
                    craftRecipe(fryingBatch.poll().getRecipe());
                    currentOilUsage++;
                }
                if(!isOn())
                {
                    setOn(true);
                }
            }
            else if(isOn())
            {
                setOn(false);
            }
        }
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
    
    private boolean canProcessingSlotAcceptInput(Item item)
    {
        if(item.equals(null))
        {
            return true;
        }
        ProcessableRecipeObject peek = fryingBatch.head();
        if(peek == null)
        {
            return true;
        }
        if(fryingBatch.canAdd() && peek.getInputType().equals(item))
        {
            return true;
        }
        return false;
    }
    
    public int getComparatorOutput()
    {
        return getOilType() == OilType.NONE ? 6 : (int) (((float) currentOilUsage / MAX_OIL_USAGE) * 5);
    }
    
    public void fillOil(OilType oilType)
    {
        if(!world.isClient)
        {
            setOilType(oilType);
            currentOilUsage = 0;
            world.updateHorizontalAdjacent(pos, world.getBlockState(pos).getBlock());
        }
    }
    
    public void drainOil()
    {
        if(!world.isClient)
        {
            setOilType(OilType.NONE);
            currentOilUsage = MAX_OIL_USAGE;
            world.updateHorizontalAdjacent(pos, world.getBlockState(pos).getBlock());
        }
    }
    
    public boolean isOilNew()
    {
        return currentOilUsage == 0 && getOilType() != OilType.NONE;
    }
    
    public boolean isOilValid()
    {
        return currentOilUsage < MAX_OIL_USAGE && getOilType() != OilType.NONE;
    }
    
    private void setOilType(OilType oilType)
    {
        if(!world.isClient)
        {
            this.world.setBlockState(this.pos, (BlockState) this.world.getBlockState(this.pos).with(FryerBlock.OIL_TYPE, oilType), 0B1011);
        }
    }
    
    public OilType getOilType()
    {
        return this.world.getBlockState(pos).get(FryerBlock.OIL_TYPE);
    }
    
    private void setOn(boolean state)
    {
        if(!world.isClient)
        {
            this.world.setBlockState(this.pos, (BlockState) this.world.getBlockState(this.pos).with(FryerBlock.ON, state), 3);
        }
    }
    
    private boolean isOn()
    {
        return this.world.getBlockState(this.pos).get(FryerBlock.ON);
    }
    
    @Override public void fromTag(CompoundTag tag)
    {
        super.fromTag(tag);
        currentOilUsage = tag.getInt("oilUsage");
        inverseOilUsage = MAX_OIL_USAGE - currentOilUsage;
    }
    
    @Override public CompoundTag toTag(CompoundTag tag)
    {
        tag.putInt("oilUsage", currentOilUsage);
        return super.toTag(tag);
    }
    
    @Override public PropertyDelegate getPropertyDelegate()
    {
        return propertyDelegate;
    }
}
