package net.la.lega.mod.loader;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.la.lega.mod.block.BlastChillerBlock;
import net.la.lega.mod.gui.BlastChillerBlockController;
import net.la.lega.mod.gui.BlastChillerBlockScreen;
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
    }
}