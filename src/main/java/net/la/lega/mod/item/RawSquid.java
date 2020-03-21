package net.la.lega.mod.item;

import net.la.lega.mod.loader.LLoader;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

public class RawSquid extends Item
{
    public static final Identifier ID = new Identifier(LLoader.MOD_ID, "raw_squid");
    
    public static final StatusEffect effect = StatusEffects.POISON;
    public static final int effectDuration = 20 * 20;
    public static final float effectChance = 0.175F;
    public static final float saturation = 0.50F;
    public static final int hunger = 1;
    
    public RawSquid()
    {
        super(new Item.Settings().group(ItemGroup.FOOD)
              .food(new FoodComponent.Builder().hunger(hunger)
                    .saturationModifier(saturation).alwaysEdible()
                    .statusEffect(new StatusEffectInstance(effect, effectDuration), effectChance)
                    .build())
        );
    }
}
