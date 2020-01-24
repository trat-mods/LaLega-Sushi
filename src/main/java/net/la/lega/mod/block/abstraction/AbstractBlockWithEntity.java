package net.la.lega.mod.block.abstraction;

import net.la.lega.mod.entity.abstraction.AbstractProcessingOutputterEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.container.Container;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public abstract class AbstractBlockWithEntity extends BlockWithEntity 
{

    protected AbstractBlockWithEntity(Settings settings) 
    {
        super(settings);
    }

    public abstract BlockEntity createBlockEntity(BlockView view);

    public void onBlockRemoved(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) 
    {
        if (state.getBlock() != newState.getBlock()) 
        {
           BlockEntity blockEntity = world.getBlockEntity(pos);
           if (blockEntity instanceof AbstractProcessingOutputterEntity) 
           {
              ItemScatterer.spawn(world, (BlockPos)pos, (Inventory)((AbstractProcessingOutputterEntity)blockEntity));
              world.updateHorizontalAdjacent(pos, this);
           }
           super.onBlockRemoved(state, world, pos, newState, moved);
        }
     }

    public boolean hasComparatorOutput(BlockState state)
    {
        return true;
    }
  
    public int getComparatorOutput(BlockState state, World world, BlockPos pos)
    {
        return Container.calculateComparatorOutput(world.getBlockEntity(pos));
    }

    public BlockRenderType getRenderType(BlockState state) 
    {
        return BlockRenderType.MODEL;
    }
}