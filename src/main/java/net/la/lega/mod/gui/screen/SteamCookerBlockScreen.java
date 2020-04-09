package net.la.lega.mod.gui.screen;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.la.lega.mod.gui.controller.SteamCookerBlockController;
import net.minecraft.entity.player.PlayerEntity;

public class SteamCookerBlockScreen extends CottonInventoryScreen<SteamCookerBlockController>
{
    public SteamCookerBlockScreen(SteamCookerBlockController container, PlayerEntity player)
    {
        super(container, player);
    }
}