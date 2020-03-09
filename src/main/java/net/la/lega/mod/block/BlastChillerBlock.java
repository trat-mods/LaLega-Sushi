package net.la.lega.mod.block;

import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.la.lega.mod.block.abstraction.AbstractProcessingOutputterBlock;
import net.la.lega.mod.entity.BlastChillerBlockEntity;
import net.la.lega.mod.initializer.LSounds;
import net.la.lega.mod.loader.LLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
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

public class BlastChillerBlock extends AbstractProcessingOutputterBlock
{
    public static final Identifier ID = new Identifier(LLoader.MOD_ID, "blast_chiller");
    public static final Identifier HUM_SOUND = new Identifier(LLoader.MOD_ID, "blast_chiller_hum");

    public static final BooleanProperty ON;    
    public static final DirectionProperty FACING;
    static
    {
        ON = BooleanProperty.of("on");
        FACING = HorizontalFacingBlock.FACING;
    }


    public BlastChillerBlock() 
    {
        super(FabricBlockSettings.of(Material.METAL).breakByHand((true)).sounds(BlockSoundGroup.METAL).strength(0.5F, 0.5F).nonOpaque().build());
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(ON, false).with(FACING, Direction.NORTH)));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) 
    {
        return new BlastChillerBlockEntity();
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) 
    {
        if (world.isClient) return ActionResult.SUCCESS;

		BlockEntity be = world.getBlockEntity(pos);
        if (be!=null && be instanceof BlastChillerBlockEntity) 
        {
			ContainerProviderRegistry.INSTANCE.openContainer(ID, player, (packetByteBuf -> packetByteBuf.writeBlockPos(pos)));
		}
		return ActionResult.SUCCESS;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) 
    {
        stateManager.add(ON).add(FACING);
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

    
    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) 
    {
        if((Boolean)state.get(ON)) 
        {
            double d = (double)pos.getX() + 0.5D;
            double e = (double)pos.getY();
            double f = (double)pos.getZ() + 0.5D;
            if (random.nextDouble() < 0.15D) 
            {
               world.playSound(d, e, f, LSounds.BLAST_CHILLER_HUM_SOUNDEVENT, SoundCategory.BLOCKS, 0.75F, 1.1F, false);
            }
        }
    }
}