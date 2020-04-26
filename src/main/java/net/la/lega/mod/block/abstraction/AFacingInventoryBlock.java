package net.la.lega.mod.block.abstraction;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public abstract class AFacingInventoryBlock extends AInventoryBlock
{
    public static final DirectionProperty FACING;
    
    static
    {
        FACING = FacingBlock.FACING;
    }
    
    public AFacingInventoryBlock(Settings settings)
    {
        super(settings);
        this.setDefaultState((BlockState) ((BlockState) ((BlockState) this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)));
    }
    
    @Override public BlockEntity createBlockEntity(BlockView view)
    {
        return null;
    }
    
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
    {
        stateManager.add(FACING);
    }
    
    public BlockState getPlacementState(ItemPlacementContext ctx)
    {
        return (BlockState) this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
    }
    
    public BlockState rotate(BlockState state, BlockRotation rotation)
    {
        return (BlockState) state.with(FACING, rotation.rotate((Direction) state.get(FACING)));
    }
    
    public BlockState mirror(BlockState state, BlockMirror mirror)
    {
        return state.rotate(mirror.getRotation((Direction) state.get(FACING)));
    }
}
