package net.la.lega.mod.item;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class SashimiItem extends Item
{
    public static final Identifier ID = new Identifier("lalegamod", "sashimi");
    
    public static final StatusEffect effect = StatusEffects.HUNGER;
    public static final int effectDuration = 20 * 5;
    public static final float effectChance = 0.2F;
    public static final float saturation = 0.85F;
    public static final int hunger = 3;

    public SashimiItem(Settings settings) 
    {
        super(settings);
    }

}