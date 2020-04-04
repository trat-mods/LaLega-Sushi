package net.la.lega.mod.entity;

import net.la.lega.mod.block.ThreadCutterBlock;
import net.la.lega.mod.entity.abstraction.AbstractProcessingOutputterEntity;
import net.la.lega.mod.initializer.LEntities;
import net.la.lega.mod.initializer.LSounds;
import net.la.lega.mod.recipe.ThreadCuttingRecipe;
import net.la.lega.mod.recipe.abstraction.AbstractInjectiveProcessingRecipe;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

public class ThreadCutterBlockEntity extends AbstractProcessingOutputterEntity
{
    public static int[] NONE = {};
    public static int[] IN_SLOTS = {0};
    
    public ThreadCutterBlockEntity()
    {
        super(LEntities.THREAD_CUTTER_BLOCK_ENTITY, 1);
    }
    
    @Override
    public void tick()
    {
        if(!this.world.isClient)
        {
            ThreadCuttingRecipe recipe = this.world.getRecipeManager().getFirstMatch(ThreadCuttingRecipe.Type.INSTANCE, this, world).orElse(null);
            checkCurrentRecipe(recipe);
            processCurrentRecipe();
        }
    }
    
    @Override
    public int[] getInvAvailableSlots(Direction side)
    {
        if(side == Direction.UP || side != Direction.DOWN)
        {
            return IN_SLOTS;
        }
        return NONE;
    }
    
    @Override
    public boolean isValidInvStack(int slot, ItemStack stack)
    {
        return slot == 0;
    }
    
    @Override
    public boolean canInsertInvStack(int slot, ItemStack stack, Direction dir)
    {
        return dir != Direction.DOWN && this.isValidInvStack(slot, stack);
    }
    
    @Override
    public boolean canExtractInvStack(int slot, ItemStack stack, Direction dir)
    {
        return false;
    }
    
    @Override
    protected boolean canAcceptRecipeOutput(Recipe<?> recipe)
    {
        if(!((ItemStack) this.items.get(0)).isEmpty() && recipe != null)
        {
            ItemStack itemStack = recipe.getOutput();
            if(itemStack.isEmpty())
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }
    
    @Override
    protected void craftRecipe(Recipe<?> recipe)
    {
        AbstractInjectiveProcessingRecipe tCRecipe = (AbstractInjectiveProcessingRecipe) recipe;
        if(tCRecipe != null && this.canAcceptRecipeOutput(tCRecipe))
        {
            ItemStack inputSlot = (ItemStack) this.items.get(0);
            BlockPointerImpl blockPointerImpl = new BlockPointerImpl(world, pos);
            
            Direction direction = (Direction) blockPointerImpl.getBlockState().get(ThreadCutterBlock.FACING);
            Position position = getOutputLocation(blockPointerImpl);
            ItemStack output = tCRecipe.craft(this);
            inputSlot.decrement(1);
            
            spawnItem(blockPointerImpl.getWorld(), output.copy(), 6, direction, position);
            world.playSound(null, getPos(), LSounds.THREAD_CUTTER_CUT_SOUNDEVENT, SoundCategory.BLOCKS, 0.045F, 0.85F);
        }
    }
    
    public static Position getOutputLocation(BlockPointer pointer)
    {
        Direction direction = (Direction) pointer.getBlockState().get(ThreadCutterBlock.FACING);
        double d = pointer.getX() + 0.7D * (double) direction.getOffsetX();
        double e = pointer.getY() + 0.7D * (double) direction.getOffsetY();
        double f = pointer.getZ() + 0.7D * (double) direction.getOffsetZ();
        return new PositionImpl(d, e, f);
    }
    
    public static void spawnItem(World world, ItemStack stack, int offset, Direction side, Position pos)
    {
        double d = pos.getX();
        double e = pos.getY();
        double f = pos.getZ();
        if(side.getAxis() == Direction.Axis.Y)
        {
            e -= 0.125D;
        }
        else
        {
            e -= 0.15625D;
        }
        ItemEntity itemEntity = new ItemEntity(world, d, e, f, stack);
        double g = world.random.nextDouble() * 0.1D + 0.2D;
        itemEntity.setVelocity(world.random.nextGaussian() * 0.007499999832361937D * (double) offset + (double) side.getOffsetX() * g, world.random.nextGaussian() * 0.007499999832361937D * (double) offset + 0.20000000298023224D, world.random.nextGaussian() * 0.007499999832361937D * (double) offset + (double) side.getOffsetZ() * g);
        world.spawnEntity(itemEntity);
    }
}