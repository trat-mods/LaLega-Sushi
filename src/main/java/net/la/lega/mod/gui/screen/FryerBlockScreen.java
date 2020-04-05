package net.la.lega.mod.gui.screen;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.la.lega.mod.gui.controller.FryerBlockController;
import net.minecraft.entity.player.PlayerEntity;

public class FryerBlockScreen extends CottonInventoryScreen<FryerBlockController>
{
    public FryerBlockScreen(FryerBlockController container, PlayerEntity player)
    {
        super(container, player);
    }
}