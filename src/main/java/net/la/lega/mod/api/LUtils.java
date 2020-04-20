package net.la.lega.mod.api;

import net.minecraft.util.Unit;

public final class LUtils
{
    public static Unit executeRunnable(Runnable runnable)
    {
        runnable.run();
        return Unit.INSTANCE;
    }
}
