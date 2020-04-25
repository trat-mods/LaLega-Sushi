package net.la.lega.mod.entity;

import blue.endless.jankson.annotation.Nullable;
import net.la.lega.mod.block.QuadrhopperBlock;
import net.la.lega.mod.entity.abstraction.AInventoryEntity;
import net.la.lega.mod.initializer.LEntities;
import net.la.lega.mod.mixin.HopperBlockEntityAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.Hopper;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.BooleanBiFunction;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuadrhopperBlockEntity extends AInventoryEntity implements Hopper, Tickable
{
    public static final int COOLDOWN = 6;
    private int transferCooldown;
    private long lastTickTime;
    private int lastUsedIndex;
    
    public QuadrhopperBlockEntity()
    {
        super(LEntities.PENTAHOPPER_BLOCK_ENTITY, 4);
        this.transferCooldown = -1;
        lastUsedIndex = 0;
    }
    
    
    @Override public boolean isValidInvStack(int slot, ItemStack stack)
    {
        return canAcceptItemType(stack.getItem(), slot);
    }
    
    private boolean canAcceptItemType(Item item, int slot)
    {
        for(int i = 0; i < items.size(); i++)
        {
            if(items.get(i).getItem().equals(item))
            {
                if(items.get(i).getCount() < this.getInvMaxStackAmount() && slot == i)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override public void tick()
    {
        if(this.world != null && !this.world.isClient)
        {
            --this.transferCooldown;
            this.lastTickTime = this.world.getTime();
            if(!this.needsCooldown())
            {
                this.setCooldown(0);
                this.insertAndExtract(() ->
                {
                    return extract(this);
                });
            }
        }
    }
    
    private boolean insertAndExtract(Supplier<Boolean> extractMethod)
    {
        if(this.world != null && !this.world.isClient)
        {
            if(!this.needsCooldown() && this.getCachedState().get(QuadrhopperBlock.ENABLED))
            {
                boolean bl = false;
                if(!this.isInvEmpty())
                {
                    bl = this.insert();
                }
                
                if(!this.isFull())
                {
                    bl |= (Boolean) extractMethod.get();
                }
                
                if(bl)
                {
                    this.setCooldown(COOLDOWN);
                    this.markDirty();
                    return true;
                }
            }
            
            return false;
        }
        else
        {
            return false;
        }
    }
    
    public void onEntityCollided(Entity entity)
    {
        if(entity instanceof ItemEntity)
        {
            BlockPos blockPos = this.getPos();
            if(VoxelShapes.matchesAnywhere(VoxelShapes.cuboid(entity.getBoundingBox().offset((double) (-blockPos.getX()), (double) (-blockPos.getY()), (double) (-blockPos.getZ()))), this.getInputAreaShape(), BooleanBiFunction.AND))
            {
                this.insertAndExtract(() ->
                {
                    return extract(this, (ItemEntity) entity);
                });
            }
        }
    }
    
    public static boolean extract(Hopper hopper)
    {
        Inventory inventory = getInputInventory(hopper);
        if(inventory != null)
        {
            Direction direction = Direction.DOWN;
            return isInventoryEmpty(inventory, direction) ? false : getAvailableSlots(inventory, direction).anyMatch((i) ->
            {
                return extract(hopper, inventory, i, direction);
            });
        }
        else
        {
            Iterator var2 = getInputItemEntities(hopper).iterator();
            
            ItemEntity itemEntity;
            do
            {
                if(!var2.hasNext())
                {
                    return false;
                }
                
                itemEntity = (ItemEntity) var2.next();
            } while(!extract(hopper, itemEntity));
            
            return true;
        }
    }
    
    public static boolean extract(Inventory inventory, ItemEntity itemEntity)
    {
        boolean bl = false;
        ItemStack itemStack = itemEntity.getStack().copy();
        ItemStack itemStack2 = transfer((Inventory) null, inventory, itemStack, (Direction) null);
        if(itemStack2.isEmpty())
        {
            bl = true;
            itemEntity.remove();
        }
        else
        {
            itemEntity.setStack(itemStack2);
        }
        
        return bl;
    }
    
    private static boolean extract(Hopper hopper, Inventory inventory, int slot, Direction side)
    {
        ItemStack itemStack = inventory.getInvStack(slot);
        if(!itemStack.isEmpty() && canExtract(inventory, itemStack, slot, side))
        {
            ItemStack itemStack2 = itemStack.copy();
            ItemStack itemStack3 = transfer(inventory, hopper, inventory.takeInvStack(slot, 1), (Direction) null);
            if(itemStack3.isEmpty())
            {
                inventory.markDirty();
                return true;
            }
            
            inventory.setInvStack(slot, itemStack2);
        }
        
        return false;
    }
    
    private static boolean canExtract(Inventory inv, ItemStack stack, int slot, Direction facing)
    {
        return !(inv instanceof SidedInventory) || ((SidedInventory) inv).canExtractInvStack(slot, stack, facing);
    }
    
    public static List<ItemEntity> getInputItemEntities(Hopper hopper)
    {
        return (List) hopper.getInputAreaShape().getBoundingBoxes().stream().flatMap((box) ->
        {
            return hopper.getWorld().getEntities(ItemEntity.class, box.offset(hopper.getHopperX() - 0.5D, hopper.getHopperY() - 0.5D, hopper.getHopperZ() - 0.5D), EntityPredicates.VALID_ENTITY).stream();
        }).collect(Collectors.toList());
    }
    
    private static boolean isInventoryEmpty(Inventory inv, Direction facing)
    {
        return getAvailableSlots(inv, facing).allMatch((i) ->
        {
            return inv.getInvStack(i).isEmpty();
        });
    }
    
    private boolean insert()
    {
        Inventory inventory = this.getOutputInventory();
        if(inventory == null)
        {
            return false;
        }
        else
        {
            Direction direction = ((Direction) this.getCachedState().get(QuadrhopperBlock.FACING)).getOpposite();
            if(this.isInventoryFull(inventory, direction))
            {
                return false;
            }
            else
            {
                for(int i = lastUsedIndex; i < this.getInvSize(); i++)
                {
                    if(!this.getInvStack(i).isEmpty())
                    {
                        ItemStack itemStack = this.getInvStack(i).copy();
                        ItemStack itemStack2 = transfer(this, inventory, this.takeInvStack(i, 1), direction);
                        lastUsedIndex = i + 1;
                        if(itemStack2.isEmpty())
                        {
                            inventory.markDirty();
                            return true;
                        }
                        this.setInvStack(i, itemStack);
                    }
                }
                lastUsedIndex = 0;
                
                return false;
            }
        }
    }
    
    public static ItemStack transfer(@Nullable Inventory from, Inventory to, ItemStack stack, @Nullable Direction side)
    {
        if(to instanceof SidedInventory && side != null)
        {
            SidedInventory sidedInventory = (SidedInventory) to;
            int[] is = sidedInventory.getInvAvailableSlots(side);
            
            for(int i = 0; i < is.length && !stack.isEmpty(); ++i)
            {
                stack = transfer(from, to, stack, is[i], side);
            }
        }
        else
        {
            int j = to.getInvSize();
            
            for(int k = 0; k < j && !stack.isEmpty(); ++k)
            {
                stack = transfer(from, to, stack, k, side);
            }
        }
        
        return stack;
    }
    
    private static ItemStack transfer(@Nullable Inventory from, Inventory to, ItemStack stack, int slot, @Nullable Direction direction)
    {
        ItemStack itemStack = to.getInvStack(slot);
        if(canInsert(to, stack, slot, direction))
        {
            boolean bl = false;
            boolean bl2 = to.isInvEmpty();
            if(itemStack.isEmpty())
            {
                to.setInvStack(slot, stack);
                stack = ItemStack.EMPTY;
                bl = true;
            }
            else if(canMergeItems(itemStack, stack))
            {
                int i = stack.getMaxCount() - itemStack.getCount();
                int j = Math.min(stack.getCount(), i);
                stack.decrement(j);
                itemStack.increment(j);
                bl = j > 0;
            }
            
            if(bl)
            {
                if(bl2 && to instanceof QuadrhopperBlockEntity)
                {
                    QuadrhopperBlockEntity hopperBlockEntity = (QuadrhopperBlockEntity) to;
                    if(!hopperBlockEntity.isDisabled())
                    {
                        int k = 0;
                        if(from instanceof QuadrhopperBlockEntity)
                        {
                            QuadrhopperBlockEntity hopperBlockEntity2 = (QuadrhopperBlockEntity) from;
                            if(hopperBlockEntity.lastTickTime >= hopperBlockEntity2.lastTickTime)
                            {
                                k = 1;
                            }
                        }
                        hopperBlockEntity.setCooldown(COOLDOWN - k);
                    }
                }
                if(bl2 && to instanceof HopperBlockEntity)
                {
                    HopperBlockEntity hopperBlockEntity = (HopperBlockEntity) to;
                    if(!((HopperBlockEntityAccessor) hopperBlockEntity).is$Disabled())
                    {
                        int k = 0;
                        if(from instanceof HopperBlockEntity)
                        {
                            HopperBlockEntity hopperBlockEntity2 = (HopperBlockEntity) from;
                            if(((HopperBlockEntityAccessor) hopperBlockEntity).getLastTickTime() >= ((HopperBlockEntityAccessor) hopperBlockEntity2).getLastTickTime())
                            {
                                k = 1;
                            }
                        }
                        
                        ((HopperBlockEntityAccessor) hopperBlockEntity).set$Cooldown(8 - k);
                    }
                }
                to.markDirty();
            }
        }
        return stack;
    }
    
    
    private static boolean canInsert(Inventory inventory, ItemStack stack, int slot, @Nullable Direction side)
    {
        if(!inventory.isValidInvStack(slot, stack))
        {
            return false;
        }
        else
        {
            return !(inventory instanceof SidedInventory) || ((SidedInventory) inventory).canInsertInvStack(slot, stack, side);
        }
    }
    
    private static boolean canMergeItems(ItemStack first, ItemStack second)
    {
        if(first.getItem() != second.getItem())
        {
            return false;
        }
        else if(first.getDamage() != second.getDamage())
        {
            return false;
        }
        else if(first.getCount() > first.getMaxCount())
        {
            return false;
        }
        else
        {
            return ItemStack.areTagsEqual(first, second);
        }
    }
    
    private boolean isInventoryFull(Inventory inv, Direction direction)
    {
        return getAvailableSlots(inv, direction).allMatch((i) ->
        {
            ItemStack itemStack = inv.getInvStack(i);
            return itemStack.getCount() >= itemStack.getMaxCount();
        });
    }
    
    private static IntStream getAvailableSlots(Inventory inventory, Direction side)
    {
        return inventory instanceof SidedInventory ? IntStream.of(((SidedInventory) inventory).getInvAvailableSlots(side)) : IntStream.range(0, inventory.getInvSize());
    }
    
    @Nullable
    public static Inventory getInputInventory(Hopper hopper)
    {
        return getInventoryAt(hopper.getWorld(), hopper.getHopperX(), hopper.getHopperY() + 1.0D, hopper.getHopperZ());
    }
    
    @Nullable
    private Inventory getOutputInventory()
    {
        Direction direction = (Direction) this.getCachedState().get(QuadrhopperBlock.FACING);
        return getInventoryAt(this.getWorld(), this.pos.offset(direction));
    }
    
    
    @Nullable
    public static Inventory getInventoryAt(World world, BlockPos blockPos)
    {
        return getInventoryAt(world, (double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.5D, (double) blockPos.getZ() + 0.5D);
    }
    
    @Nullable
    public static Inventory getInventoryAt(World world, double x, double y, double z)
    {
        Inventory inventory = null;
        BlockPos blockPos = new BlockPos(x, y, z);
        BlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if(block instanceof InventoryProvider)
        {
            inventory = ((InventoryProvider) block).getInventory(blockState, world, blockPos);
        }
        else if(block.hasBlockEntity())
        {
            BlockEntity blockEntity = world.getBlockEntity(blockPos);
            if(blockEntity instanceof Inventory)
            {
                inventory = (Inventory) blockEntity;
                if(inventory instanceof ChestBlockEntity && block instanceof ChestBlock)
                {
                    inventory = ChestBlock.getInventory((ChestBlock) block, blockState, world, blockPos, true);
                }
            }
        }
        
        if(inventory == null)
        {
            List<Entity> list = world.getEntities((Entity) null, new Box(x - 0.5D, y - 0.5D, z - 0.5D, x + 0.5D, y + 0.5D, z + 0.5D), EntityPredicates.VALID_INVENTORIES);
            if(!list.isEmpty())
            {
                inventory = (Inventory) list.get(world.random.nextInt(list.size()));
            }
        }
        return (Inventory) inventory;
    }
    
    
    private boolean isFull()
    {
        Iterator var1 = this.items.iterator();
        
        ItemStack itemStack;
        do
        {
            if(!var1.hasNext())
            {
                return true;
            }
            
            itemStack = (ItemStack) var1.next();
        } while(!itemStack.isEmpty() && itemStack.getCount() == itemStack.getMaxCount());
        
        return false;
    }
    
    private boolean isDisabled()
    {
        return this.transferCooldown > COOLDOWN;
    }
    
    private boolean needsCooldown() { return this.transferCooldown > 0;}
    
    private void setCooldown(int cooldown)
    {
        this.transferCooldown = cooldown;
    }
    
    public double getHopperX()
    {
        return (double) this.pos.getX() + 0.5D;
    }
    
    public double getHopperY()
    {
        return (double) this.pos.getY() + 0.5D;
    }
    
    public double getHopperZ()
    {
        return (double) this.pos.getZ() + 0.5D;
    }
    
    @Override
    public void fromTag(CompoundTag tag)
    {
        super.fromTag(tag);
        this.transferCooldown = tag.getInt("TransferCooldown");
    }
    
    @Override
    public CompoundTag toTag(CompoundTag tag)
    {
        tag.putInt("TransferCooldown", this.transferCooldown);
        return super.toTag(tag);
    }
}
