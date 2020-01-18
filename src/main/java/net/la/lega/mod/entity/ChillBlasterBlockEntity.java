package net.la.lega.mod.entity;

import java.util.Optional;

import io.github.cottonmc.cotton.gui.PropertyDelegateHolder;
import net.la.lega.mod.TickableSidedInventory;
import net.la.lega.mod.loader.LaLegaLoader;
import net.la.lega.mod.recipe.ChillBlastingRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.container.PropertyDelegate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;

public class ChillBlasterBlockEntity extends BlockEntity implements TickableSidedInventory, PropertyDelegateHolder, InventoryProvider {

    public static final int CHILL_TIME = 0;
    public static final int CHILL_TIME_TOTAL = 1;

    private static final int[] TOP_SLOTS = new int[] { 0 };
    private static final int[] BOTTOM_SLOTS = new int[] { 1 };
    private static final int[] SIDE_SLOTS = new int[] { 1 };

    private int chillTime;
    private int chillTimeTotal;
    private final PropertyDelegate propertyDelegate;

    private int counter = 0;

    DefaultedList<ItemStack> items = DefaultedList.ofSize(2, ItemStack.EMPTY);

    public ChillBlasterBlockEntity() {
        super(LaLegaLoader.CHILL_BLASTER_BLOCK_ENTITY);

        this.propertyDelegate = new PropertyDelegate() {
            public int get(int key) {
                switch (key) {
                case CHILL_TIME:
                    return ChillBlasterBlockEntity.this.chillTime;
                case CHILL_TIME_TOTAL:
                    return ChillBlasterBlockEntity.this.chillTimeTotal;
                default:
                    return 0;
                }
            }

            public void set(int key, int value) {
                switch (key) {
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

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public boolean canPlayerUseInv(PlayerEntity player) {
        if (this.world.getBlockEntity(this.pos) != this) {
            return false;
        } else {
            return player.squaredDistanceTo((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        Inventories.fromTag(tag, items);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
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

    @Override
    public boolean canInsertInvStack(int slot, ItemStack stack, Direction dir) {
        return this.isValidInvStack(slot, stack);
    }

    @Override
    public boolean canExtractInvStack(int slot, ItemStack stack, Direction dir) 
    {
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

    @Override
    public void tick() 
    {
        if(items.get(0) != ItemStack.EMPTY)
        {
            BasicInventory inv = new BasicInventory(items.get(0));
            Optional<ChillBlastingRecipe> match = world.getRecipeManager().getFirstMatch(ChillBlastingRecipe.Type.INSTANCE, inv, world);

            if (match.isPresent()) 
            {
                items.set(1, match.get().getOutput());
                // items.get(0).decrement(1); 
            }
        }
        if(counter < 100)
        {
            counter++;
        }
        else
        {
            System.out.println("Itemstack[0]:" + items.get(0).getCount());
            counter = 0;
        }
    }

    @Override
    public PropertyDelegate getPropertyDelegate() {
        return this.propertyDelegate;
    }

    @Override
    public SidedInventory getInventory(BlockState state, IWorld world, BlockPos pos) 
    {
        return this;
    }

    public void slotClicked(int slot)
    {
        System.out.println("Slot:" + slot);

    }
}