package net.la.lega.mod.block;


public class ExtremeLauncherBlock extends LauncherBlock
{
    public ExtremeLauncherBlock()
    {
        super();
        baseMultiplier = 3.15F;
        stackPowerPercentage = 0.25F;
        stackMultiplier = baseMultiplier * stackPowerPercentage;
    }
}