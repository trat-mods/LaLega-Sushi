package net.la.lega.mod.initializer;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.la.lega.mod.block.*;
import net.la.lega.mod.gui.controller.*;
import net.minecraft.container.BlockContext;

public abstract class LUIControllers
{
    public static void initialize()
    {
        ContainerProviderRegistry.INSTANCE.registerFactory(BlastChillerBlock.ID, (syncId, id, player, buf) -> new BlastChillerBlockController(syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos())));
        ContainerProviderRegistry.INSTANCE.registerFactory(ThreadCutterBlock.ID, (syncId, id, player, buf) -> new ThreadCutterBlockController(syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos())));
        ContainerProviderRegistry.INSTANCE.registerFactory(SushiCrafterBlock.ID, (syncId, id, player, buf) -> new SushiCrafterBlockController(syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos())));
        ContainerProviderRegistry.INSTANCE.registerFactory(FryerBlock.ID, (syncId, id, player, buf) -> new FryerBlockController(syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos())));
        ContainerProviderRegistry.INSTANCE.registerFactory(SteamCookerBlock.ID, (syncId, id, player, buf) -> new SteamCookerBlockController(syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos())));
    }
}