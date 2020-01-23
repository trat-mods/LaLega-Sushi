package net.la.lega.mod.block;

import net.minecraft.util.Identifier;

public class PoweredLauncherBlock extends LauncherBlock
{
    public static final Identifier ID = new Identifier("lalegamod", "powered_launcher_block");

    public PoweredLauncherBlock()
    {
        super();
        baseMultiplier = 1.9F;
        stackPowerPercentage = 0.32F;
        stackMultiplier = baseMultiplier * stackPowerPercentage;
    }
}