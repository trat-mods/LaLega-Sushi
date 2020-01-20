package net.la.lega.mod.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.la.lega.mod.entity.BlastChillerBlockEntity;
import net.la.lega.mod.loader.LaLegaLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class BlastChillerBlock extends BlockWithEntity 
{
    public static final Identifier ID = new Identifier("lalegamod", "blast_chiller_block");

    public BlastChillerBlock() 
    {
        super(FabricBlockSettings.of(Material.METAL).breakByHand((true)).sounds(BlockSoundGroup.METAL).strength(0.8F, 0.5F).build());
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

     
     public void onBlockRemoved(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) 
     {
        if (state.getBlock() != newState.getBlock()) 
        {
           BlockEntity blockEntity = world.getBlockEntity(pos);
           if (blockEntity instanceof BlastChillerBlockEntity) 
           {
              ItemScatterer.spawn(world, (BlockPos)pos, (Inventory)((BlastChillerBlockEntity)blockEntity));
              world.updateHorizontalAdjacent(pos, this);
           }
  
           super.onBlockRemoved(state, world, pos, newState, moved);
        }
     }
}