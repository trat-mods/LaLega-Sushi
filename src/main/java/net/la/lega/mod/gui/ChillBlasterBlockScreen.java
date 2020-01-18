package net.la.lega.mod.gui;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;

public class ChillBlasterBlockScreen extends CottonInventoryScreen<ChillBlasterBlockController> {

    public ChillBlasterBlockScreen(ChillBlasterBlockController container, PlayerEntity player) {
        super(container, player);
    }

}