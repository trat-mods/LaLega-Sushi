package net.la.lega.mod.entity;

import blue.endless.jankson.annotation.Nullable;
import io.github.cottonmc.cotton.gui.PropertyDelegateHolder;
import net.la.lega.mod.ImplementedInventory;
import net.la.lega.mod.block.BlastChillerBlock;
import net.la.lega.mod.loader.LaLegaLoader;
import net.la.lega.mod.recipe.BlastChillingRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.container.PropertyDelegate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;

public class BlastChillerBlockEntity extends BlockEntity implements ImplementedInventory, PropertyDelegateHolder, Tickable
{

    public static final int CHILL_TIME = 0;
    public static final int CHILL_TIME_TOTAL = 1;

    private static final int[] TOP_SLOTS = new int[] { 0 };
    private static final int[] BOTTOM_SLOTS = new int[] { 1 };
    private static final int[] SIDE_SLOTS = new int[] { 0 };

    private int currentChillTime = 0;
    private int chillTimeTotal;
    private final PropertyDelegate propertyDelegate;


    DefaultedList<ItemStack> items = DefaultedList.ofSize(2, ItemStack.EMPTY);

    public BlastChillerBlockEntity() {
        super(LaLegaLoader.CHILL_BLASTER_BLOCK_ENTITY);

        this.propertyDelegate = new PropertyDelegate() {
            public int get(int key) {
                switch (key) {
                case CHILL_TIME:
                    return BlastChillerBlockEntity.this.currentChillTime;
                case CHILL_TIME_TOTAL:
                    return BlastChillerBlockEntity.this.chillTimeTotal;
                default:
                    return 0;
                }
            }

            public void set(int key, int value) {
                switch (key) {
                case CHILL_TIME:
                    BlastChillerBlockEntity.this.currentChillTime = value;
                    break;
                case CHILL_TIME_TOTAL:
                    BlastChillerBlockEntity.this.chillTimeTotal = value;
                    break;
                }
            }

            public int size() 
            {
                return 2;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() 
    {
        return items;
    }

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
    public void tick() 
    {
        boolean stateChanged = this.isChilling();
        boolean crafted = false;

        if(!this.world.isClient)
        {
            BasicInventory inv = new BasicInventory(items.get(0));
            BlastChillingRecipe match = world.getRecipeManager().getFirstMatch(BlastChillingRecipe.Type.INSTANCE, inv, world).orElse(null);
            if (!this.isChilling())
            {
                if(this.canAcceptRecipeOutput(match)) 
                {
                    this.currentChillTime = match.getChillTime();
                }
            }

            boolean set = isChilling();
            this.world.setBlockState(this.pos, (BlockState)this.world.getBlockState(this.pos).with(BlastChillerBlock.ON, set), 3);

            if(this.isChilling())
            {
                this.currentChillTime--;
                if(this.currentChillTime == 0)
                {
                    this.craftRecipe(match);
                    crafted = true;
                }   
            }
            if(stateChanged != this.isChilling())
            {
                crafted = true;
            }
        }
        if(crafted)
        {
            this.markDirty();
        }
    }

    @Override
    public PropertyDelegate getPropertyDelegate() 
    {
        return this.propertyDelegate;
    }

    public boolean isChilling()
    {
        return this.currentChillTime != 0;
    }

    protected boolean canAcceptRecipeOutput(@Nullable BlastChillingRecipe recipe)
    {
        if (!((ItemStack)this.items.get(0)).isEmpty() && recipe != null) 
        {
           ItemStack itemStack = recipe.getOutput();
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
                else if (itemStack2.getCount() + recipe.getOutputAmount() <= this.getInvMaxStackAmount() && itemStack2.getCount() + recipe.getOutputAmount() <= itemStack2.getMaxCount()) 
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

     private void craftRecipe(@Nullable BlastChillingRecipe recipe) 
     {
        if (recipe != null && this.canAcceptRecipeOutput(recipe)) 
        {
           ItemStack inputSlot = (ItemStack)this.items.get(0);
           ItemStack output = recipe.getOutput();
           ItemStack outputSlot = (ItemStack)this.items.get(1);
           if (outputSlot.isEmpty()) 
           {
              this.items.set(1, output.copy());
           }
           else if (outputSlot.getItem() == output.getItem()) 
           {
              outputSlot.increment(recipe.getOutputAmount());
           }
           inputSlot.decrement(1);
        }
     }
}