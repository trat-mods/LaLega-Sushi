package net.la.lega.mod.block;


public class PoweredLauncherBlock extends LauncherBlock
{
    public PoweredLauncherBlock()
    {
        super();
        baseMultiplier = 1.9F;
        stackPowerPercentage = 0.32F;
        stackMultiplier = baseMultiplier * stackPowerPercentage;
    }
}