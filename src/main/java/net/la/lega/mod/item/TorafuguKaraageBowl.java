package net.la.lega.mod.item;

import net.la.lega.mod.initializer.LItemGroups;
import net.la.lega.mod.loader.LLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class TorafuguKaraageBowl extends Item
{
    public static final Identifier ID = new Identifier(LLoader.MOD_ID, "torafugu_karaage_bowl");
    
    public static final StatusEffect speedEffect = StatusEffects.SPEED;
    public static final int speedEffectDuration = 20 * 30;
    public static final float speedEffectChance = 0.325F;
    
    public static final StatusEffect regenEffect = StatusEffects.REGENERATION;
    public static final int regenEffectDuration = 20 * 20;
    public static final float regenEffectChance = 0.85F;
    
    public static final float saturation = 18F;
    public static final int hunger = 12;
    
    public TorafuguKaraageBowl()
    {
        super(new Settings().group(LItemGroups.LALEGA_FOOD)
              .maxCount(4)
              .food(new FoodComponent.Builder().hunger(hunger)
                    .saturationModifier(saturation).alwaysEdible()
                    .statusEffect(new StatusEffectInstance(speedEffect, speedEffectDuration), speedEffectChance)
                    .statusEffect(new StatusEffectInstance(regenEffect, regenEffectDuration), regenEffectChance)
                    .alwaysEdible()
                    .build())
             );
    }
    
    @Override public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user)
    {
        if(user instanceof ServerPlayerEntity)
        {
            ((ServerPlayerEntity) user).inventory.offerOrDrop(world, new ItemStack(Items.BOWL, 1));
        }
        return super.finishUsing(stack, world, user);
    }
}
