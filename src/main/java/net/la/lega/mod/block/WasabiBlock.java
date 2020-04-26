package net.la.lega.mod.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.la.lega.mod.initializer.LItems;
import net.la.lega.mod.loader.LLoader;
import net.minecraft.block.*;
import net.minecraft.entity.EntityContext;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Iterator;
import java.util.Random;

public class WasabiBlock extends CropBlock
{
    public static final Identifier ID = new Identifier(LLoader.MOD_ID, "wasabi_block");
    
    public WasabiBlock()
    {
        super(FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP).nonOpaque().build());
        this.setDefaultState(this.stateManager.getDefaultState().with(this.getAgeProperty(), 0));
    }
    
    private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{
          Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
          Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
          Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
          Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
          Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
          Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),};
    
    @Environment(EnvType.CLIENT)
    protected ItemConvertible getSeedsItem()
    {
        return LItems.WASABI_ROOT;
    }
    
    @Override
    public int getMaxAge()
    {
        return 5;
    }
    
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos)
    {
        return AGE_TO_SHAPE[(Integer) state.get(this.getAgeProperty())];
    }
    
    @Override
    protected int getGrowthAmount(World world)
    {
        return 1;
    }
    
    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
        if(world.getBaseLightLevel(pos, 0) >= 8)
        {
            int i = this.getAge(state);
            if(i < this.getMaxAge())
            {
                float f = getAvailableMoisture(this, world, pos);
                if(random.nextInt((int) (32.0F / f) + 1) == 0)
                {
                    world.setBlockState(pos, this.withAge(i + 1), 2);
                }
            }
        }
    }
    
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos)
    {
        Block block = world.getBlockState(pos.down()).getBlock();
        if((block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.PODZOL || block == Blocks.FARMLAND) && (world.getBlockState(pos.up()).getBlock() == Blocks.AIR))
        {
            BlockPos blockPos = pos.down();
            Iterator<Direction> horizontalIterator = Direction.Type.HORIZONTAL.iterator();
            
            while(horizontalIterator.hasNext())
            {
                Direction direction = (Direction) horizontalIterator.next();
                BlockState blockState = world.getBlockState(blockPos.offset(direction));
                FluidState fluidState = world.getFluidState(blockPos.offset(direction));
                if(fluidState.matches(FluidTags.WATER) || blockState.getBlock() == Blocks.FROSTED_ICE)
                {
                    return true;
                }
            }
        }
        return false;
    }
}
