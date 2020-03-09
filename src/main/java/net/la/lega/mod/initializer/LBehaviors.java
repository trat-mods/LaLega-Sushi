package net.la.lega.mod.initializer;

import net.la.lega.mod.dispense_behaviour.AvocadoDispenserBehavior;
import net.minecraft.block.DispenserBlock;
import net.minecraft.item.Items;

public abstract class LBehaviors
{
    public static void initialize()
    {
        DispenserBlock.registerBehavior(Items.SHEARS, new AvocadoDispenserBehavior());
    }
}