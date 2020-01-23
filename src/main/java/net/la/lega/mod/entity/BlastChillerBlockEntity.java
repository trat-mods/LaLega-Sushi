package net.la.lega.mod.entity;

import blue.endless.jankson.annotation.Nullable;
import net.la.lega.mod.block.BlastChillerBlock;
import net.la.lega.mod.entity.abstraction.AbstractOutputterEntity;
import net.la.lega.mod.loader.LaLegaLoader;
import net.la.lega.mod.recipe.BlastChillingRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.container.PropertyDelegate;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.math.Direction;

public class BlastChillerBlockEntity extends AbstractOutputterEntity
{
    public static final int CHILL_TIME = 0;
    public static final int UNIT_CHILL_TIME = 1;

    private static final int[] TOP_SLOTS = new int[] { 0 };
    private static final int[] BOTTOM_SLOTS = new int[] { 1 };
    private static final int[] SIDE_SLOTS = new int[] { 0 };

    private int currentChillTime = -1;
    private int unitChillTime = 0;

    public BlastChillerBlockEntity() 
    {
        super(LaLegaLoader.BLAST_CHILLER_BLOCK_ENTITY, 2);

        this.propertyDelegate = new PropertyDelegate() 
        {
            public int get(int key) 
            {
                switch (key) 
                {
                    case CHILL_TIME:
                        //System.out.println("CHILL_TIME: " + BlastChillerBlockEntity.this.currentChillTime);
                        return BlastChillerBlockEntity.this.currentChillTime;
                    case UNIT_CHILL_TIME:
                        //System.out.println("UNIT_CHILL_TIME: " + BlastChillerBlockEntity.this.unitChillTime);
                        return BlastChillerBlockEntity.this.unitChillTime;
                    default:
                        return 0;
                }
            }
            public void set(int key, int value) 
            {
                return;
            }
            public int size() 
            {
                return 2;
            }
        };
    }

    @Override
    public void fromTag(CompoundTag tag) 
    {
        this.currentChillTime = tag.getShort("CurrentChillTime");
        this.unitChillTime = tag.getShort("UnitChillTime");
        super.fromTag(tag);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) 
    {
        tag.putShort("CurrentChillTime", (short)this.currentChillTime);
        tag.putShort("UnitChillTime", (short)this.unitChillTime);
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

    @Override
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
    public void setInvStack(int slot, ItemStack stack) 
    {
        super.setInvStack(slot, stack);
        ItemStack itemStack = (ItemStack)this.getItems().get(slot);
        boolean bl = !stack.isEmpty() && stack.isItemEqualIgnoreDamage(itemStack) && ItemStack.areTagsEqual(stack, itemStack);
        if (slot == 0 && !bl) 
        {
            this.currentChillTime = 0;
            this.markDirty();
         }
    }


    @Override
    public void tick() 
    {
        if(!this.world.isClient)
        {
            BasicInventory inv = new BasicInventory(items.get(0));
            BlastChillingRecipe match = world.getRecipeManager().getFirstMatch(BlastChillingRecipe.Type.INSTANCE, inv, world).orElse(null);
            if (!this.isChilling())
            {
                if(this.canAcceptRecipeOutput(match)) 
                {
                    this.currentChillTime = 1;
                    this.unitChillTime = match.getChillTime();
                    sync();
                }
            }

            boolean set = isChilling();
            this.world.setBlockState(this.pos, (BlockState)this.world.getBlockState(this.pos).with(BlastChillerBlock.ON, set), 3);

            if(this.isChilling())
            {
                this.currentChillTime++;
                if(this.currentChillTime >= unitChillTime)
                {
                    this.craftRecipe(match);
                    this.currentChillTime = -1;
                }
                sync();
            }
        }
        this.markDirty();
    }


    public boolean isChilling()
    {
        return this.currentChillTime > 0;
    }

    @Override
    protected boolean canAcceptRecipeOutput(Recipe<?> recipe) 
    {
        BlastChillingRecipe bcRecipe = (BlastChillingRecipe) recipe;
        if (!((ItemStack)this.items.get(0)).isEmpty() && recipe != null) 
        {
           ItemStack itemStack = bcRecipe.getOutput();
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
                else if (itemStack2.getCount() + bcRecipe.getOutputAmount() <= this.getInvMaxStackAmount() && itemStack2.getCount() + bcRecipe.getOutputAmount() <= itemStack2.getMaxCount()) 
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

    @Override
    protected void craftRecipe(@Nullable Recipe<?> recipe) 
    {
        BlastChillingRecipe bcRecipe = (BlastChillingRecipe) recipe;
        if (recipe != null && this.canAcceptRecipeOutput(recipe)) 
        {
           ItemStack inputSlot = (ItemStack)this.items.get(0);
           ItemStack output = bcRecipe.getOutput();
           ItemStack outputSlot = (ItemStack)this.items.get(1);
           if (outputSlot.isEmpty()) 
           {
              this.items.set(1, output.copy());
           }
           else if (outputSlot.getItem() == output.getItem()) 
           {
              outputSlot.increment(bcRecipe.getOutputAmount());
           }
           inputSlot.decrement(1);
        }
    }
}