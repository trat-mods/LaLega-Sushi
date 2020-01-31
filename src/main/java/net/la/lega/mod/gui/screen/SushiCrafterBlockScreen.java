package net.la.lega.mod.gui.screen;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.la.lega.mod.gui.controller.SushiCrafterBlockController;
import net.minecraft.entity.player.PlayerEntity;

public class SushiCrafterBlockScreen extends CottonInventoryScreen<SushiCrafterBlockController> 
{
    public SushiCrafterBlockScreen(SushiCrafterBlockController container, PlayerEntity player) 
    {
        super(container, player);
    }
}