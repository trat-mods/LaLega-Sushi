package net.la.lega.mod.gui.screen;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.la.lega.mod.gui.controller.BlastChillerBlockController;
import net.minecraft.entity.player.PlayerEntity;

public class BlastChillerBlockScreen extends CottonInventoryScreen<BlastChillerBlockController> 
{
    public BlastChillerBlockScreen(BlastChillerBlockController container, PlayerEntity player) 
    {
        super(container, player);
    }
}