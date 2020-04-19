package net.la.lega.mod.gui.screen;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.la.lega.mod.gui.controller.PressBlockController;
import net.minecraft.entity.player.PlayerEntity;

public class PressBlockScreen extends CottonInventoryScreen<PressBlockController>
{
    public PressBlockScreen(PressBlockController container, PlayerEntity player)
    {
        super(container, player);
    }
}