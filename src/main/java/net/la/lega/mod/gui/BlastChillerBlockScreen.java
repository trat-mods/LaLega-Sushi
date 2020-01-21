package net.la.lega.mod.gui;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;

public class BlastChillerBlockScreen extends CottonInventoryScreen<BlastChillerBlockController> {

    public BlastChillerBlockScreen(BlastChillerBlockController container, PlayerEntity player) {
        super(container, player);
    }
}