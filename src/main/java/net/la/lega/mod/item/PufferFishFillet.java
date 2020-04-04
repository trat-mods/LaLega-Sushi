package net.la.lega.mod.item;

import net.la.lega.mod.loader.LLoader;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

public class PufferFishFillet extends Item
{
    public static final Identifier ID = new Identifier(LLoader.MOD_ID, "pufferfish_fillet");
    
    public static final StatusEffect effect = StatusEffects.POISON;
    public static final int effectDuration = 20 * 60;
    public static final float effectChance = 0.25F;
    public static final float saturation = 1.4F;
    public static final int hunger = 2;
    
    public PufferFishFillet()
    {
        super(new Settings().group(ItemGroup.FOOD)
                    .food(new FoodComponent.Builder().hunger(hunger)
                                .saturationModifier(saturation).alwaysEdible()
                                .statusEffect(new StatusEffectInstance(effect, effectDuration), effectChance)
                                .build())
             );
    }
}