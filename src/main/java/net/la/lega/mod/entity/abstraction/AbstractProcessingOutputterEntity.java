package net.la.lega.mod.entity.abstraction;

import blue.endless.jankson.annotation.Nullable;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.la.lega.mod.ImplementedInventory;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.container.PropertyDelegate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;
/**
 * 1Represent an abstract outputter block that can process items (similar to furnaces or even droppers)
 * @author t_r_a_t
 */
public class AbstractProcessingOutputterEntity extends BlockEntity implements ImplementedInventory, Tickable, BlockEntityClientSerializable
{
    protected DefaultedList<ItemStack> items;

    protected PropertyDelegate propertyDelegate = null;
    private int currentProcessingTime = -1;
    private int unitProcessingTime = 0;

    /**
    * @param entity the entity type
    */
    public AbstractProcessingOutputterEntity(BlockEntityType<?> entity) 
    {
        super(entity);
        items = DefaultedList.ofSize(1,  ItemStack.EMPTY);
    }

    /**
    * @param entity the entity type
    * @param itemStackNumber the number of item stacks in this container
    */
    public AbstractProcessingOutputterEntity(BlockEntityType<?> entity, int itemStackNumber)
    {
        super(entity);
        items = DefaultedList.ofSize(itemStackNumber,  ItemStack.EMPTY);
    }

    @Override
    public DefaultedList<ItemStack> getItems() 
    {
        return items;
    }

    /**
    * @apiNote override to change the accessibility of the inventory
    */
    @Override
    public boolean canPlayerUseInv(PlayerEntity player) 
    {
        if (this.world.getBlockEntity(this.pos) != this) 
        {
            return false;
        } 
        else 
        {
            return player.squaredDistanceTo((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public int[] getInvAvailableSlots(Direction side)
    {
        return null;
    }

    /**
    * @apiNote is the slot valid for extract/insert
    */
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
    public void setInvStack(int slot, ItemStack stack) 
    {
        ImplementedInventory.super.setInvStack(slot, stack);
    }

    protected boolean canAcceptRecipeOutput(@Nullable Recipe<?> recipe)
    {
        return false;
    }

    protected void craftRecipe(@Nullable Recipe<?> recipe)
    {

    }

    @Override
    public void fromClientTag(CompoundTag tag) 
    {
        fromTag(tag);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) 
    {
        return toTag(tag);
    }
    
    @Override
    public void fromTag(CompoundTag tag) 
    {
        super.fromTag(tag);
        Inventories.fromTag(tag, items);
        this.currentProcessingTime = tag.getShort("currentProcessingTime");
        this.unitProcessingTime = tag.getShort("unitProcessingTime");
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) 
    {
        Inventories.toTag(tag, items);
        tag.putShort("currentProcessingTime", (short)this.currentProcessingTime);
        tag.putShort("unitProcessingTime", (short)this.unitProcessingTime);
        return super.toTag(tag);
    }


    /**
    * @return true if the outputter is currently processing
    */
    protected boolean isProcessing()
    {
        return currentProcessingTime > 0;
    }

    /**
    * initialize a new processing
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

    protected int getCurrentProcessingTime()   
    {
        return this.currentProcessingTime;
    }

    protected int getCurrentUnitProcessingTime()
    {
        return this.unitProcessingTime;
    }

    public PropertyDelegate getPropertyDelegate()
    {
        return this.propertyDelegate;
    }

    @Override
    public void tick() 
    {
    }
}