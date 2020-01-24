package net.la.lega.mod.gui.screen;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.la.lega.mod.gui.controller.ThreadCutterBlockController;
import net.minecraft.entity.player.PlayerEntity;

public class ThreadCutterBlockScreen extends CottonInventoryScreen<ThreadCutterBlockController> 
{
    public ThreadCutterBlockScreen(ThreadCutterBlockController container, PlayerEntity player) 
    {
        super(container, player);
    }
}