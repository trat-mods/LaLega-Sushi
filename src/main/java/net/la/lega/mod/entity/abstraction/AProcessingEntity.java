package net.la.lega.mod.entity.abstraction;

import io.github.cottonmc.cotton.gui.PropertyDelegateHolder;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.la.lega.mod.recipe.abstraction.AProcessingRecipe;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.container.PropertyDelegate;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;

/**
 * Represent an abstract outputter block that can process items (similar to furnaces or even droppers)
 *
 * @author t_r_a_t
 */
public abstract class AProcessingEntity extends ASidedInventoryEntity implements Tickable, PropertyDelegateHolder, BlockEntityClientSerializable
{
    public static final int PROCESS_TIME = 0;
    public static final int UNIT_PROCESS_TIME = 1;
    
    protected AProcessingRecipe currentRecipe = null;
    private int currentProcessingTime = -1;
    private int unitProcessingTime = 0;
    
    //#region Property Delegate
    private final PropertyDelegate propertyDelegate = new PropertyDelegate()
    {
        public int get(int key)
        {
            switch(key)
            {
                case PROCESS_TIME:
                    return currentProcessingTime;
                case UNIT_PROCESS_TIME:
                    return unitProcessingTime;
                default:
                    return 0;
            }
        }
        
        public void set(int key, int value)
        {
            switch(key)
            {
                case PROCESS_TIME:
                    currentProcessingTime = value;
                    break;
                case UNIT_PROCESS_TIME:
                    unitProcessingTime = value;
                    break;
            }
        }
        
        public int size()
        {
            return 2;
        }
    };
    //#endregion
    
    /**
     * @param entity the entity type
     * @param itemStackNumber the number of item stacks in this container
     */
    public AProcessingEntity(BlockEntityType<?> entity, int itemStackNumber)
    {
        super(entity, itemStackNumber);
    }
    
    @Override
    public void fromTag(CompoundTag tag)
    {
        super.fromTag(tag);
        this.currentProcessingTime = tag.getShort("currentProcessingTime");
        this.unitProcessingTime = tag.getShort("unitProcessingTime");
    }
    
    @Override
    public CompoundTag toTag(CompoundTag tag)
    {
        tag.putShort("currentProcessingTime", (short) this.currentProcessingTime);
        tag.putShort("unitProcessingTime", (short) this.unitProcessingTime);
        return super.toTag(tag);
    }
    
    @Override public void fromClientTag(CompoundTag tag)
    {
        currentProcessingTime = tag.getShort("currentProcessingTime");
        unitProcessingTime = tag.getShort("unitProcessingTime");
    }
    
    @Override public CompoundTag toClientTag(CompoundTag tag)
    {
        tag.putShort("currentProcessingTime", (short) this.currentProcessingTime);
        tag.putShort("unitProcessingTime", (short) this.unitProcessingTime);
        return tag;
    }
    
    @Override
    public PropertyDelegate getPropertyDelegate()
    {
        return this.propertyDelegate;
    }
    
    @Override
    public void tick()
    {
    }
    
    //#region Processing
    
    /**
     * @return true if the outputter is currently processing
     */
    protected boolean isProcessing()
    {
        return this.currentProcessingTime > 0;
    }
    
    protected void checkCurrentRecipe(AProcessingRecipe match)
    {
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
    }
    
    /**
     * initialize a new processing
     *
     * @param unitProcessingTime the processing time of a single unit
     */
    protected void initializeProcessing(int unitProcessingTime)
    {
        this.currentProcessingTime = 1;
        this.unitProcessingTime = unitProcessingTime;
    }
    
    /**
     * @return true if the current unit has been processed
     */
    protected boolean isProcessingCompleted()
    {
        return currentProcessingTime >= unitProcessingTime;
    }
    
    /**
     * perform a step in the process
     */
    protected void processStep()
    {
        this.currentProcessingTime++;
    }
    
    /**
     * reset the processing task
     */
    protected void resetProcessing()
    {
        this.currentProcessingTime = -1;
        this.unitProcessingTime = 0;
    }
    
    protected void processCurrentRecipe()
    {
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
    
    protected int getCurrentProcessingTime()
    {
        return this.currentProcessingTime;
    }
    
    protected int getCurrentUnitProcessingTime()
    {
        return this.unitProcessingTime;
    }
    
    //#endregion
}