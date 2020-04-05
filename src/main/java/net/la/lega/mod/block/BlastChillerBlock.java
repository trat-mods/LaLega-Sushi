package net.la.lega.mod.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.la.lega.mod.block.abstraction.AHorizontalFacingProcessingBlock;
import net.la.lega.mod.entity.BlastChillerBlockEntity;
import net.la.lega.mod.initializer.LSounds;
import net.la.lega.mod.loader.LLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class BlastChillerBlock extends AHorizontalFacingProcessingBlock
{
    public static final Identifier ID = new Identifier(LLoader.MOD_ID, "blast_chiller");
    public static final Identifier HUM_SOUND = new Identifier(LLoader.MOD_ID, "blast_chiller_hum");
    
    public static final BooleanProperty ON;
    
    static
    {
        ON = BooleanProperty.of("on");
    }
    
    public BlastChillerBlock()
    {
        super(FabricBlockSettings.of(Material.METAL).breakByHand((true)).sounds(BlockSoundGroup.METAL).strength(0.5F, 0.5F).nonOpaque().build());
        this.setDefaultState((BlockState) ((BlockState) ((BlockState) this.stateManager.getDefaultState()).with(ON, false)));
    }
    
    @Override
    public BlockEntity createBlockEntity(BlockView view)
    {
        return new BlastChillerBlockEntity();
    }
    
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
    {
        if(world.isClient) return ActionResult.SUCCESS;
        
        BlockEntity be = world.getBlockEntity(pos);
        if(be instanceof BlastChillerBlockEntity)
        {
            ContainerProviderRegistry.INSTANCE.openContainer(ID, player, (packetByteBuf -> packetByteBuf.writeBlockPos(pos)));
        }
        return ActionResult.SUCCESS;
    }
    
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
    {
        super.appendProperties(stateManager);
        stateManager.add(ON);
    }
    
    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
    {
        if((Boolean) state.get(ON))
        {
            double d = (double) pos.getX() + 0.5D;
            double e = (double) pos.getY();
            double f = (double) pos.getZ() + 0.5D;
            if(random.nextDouble() < 0.15D)
            {
                world.playSound(d, e, f, LSounds.BLAST_CHILLER_HUM_SOUNDEVENT, SoundCategory.BLOCKS, 0.75F, 1.1F, false);
            }
        }
    }
}