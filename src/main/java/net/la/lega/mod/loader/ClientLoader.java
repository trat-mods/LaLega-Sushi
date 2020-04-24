package net.la.lega.mod.loader;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.la.lega.mod.block.*;
import net.la.lega.mod.entity_renderer.PressBlockEntityRenderer;
import net.la.lega.mod.gui.controller.*;
import net.la.lega.mod.gui.screen.*;
import net.la.lega.mod.initializer.LBlocks;
import net.la.lega.mod.initializer.LEntities;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.container.BlockContext;

public final class ClientLoader implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        initializeScreens();
        setBlocksRenderLayer();
        initializeBlockEntityRenderers();
    }
    
    private void initializeBlockEntityRenderers()
    {
        BlockEntityRendererRegistry.INSTANCE.register(LEntities.PRESS_BLOCK_ENTITY, PressBlockEntityRenderer::new);
    }
    
    private void initializeScreens()
    {
        ScreenProviderRegistry.INSTANCE.registerFactory(
              BlastChillerBlock.ID,
              (syncId, id, player, buf) -> new BlastChillerBlockScreen(
                    new BlastChillerBlockController(syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos())),
                    player));
        
        ScreenProviderRegistry.INSTANCE.registerFactory(
              ThreadCutterBlock.ID,
              (syncId, id, player, buf) -> new ThreadCutterBlockScreen(
                    new ThreadCutterBlockController(syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos())),
                    player));
        
        ScreenProviderRegistry.INSTANCE.registerFactory(
              SushiCrafterBlock.ID,
              (syncId, id, player, buf) -> new SushiCrafterBlockScreen(
                    new SushiCrafterBlockController(syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos())),
                    player));
        
        ScreenProviderRegistry.INSTANCE.registerFactory(
              FryerBlock.ID,
              (syncId, id, player, buf) -> new FryerBlockScreen(
                    new FryerBlockController(syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos())),
                    player));
        
        ScreenProviderRegistry.INSTANCE.registerFactory(
              SteamCookerBlock.ID,
              (syncId, id, player, buf) -> new SteamCookerBlockScreen(
                    new SteamCookerBlockController(syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos())),
                    player));
        
        ScreenProviderRegistry.INSTANCE.registerFactory(
              PressBlock.ID,
              (syncId, id, player, buf) -> new PressBlockScreen(
                    new PressBlockController(syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos())),
                    player));
        
        ScreenProviderRegistry.INSTANCE.registerFactory(
              PentahopperBlock.ID,
              (syncId, id, player, buf) -> new PentahopperBlockScreen(
                    new PentahopperBlockController(syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos())),
                    player));
    }
    
    private void setBlocksRenderLayer()
    {
        BlockRenderLayerMap.INSTANCE.putBlock(LBlocks.RICE_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(LBlocks.AVOCADO_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(LBlocks.WASABI_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(LBlocks.STEAM_COOKER_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(LBlocks.PRESS_BLOCK, RenderLayer.getTranslucent());
    }
}