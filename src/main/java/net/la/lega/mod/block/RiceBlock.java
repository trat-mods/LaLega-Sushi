package net.la.lega.mod.block;

import java.util.Iterator;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.la.lega.mod.loader.LaLegaLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityContext;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemConvertible;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

public class RiceBlock extends CropBlock
{
    public static final Identifier ID =  new Identifier(LaLegaLoader.MOD_ID, "rice_block");

    public RiceBlock()
    {
        super(FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP).nonOpaque().build());
    }
    
    private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D), 
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D), 
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D), 
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D), 
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D), 
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D), 
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D), 
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D)};
 
    @Environment(EnvType.CLIENT)
    protected ItemConvertible getSeedsItem() 
    {
       return LaLegaLoader.RICE_SEEDS;
    }
 
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos) {
       return AGE_TO_SHAPE[(Integer)state.get(this.getAgeProperty())];
    }

    @Override 
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) 
    {
        Block block = world.getBlockState(pos.down()).getBlock();
        if (block == Blocks.GRASS_BLOCK || block == Blocks.DIRT) 
        {
            BlockPos blockPos = pos.down();
            Iterator<Direction> horizontalIterator = Direction.Type.HORIZONTAL.iterator();

            while(horizontalIterator.hasNext()) 
            {
                Direction direction = (Direction)horizontalIterator.next();
                BlockState blockState = world.getBlockState(blockPos.offset(direction));
                FluidState fluidState = world.getFluidState(blockPos.offset(direction));
                if (fluidState.matches(FluidTags.WATER) || blockState.getBlock() == Blocks.FROSTED_ICE) 
                {
                    return true;
                }
            }
        }
        return false;
    }
}