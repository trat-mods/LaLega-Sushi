package net.la.lega.mod.block;

import net.minecraft.util.Identifier;

public class PoweredLauncherBlock extends LauncherBlock
{
    public static final Identifier ID = new Identifier("lalegamod", "powered_launcher_block");

    public PoweredLauncherBlock()
    {
        super();
        baseMultiplier = 2F;
        stackPowerPercentage = 0.325F;
        stackMultiplier = baseMultiplier * stackPowerPercentage;
    }
}