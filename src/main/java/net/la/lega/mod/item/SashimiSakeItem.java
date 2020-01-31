package net.la.lega.mod.item;

import net.la.lega.mod.loader.LaLegaLoader;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class SashimiSakeItem extends Item
{
    public static final Identifier ID = new Identifier(LaLegaLoader.MOD_ID, "sashimi_sake");
    
    public static final StatusEffect effect = StatusEffects.HUNGER;
    public static final int effectDuration = 20 * 8;
    public static final float effectChance = 0.2F;
    public static final float saturation = 0.55F;
    public static final int hunger = 2;

    public SashimiSakeItem(Settings settings) 
    {
        super(settings);
    }

}