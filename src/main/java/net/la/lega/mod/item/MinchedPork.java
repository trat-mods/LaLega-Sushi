package net.la.lega.mod.item;

import net.la.lega.mod.initializer.LItemGroups;
import net.la.lega.mod.loader.LLoader;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class MinchedPork extends Item
{
    public static final Identifier ID = new Identifier(LLoader.MOD_ID, "minched_pork");
    
    public static final float saturation = 2.15F;
    public static final int hunger = 4;
    public static final StatusEffect effect = StatusEffects.POISON;
    public static final int effectDuration = 20 * 20;
    public static final float effectChance = 0.175F;
    
    public MinchedPork()
    {
        super(new Item.Settings().group(LItemGroups.JAPANESE_INGREDIENTS)
                    .food(new FoodComponent.Builder()
                                .hunger(hunger)
                                .saturationModifier(saturation)
                                .statusEffect(new StatusEffectInstance(effect, effectDuration), effectChance)
                                .alwaysEdible()
                                .build()));
    }
}
