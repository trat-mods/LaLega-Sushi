package net.la.lega.mod.loader;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.la.lega.mod.screen.ChillBlasterScreen;
import net.minecraft.util.Identifier;

public class ClientLoader implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        ScreenProviderRegistry.INSTANCE.registerFactory(new Identifier("lalegamod", "chill_blaster_container"), ChillBlasterScreen::createScreen);
    }
}