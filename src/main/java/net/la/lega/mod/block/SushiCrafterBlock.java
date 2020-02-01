package net.la.lega.mod.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.la.lega.mod.block.abstraction.AbstractProcessingOutputterBlock;
import net.la.lega.mod.entity.SushiCrafterBlockEntity;
import net.la.lega.mod.loader.LaLegaLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.block.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class SushiCrafterBlock extends AbstractProcessingOutputterBlock
{
    public static final Identifier ID = new Identifier(LaLegaLoader.MOD_ID, "sushi_crafter"); 
    public static final DirectionProperty FACING;
    static
    {
        FACING = HorizontalFacingBlock.FACING;
    }

    public SushiCrafterBlock() 
    {
        super(FabricBlockSettings.of(Material.METAL).breakByHand((true)).sounds(BlockSoundGroup.METAL).strength(1F, 1F).nonOpaque().build());
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) 
    {
        return new SushiCrafterBlockEntity();
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) 
    {
        if (world.isClient) return ActionResult.SUCCESS;

		BlockEntity be = world.getBlockEntity(pos);
        if (be!=null && be instanceof SushiCrafterBlockEntity) 
        {
			ContainerProviderRegistry.INSTANCE.openContainer(ID, player, (packetByteBuf -> packetByteBuf.writeBlockPos(pos)));
		}
		return ActionResult.SUCCESS;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) 
    {
        stateManager.add(FACING);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx)
    {
        return (BlockState)this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) 
    {
        return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
    }
  
    public BlockState mirror(BlockState state, BlockMirror mirror) 
    {
        return state.rotate(mirror.getRotation((Direction)state.get(FACING)));
    }   
}