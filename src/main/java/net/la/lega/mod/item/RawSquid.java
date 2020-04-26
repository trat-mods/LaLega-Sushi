package net.la.lega.mod.item;

import net.la.lega.mod.initializer.LItemGroups;
import net.la.lega.mod.loader.LLoader;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class RawSquid extends Item
{
    public static final Identifier ID = new Identifier(LLoader.MOD_ID, "raw_squid");
    
    public static final StatusEffect effect = StatusEffects.NAUSEA;
    public static final int effectDuration = 20 * 20;
    public static final float effectChance = 0.215F;
    public static final float saturation = 1.3F;
    public static final int hunger = 2;
    
    public RawSquid()
    {
        super(new Item.Settings().group(LItemGroups.LALEGA_INGREDIENTS)
              .food(new FoodComponent.Builder().hunger(hunger)
                    .saturationModifier(saturation)
                    .statusEffect(new StatusEffectInstance(effect, effectDuration), effectChance)
                    .build())
             );
    }
}
