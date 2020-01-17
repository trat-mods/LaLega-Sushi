package net.la.lega.mod.entity;

import java.util.Iterator;

import net.la.lega.mod.loader.LaLegaLoader;
import net.la.lega.mod.recipe.ChillBlastingRecipe;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.container.Container;
import net.minecraft.container.PropertyDelegate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeType;
import net.minecraft.text.Text;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;

public class ChillBlasterBlockEntity extends BlockEntity implements SidedInventory, Tickable, Inventory
{
    public static final int CHILL_TIME = 0;
    public static final int CHILL_TIME_TOTAL = 1;

    private static final int[] TOP_SLOTS = new int[]{0};
    private static final int[] BOTTOM_SLOTS = new int[]{1};
    private static final int[] SIDE_SLOTS = new int[]{1};

    private int chillTime;
    private int chillTimeTotal;
    protected DefaultedList<ItemStack> inventory;
    protected final RecipeType<ChillBlastingRecipe> recipeType;
    private final PropertyDelegate propertyDelegate;

    public ChillBlasterBlockEntity() 
    {
        super(LaLegaLoader.CHILL_BLASTER_BLOCK_ENTITY);
        this.inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
        this.recipeType = ChillBlastingRecipe.Type.INSTANCE;

        this.propertyDelegate = new PropertyDelegate() {
            public int get(int key) {
               switch(key) {
               case CHILL_TIME:
                  return ChillBlasterBlockEntity.this.chillTime;
               case CHILL_TIME_TOTAL:
                  return ChillBlasterBlockEntity.this.chillTimeTotal;
               default:
                  return 0;
               }
            }
   
            public void set(int key, int value) {
               switch(key) {
               case CHILL_TIME:
                    ChillBlasterBlockEntity.this.chillTime = value;
                  break;
               case CHILL_TIME_TOTAL:
                    ChillBlasterBlockEntity.this.chillTimeTotal = value;
                  break;
               }
            } 
            public int size() {
               return 2;
            }
         };

    }

    protected int getChillTime()
    {
        return (Integer)this.world.getRecipeManager().getFirstMatch(this.recipeType, this, this.world).map(ChillBlastingRecipe::getChillTime).orElse(40);
    }

    private boolean isChilling()
    {
        return this.chillTime > 0;
    }

    @Override
    public int getInvSize()
    {
        return this.inventory.size();
    }

    @Override
    public boolean isInvEmpty() 
    {
        Iterator var1 = this.inventory.iterator();
        ItemStack itemStack;
        do {
           if (!var1.hasNext()) {
              return true;
           }
           itemStack = (ItemStack)var1.next();
        } while(itemStack.isEmpty());
        return false;
    }

    @Override
    public ItemStack getInvStack(int slot) 
    {
        return (ItemStack)this.inventory.get(slot);
    }

    @Override
    public ItemStack takeInvStack(int slot, int amount) 
    {
        return Inventories.splitStack(this.inventory, slot, amount);
    }

    @Override
    public ItemStack removeInvStack(int slot) 
    {
        return Inventories.removeStack(this.inventory, slot);
    }

    @Override
    public void setInvStack(int slot, ItemStack stack) 
    {
        ItemStack itemStack = (ItemStack)this.inventory.get(slot);
        boolean bl = !stack.isEmpty() && stack.isItemEqualIgnoreDamage(itemStack) && ItemStack.areTagsEqual(stack, itemStack);
        this.inventory.set(slot, stack);
        if (stack.getCount() > this.getInvMaxStackAmount()) {
           stack.setCount(this.getInvMaxStackAmount());
        }
  
        if (slot == 0 && !bl) 
        {
           this.chillTimeTotal = this.getChillTime();
           this.chillTime = 0;
           this.markDirty();
        }
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
    public void clear() 
    {
        this.inventory.clear();
    }

    protected Container createContainer(int i, PlayerInventory playerInventory) 
    {
        System.out.println("Creating container");
        return null;
    }

    @Override
    public void tick() 
    {
    }

    @Override
    public int[] getInvAvailableSlots(Direction side) 
    {
        if (side == Direction.DOWN) 
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
        return !(slot == 1);
     }

    @Override
    public boolean canInsertInvStack(int slot, ItemStack stack, Direction dir) 
    {
        return this.isValidInvStack(slot, stack);
    }

    @Override
    public boolean canExtractInvStack(int slot, ItemStack stack, Direction dir) {
        if (dir == Direction.DOWN && slot == 1) 
        {
           Item item = stack.getItem();
           if (item != Items.WATER_BUCKET && item != Items.BUCKET) 
           {
              return false;
           }
        }  
        return true;
    }

    protected Text getContainerName() 
    {
        return null;
    }

}