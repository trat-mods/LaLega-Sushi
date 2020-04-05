package net.la.lega.mod.initializer;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.la.lega.mod.block.BlastChillerBlock;
import net.la.lega.mod.block.FryerBlock;
import net.la.lega.mod.block.SushiCrafterBlock;
import net.la.lega.mod.block.ThreadCutterBlock;
import net.la.lega.mod.gui.controller.BlastChillerBlockController;
import net.la.lega.mod.gui.controller.FryerBlockController;
import net.la.lega.mod.gui.controller.SushiCrafterBlockController;
import net.la.lega.mod.gui.controller.ThreadCutterBlockController;
import net.minecraft.container.BlockContext;

public abstract class LUIControllers
{
    public static void initialize()
    {
        ContainerProviderRegistry.INSTANCE.registerFactory(BlastChillerBlock.ID, (syncId, id, player, buf) -> new BlastChillerBlockController(syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos())));
        ContainerProviderRegistry.INSTANCE.registerFactory(ThreadCutterBlock.ID, (syncId, id, player, buf) -> new ThreadCutterBlockController(syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos())));
        ContainerProviderRegistry.INSTANCE.registerFactory(SushiCrafterBlock.ID, (syncId, id, player, buf) -> new SushiCrafterBlockController(syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos())));
        ContainerProviderRegistry.INSTANCE.registerFactory(FryerBlock.ID, (syncId, id, player, buf) -> new FryerBlockController(syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos())));
    }
}