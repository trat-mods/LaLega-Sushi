package net.la.lega.mod.gui.screen;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.la.lega.mod.gui.controller.PentahopperBlockController;
import net.minecraft.entity.player.PlayerEntity;

public class PentahopperBlockScreen extends CottonInventoryScreen<PentahopperBlockController>
{
    public PentahopperBlockScreen(PentahopperBlockController container, PlayerEntity player)
    {
        super(container, player);
    }
}