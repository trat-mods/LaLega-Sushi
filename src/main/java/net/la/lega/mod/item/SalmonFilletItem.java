package net.la.lega.mod.item;

import net.la.lega.mod.loader.LaLegaLoader;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class SalmonFilletItem extends Item
{
    public static final Identifier ID = new Identifier(LaLegaLoader.MOD_ID, "salmon_fillet");
    
    public static final StatusEffect effect = StatusEffects.POISON;
    public static final int effectDuration = 20 * 15;
    public static final float effectChance = 0.225F;
    public static final float saturation = 0.7F;
    public static final int hunger = 2;

    public SalmonFilletItem(Settings settings) 
    {
        super(settings);
    }

}