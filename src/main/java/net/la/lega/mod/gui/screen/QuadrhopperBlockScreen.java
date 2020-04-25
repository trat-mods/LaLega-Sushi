package net.la.lega.mod.gui.screen;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.la.lega.mod.gui.controller.QuadrhopperBlockController;
import net.minecraft.entity.player.PlayerEntity;

public class QuadrhopperBlockScreen extends CottonInventoryScreen<QuadrhopperBlockController>
{
    public QuadrhopperBlockScreen(QuadrhopperBlockController container, PlayerEntity player)
    {
        super(container, player);
    }
}