package net.la.lega.mod.item;

import net.la.lega.mod.loader.LaLegaLoader;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

public class SashimiSake extends Item
{
    public static final Identifier ID = new Identifier(LaLegaLoader.MOD_ID, "sashimi_sake");
    
    public static final StatusEffect effect = StatusEffects.HUNGER;
    public static final int effectDuration = 20 * 8;
    public static final float effectChance = 0.2F;
    public static final float saturation = 0.55F;
    public static final int hunger = 2;

    public SashimiSake() 
    {
        super(new Item.Settings().group(ItemGroup.FOOD)
                .food(new FoodComponent.Builder()
                .hunger(hunger)
                .saturationModifier(saturation).snack().alwaysEdible()
                .statusEffect(new StatusEffectInstance(effect, effectDuration), effectChance)
                .build())
            );
    }

}