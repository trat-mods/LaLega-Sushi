package net.la.lega.mod.loader;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.la.lega.mod.block.BlastChillerBlock;
import net.la.lega.mod.block.SushiCrafterBlock;
import net.la.lega.mod.block.ThreadCutterBlock;
import net.la.lega.mod.gui.controller.BlastChillerBlockController;
import net.la.lega.mod.gui.controller.SushiCrafterBlockController;
import net.la.lega.mod.gui.controller.ThreadCutterBlockController;
import net.la.lega.mod.gui.screen.BlastChillerBlockScreen;
import net.la.lega.mod.gui.screen.SushiCrafterBlockScreen;
import net.la.lega.mod.gui.screen.ThreadCutterBlockScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.container.BlockContext;

public class ClientLoader implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        ScreenProviderRegistry.INSTANCE.registerFactory(
            BlastChillerBlock.ID, 
            (syncId, id, player, buf) -> new BlastChillerBlockScreen( 
                new BlastChillerBlockController(syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos())), 
                player)
        );

        ScreenProviderRegistry.INSTANCE.registerFactory(
            ThreadCutterBlock.ID,
            (syncId, id, player, buf) -> new ThreadCutterBlockScreen( 
                new ThreadCutterBlockController(syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos())), 
                player)
        );

        ScreenProviderRegistry.INSTANCE.registerFactory(
            SushiCrafterBlock.ID,
            (syncId, id, player, buf) -> new SushiCrafterBlockScreen( 
                new SushiCrafterBlockController(syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos())), 
                player)
        );

        BlockRenderLayerMap.INSTANCE.putBlock(LaLegaLoader.RICE_BLOCK, RenderLayer.getTranslucent());
    }
}