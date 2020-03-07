package net.la.lega.mod.block;

import java.util.Random;
import java.util.function.Consumer;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.la.lega.mod.loader.LaLegaLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.Material;
import net.minecraft.block.PlantBlock;
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

public class AvocadoBlock extends PlantBlock implements Fertilizable
{
    public static final IntProperty AGE;

    public static final Identifier ID =  new Identifier(LaLegaLoader.MOD_ID, "avocado_block");
    public static final Identifier HARVEST_SOUND = new Identifier(LaLegaLoader.MOD_ID, "avocado_harvest");

    private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]
    {
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D), 
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D), 
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), 
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), 
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), 
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)
    };

    public AvocadoBlock() 
    {
        super(FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP).nonOpaque().build());
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(this.getAgeProperty(), 0));
    }

    public IntProperty getAgeProperty() 
    {
        return AGE;
    }

    public int getMaxAge() 
    {
        return 5;
    }

    protected int getAge(final BlockState state) 
    {
        return (Integer)state.get(this.getAgeProperty());
    }

    public BlockState withAge(final int age) 
    {
    return (BlockState)this.getDefaultState().with(this.getAgeProperty(), age);
    }

    public boolean isMature(final BlockState state) 
    {
    return (Integer)state.get(this.getAgeProperty()) >= this.getMaxAge();
    }

    @Override
    public boolean isFertilizable(final BlockView world, final BlockPos pos, final BlockState state, final boolean isClient) 
    {
        return !this.isMature(state);
    }

    @Override
    protected void appendProperties(final StateManager.Builder<Block, BlockState> builder) 
    {
        builder.add(AGE);
    }

    @Override
    public boolean canGrow(final World world, final Random random, final BlockPos pos, final BlockState state) 
    {
        return !this.isMature(state);
    }

    @Override
    public void grow(final ServerWorld world, final Random random, final BlockPos pos, final BlockState state) 
    {
        int i = this.getAge(state) + this.getGrowthAmount(world);
        final int j = this.getMaxAge();
        if (i > j) {
            i = j;
        }

        world.setBlockState(pos, this.withAge(i), 2);
    }

    protected int getGrowthAmount(final World world) 
    {
        return 1;
    }

    public ActionResult onUse(final BlockState state, final World world, final BlockPos pos, final PlayerEntity player, final Hand hand, final BlockHitResult hit) 
    {
        final ItemStack itemStack = player.getStackInHand(hand);
        final int currentAge = (Integer)state.get(AGE);
        boolean hasBeenHarvested = false;
        int avocadoesAmount = (currentAge == getMaxAge()) ? 3 : (currentAge == getMaxAge() - 1 ? 2 : 0);
        if (isHarvestable(state)) 
        {
            if (itemStack.getItem() == Items.SHEARS) 
            {
                world.playSound(player, player.getX(), player.getY(), player.getZ(), LaLegaLoader.AVOCADO_HARVEST_SOUNDEVENT, SoundCategory.NEUTRAL, 0.8F, 1.15F);
                dropStack(world, pos, new ItemStack(LaLegaLoader.AVOCADO, avocadoesAmount));
                itemStack.damage(1, (LivingEntity)player, (Consumer<LivingEntity>)((playerx) -> { ((LivingEntity) playerx).sendToolBreakStatus(hand); }));
                hasBeenHarvested = true;
            }
        }

        if (hasBeenHarvested) 
        {
            int newAge = currentAge == getMaxAge() ? currentAge - 2 : currentAge - 1;
            world.setBlockState(pos, (BlockState)state.with(AGE, newAge), 3);
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
           if (i < this.getMaxAge()) 
           {
              if (random.nextInt((int)8) == 0) 
              {
                 world.setBlockState(pos, this.withAge(i + 1), 2);
              }
           }
     }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView view, BlockPos pos) 
    {
        Block block = floor.getBlock();
        return block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.PODZOL;
    }

    private boolean isHarvestable(BlockState state)
    {
        return (Integer)state.get(AGE) >= getMaxAge() - 1;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos) {
        return AGE_TO_SHAPE[(Integer)state.get(this.getAgeProperty())];
     }

    static
    {
        AGE = Properties.AGE_5;
    }
}