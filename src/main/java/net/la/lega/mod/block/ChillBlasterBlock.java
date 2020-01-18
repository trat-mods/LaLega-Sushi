package net.la.lega.mod.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.la.lega.mod.entity.ChillBlasterBlockEntity;
import net.la.lega.mod.loader.LaLegaLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class ChillBlasterBlock extends BlockWithEntity 
{
    public static final Identifier ID = new Identifier("lalegamod", "chill_blaster_block");

    public ChillBlasterBlock() 
    {
        super(FabricBlockSettings.of(Material.METAL).breakByHand((true)).sounds(BlockSoundGroup.METAL).strength(0.8F, 0.5F).build());
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) 
    {
        return new ChillBlasterBlockEntity();
    }

    // public void openContainer(BlockPos pos, PlayerEntity playerEntity, Text name)
    // {
    //     ContainerProviderRegistry.INSTANCE.openContainer(LaLegaLoader.CHILL_BLASTER_CONTAINER_ID, playerEntity, (packetByteBuf -> {
	// 		packetByteBuf.writeText(name);
	// 	}));
    // }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) 
    {
        if (world.isClient) return ActionResult.PASS;

		BlockEntity be = world.getBlockEntity(pos);
		if (be!=null && be instanceof ChillBlasterBlockEntity) {
			ContainerProviderRegistry.INSTANCE.openContainer(ID, player, (packetByteBuf -> packetByteBuf.writeBlockPos(pos)));
		}
		return ActionResult.SUCCESS;
     }
}