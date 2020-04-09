package net.la.lega.mod.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.la.lega.mod.block.abstraction.AFacingInventoryBlock;
import net.la.lega.mod.entity.ThreadCutterBlockEntity;
import net.la.lega.mod.loader.LLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class ThreadCutterBlock extends AFacingInventoryBlock
{
    public static final Identifier ID = new Identifier(LLoader.MOD_ID, "thread_cutter");
    public static final Identifier CUT_SOUND = new Identifier(LLoader.MOD_ID, "thread_cutter_cut");
    
    public ThreadCutterBlock()
    {
        super(FabricBlockSettings.of(Material.METAL).breakByHand((true)).sounds(BlockSoundGroup.METAL).strength(0.8F, 0.8F).build());
    }
    
    @Override
    public BlockEntity createBlockEntity(BlockView view)
    {
        return new ThreadCutterBlockEntity();
    }
    
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
    {
        if(world.isClient) return ActionResult.SUCCESS;
        
        BlockEntity be = world.getBlockEntity(pos);
        if(be != null && be instanceof ThreadCutterBlockEntity)
        {
            ContainerProviderRegistry.INSTANCE.openContainer(ID, player, (packetByteBuf -> packetByteBuf.writeBlockPos(pos)));
        }
        return ActionResult.SUCCESS;
    }
}