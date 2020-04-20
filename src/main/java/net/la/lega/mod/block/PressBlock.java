package net.la.lega.mod.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.la.lega.mod.block.abstraction.AHorizontalFacingInventoryBlock;
import net.la.lega.mod.entity.PressBlockEntity;
import net.la.lega.mod.loader.LLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class PressBlock extends AHorizontalFacingInventoryBlock
{
    public static final Identifier ID = new Identifier(LLoader.MOD_ID, "press");
    public static final IntProperty MODELS = IntProperty.of("models", 0, 1);
    public static final Identifier ACTIVATE_SOUND = new Identifier(LLoader.MOD_ID, "press_activate");
    
    public PressBlock()
    {
        super(FabricBlockSettings.of(Material.METAL).breakByHand((true)).sounds(BlockSoundGroup.METAL).strength(1F, 1F).nonOpaque().build());
        this.setDefaultState(this.stateManager.getDefaultState().with(MODELS, 0));
    }
    
    @Override public BlockEntity createBlockEntity(BlockView view)
    {
        return new PressBlockEntity();
    }
    
    @Override protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
    {
        super.appendProperties(stateManager);
        stateManager.add(MODELS);
    }
    
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
    {
        if(world.isClient) return ActionResult.SUCCESS;
        else
        {
            BlockEntity be = world.getBlockEntity(pos);
            if(be instanceof PressBlockEntity)
            {
                ContainerProviderRegistry.INSTANCE.openContainer(ID, player, (packetByteBuf -> packetByteBuf.writeBlockPos(pos)));
            }
            return ActionResult.SUCCESS;
        }
    }
}
