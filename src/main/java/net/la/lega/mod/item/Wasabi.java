package net.la.lega.mod.item;

import net.la.lega.mod.initializer.LItemGroups;
import net.la.lega.mod.loader.LLoader;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class Wasabi extends Item
{
    public static final Identifier ID = new Identifier(LLoader.MOD_ID, "wasabi");
    
    public static final StatusEffect effect = StatusEffects.SPEED;
    public static final int effectDuration = 20 * 8;
    public static final float effectChance = 0.125F;
    public static final float saturation = 0.85F;
    public static final int hunger = 1;
    
    public Wasabi()
    {
        super(new Item.Settings().group(LItemGroups.LALEGA_INGREDIENTS)
                    .food(new FoodComponent.Builder()
                                .hunger(hunger)
                                .saturationModifier(saturation).snack().alwaysEdible()
                                .statusEffect(new StatusEffectInstance(effect, effectDuration), effectChance)
                                .build())
             );
    }
}
