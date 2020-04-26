package net.la.lega.mod.item;

import net.la.lega.mod.initializer.LItemGroups;
import net.la.lega.mod.loader.LLoader;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class Gyoza extends Item
{
    public static final Identifier ID = new Identifier(LLoader.MOD_ID, "gyoza");
    
    public static final StatusEffect effect = StatusEffects.REGENERATION;
    public static final int effectDuration = 20 * 8;
    public static final float effectChance = 0.245F;
    public static final float saturation = 3.85F;
    public static final int hunger = 6;
    
    public Gyoza()
    {
        super(new Settings().group(LItemGroups.LALEGA_FOOD)
              .food(new FoodComponent.Builder()
                    .hunger(hunger)
                    .statusEffect(new StatusEffectInstance(effect, effectDuration), effectChance)
                    .saturationModifier(saturation)
                    .snack()
                    .build())
             );
    }
}