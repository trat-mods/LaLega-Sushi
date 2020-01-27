package net.la.lega.mod.item;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class SalmonFilletItem extends Item
{
    public static final Identifier ID = new Identifier("lalegamod", "salmon_fillet");
    
    public static final StatusEffect effect = StatusEffects.POISON;
    public static final int effectDuration = 20 * 3;
    public static final float effectChance = 0.2F;
    public static final float saturation = 0.5F;
    public static final int hunger = 1;

    public SalmonFilletItem(Settings settings) 
    {
        super(settings);
    }

}