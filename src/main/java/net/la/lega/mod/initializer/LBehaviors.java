package net.la.lega.mod.initializer;

import net.la.lega.mod.dispense_behaviour.AvocadoDispenserBehavior;
import net.la.lega.mod.dispense_behaviour.FryerOilDispenserBehavior;
import net.minecraft.block.DispenserBlock;
import net.minecraft.item.Items;

public abstract class LBehaviors
{
    public static void initialize()
    {
        DispenserBlock.registerBehavior(Items.SHEARS, new AvocadoDispenserBehavior());
        DispenserBlock.registerBehavior(LItems.SUNFLOWER_OIL_BOTTLE, new FryerOilDispenserBehavior());
        DispenserBlock.registerBehavior(LItems.RICE_OIL_BOTTLE, new FryerOilDispenserBehavior());
    }
}