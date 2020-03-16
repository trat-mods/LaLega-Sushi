package net.la.lega.mod.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.la.lega.mod.initializer.LItems;
import net.la.lega.mod.initializer.LSounds;
import net.la.lega.mod.loader.LLoader;
import net.minecraft.block.*;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Consumer;

public class AvocadoBlock extends PlantBlock implements Fertilizable
{
    public static final IntProperty AGE;
    public static final Identifier ID = new Identifier(LLoader.MOD_ID, "avocado_block");
    public static final Identifier HARVEST_SOUND = new Identifier(LLoader.MOD_ID, "avocado_harvest");
    
    private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{
          Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D),
          Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D),
          Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
          Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
          Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
          Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};
    
    public AvocadoBlock()
    {
        super(FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP).nonOpaque().build());
        Random random = new Random();
        this.setDefaultState((BlockState) ((BlockState) this.stateManager.getDefaultState()).with(AGE, random.nextInt(getMaxAge())));
    }
    
    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView view, BlockPos pos)
    {
        Block block = floor.getBlock();
        return block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL;
    }
    
    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack)
    {
        setAgeState(world, pos, 0);
        super.onPlaced(world, pos, state, placer, itemStack);
    }
    
    public int getMaxAge()
    {
        return 5;
    }
    
    protected int getAge(final BlockState state)
    {
        return (Integer) state.get(AGE);
    }
    
    public BlockState withAge(final int age)
    {
        return (BlockState) this.getDefaultState().with(AGE, age);
    }
    
    public boolean isMature(final BlockState state)
    {
        return (Integer) state.get(AGE) >= this.getMaxAge();
    }
    
    public boolean isFertilizable(final BlockView world, final BlockPos pos, final BlockState state, final boolean isClient)
    {
        return !this.isMature(state);
    }
    
    @Override
    protected void appendProperties(final StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(AGE);
    }
    
    public boolean canGrow(final World world, final Random random, final BlockPos pos, final BlockState state)
    {
        return !this.isMature(state);
    }
    
    public void grow(final ServerWorld world, final Random random, final BlockPos pos, final BlockState state)
    {
        int i = this.getAge(state) + this.getGrowthAmount(world);
        final int j = this.getMaxAge();
        if(i > j)
        {
            i = j;
        }
        setAgeState(world, pos, i);
    }
    
    public void setAgeState(World world, BlockPos pos, int age)
    {
        if(!world.isClient)
        {
            world.setBlockState(pos, this.withAge(age), 0B1011);
        }
    }
    
    protected int getGrowthAmount(final World world)
    {
        return 1;
    }
    
    public int dropAvocadoes(final BlockState state, final World world, final BlockPos pos)
    {
        final int currentAge = getAge(state);
        int avocadoesAmount = (currentAge == getMaxAge()) ? 4 : (currentAge == getMaxAge() - 1 ? 2 : 0);
        if(avocadoesAmount > 0)
        {
            dropStack(world, pos, new ItemStack(LItems.AVOCADO, avocadoesAmount));
        }
        return avocadoesAmount;
    }
    
    public ActionResult onUse(final BlockState state, final World world, final BlockPos pos, final PlayerEntity player, final Hand hand, final BlockHitResult hit)
    {
        final ItemStack itemStack = player.getStackInHand(hand);
        boolean hasBeenHarvested = false;
        if(isHarvestable(state))
        {
            if(itemStack.getItem() == Items.SHEARS)
            {
                world.playSound(player, player.getX(), player.getY(), player.getZ(), LSounds.AVOCADO_HARVEST_SOUNDEVENT, SoundCategory.NEUTRAL, 0.8F, 1.15F);
                dropAvocadoes(state, world, pos);
                itemStack.damage(1, (LivingEntity) player, (Consumer<LivingEntity>) ((playerx) ->
                {
                    ((LivingEntity) playerx).sendToolBreakStatus(hand);
                }));
                hasBeenHarvested = true;
            }
        }
        
        if(hasBeenHarvested)
        {
            setAgeState(world, pos, 3);
            return ActionResult.SUCCESS;
        }
        else
        {
            return super.onUse(state, world, pos, player, hand, hit);
        }
    }
    
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
        super.scheduledTick(state, world, pos, random);
        int i = this.getAge(state);
        if(i < this.getMaxAge())
        {
            if(random.nextInt((int) 8) == 0)
            {
                setAgeState(world, pos, i + 1);
            }
        }
    }
    
    private boolean isHarvestable(BlockState state)
    {
        return (Integer) state.get(AGE) >= getMaxAge() - 1;
    }
    
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos)
    {
        return AGE_TO_SHAPE[(Integer) state.get(AGE)];
    }
    
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player)
    {
        if(getAge(state) > 3)
        {
            dropAvocadoes(state, world, pos);
        }
        
        super.onBreak(world, pos, state, player);
        return;
    }
    
    public boolean hasComparatorOutput(BlockState state)
    {
        return true;
    }
    
    public int getComparatorOutput(BlockState state, World world, BlockPos pos)
    {
        return state.get(AGE);
    }
    
    static
    {
        AGE = Properties.AGE_5;
    }
}