package net.la.lega.mod.loader;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.la.lega.mod.block.ChillBlasterBlock;
import net.la.lega.mod.gui.ChillBlasterBlockController;
import net.la.lega.mod.gui.ChillBlasterBlockScreen;
import net.minecraft.container.BlockContext;

public class ClientLoader implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        ScreenProviderRegistry.INSTANCE.registerFactory(
            ChillBlasterBlock.ID, 
            (syncId, id, player, buf) -> new ChillBlasterBlockScreen( 
                new ChillBlasterBlockController(syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos())), 
                player)
        );
    }
}