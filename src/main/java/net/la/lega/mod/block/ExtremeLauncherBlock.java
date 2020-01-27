package net.la.lega.mod.block;

import net.minecraft.util.Identifier;

public class ExtremeLauncherBlock extends LauncherBlock
{
    public static final Identifier ID = new Identifier("lalegamod", "extreme_launcher_block");

    public ExtremeLauncherBlock()
    {
        super();
        baseMultiplier = 3F;
        stackPowerPercentage = 0.325F;
        stackMultiplier = baseMultiplier * stackPowerPercentage;
    }
}