package net.la.lega.mod.entity.abstraction;

import blue.endless.jankson.annotation.Nullable;
import io.github.cottonmc.cotton.gui.PropertyDelegateHolder;
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

public abstract class AbstractOutputterEntity extends BlockEntity implements ImplementedInventory, PropertyDelegateHolder, Tickable, BlockEntityClientSerializable
{

    protected PropertyDelegate propertyDelegate = null;

    protected DefaultedList<ItemStack> items;

    /**
    * @param entity the entity type
    */
    public AbstractOutputterEntity(BlockEntityType<?> entity) 
    {
        super(entity);
        items = DefaultedList.ofSize(1,  ItemStack.EMPTY);
    }

    /**
    * @param entity the entity type
    * @param itemStackNumber the number of item stacks in this container
    */
    public AbstractOutputterEntity(BlockEntityType<?> entity, int itemStackNumber)
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
    public abstract int[] getInvAvailableSlots(Direction side);

    /**
    * @apiNote is the slot valid for extract/insert
    */
    public abstract boolean isValidInvStack(int slot, ItemStack stack);

    @Override
    public abstract boolean canInsertInvStack(int slot, ItemStack stack, Direction dir);

    @Override
    public abstract boolean canExtractInvStack(int slot, ItemStack stack, Direction dir);

    @Override
    public void setInvStack(int slot, ItemStack stack) 
    {
        ImplementedInventory.super.setInvStack(slot, stack);
    }

    @Override
    public PropertyDelegate getPropertyDelegate() 
    {
        return this.propertyDelegate;
    }

    protected abstract boolean canAcceptRecipeOutput(@Nullable Recipe<?> recipe);

    protected abstract void craftRecipe(@Nullable Recipe<?> recipe);


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
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) 
    {
        Inventories.toTag(tag, items);
        return super.toTag(tag);
    }
}