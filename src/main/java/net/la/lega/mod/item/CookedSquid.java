package net.la.lega.mod.item;

import net.la.lega.mod.loader.LLoader;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

public class CookedSquid extends Item
{
    public static final Identifier ID = new Identifier(LLoader.MOD_ID, "cooked_squid");
    
    public static final StatusEffect effect = StatusEffects.NAUSEA;
    public static final int effectDuration = 20 * 20;
    public static final float effectChance = 0.15F;
    public static final float saturation = 1.45F;
    public static final int hunger = 3;
    
    public CookedSquid()
    {
        super(new Item.Settings().group(ItemGroup.FOOD)
              .food(new FoodComponent.Builder().hunger(hunger)
                    .saturationModifier(saturation)
                    .statusEffect(new StatusEffectInstance(effect, effectDuration), effectChance)
                    .snack()
                    .alwaysEdible()
                    .build())
        );
    }
}