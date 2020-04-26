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

public class SashimiFuguBowl extends Item
{
    public static final Identifier ID = new Identifier(LLoader.MOD_ID, "sashimi_fugu_bowl");
    
    public static final StatusEffect speedEffect = StatusEffects.SPEED;
    public static final int speedEffectDuration = 20 * 30;
    public static final float speedEffectChance = 0.20F;
    
    public static final StatusEffect luckEffect = StatusEffects.LUCK;
    public static final int luckEffectDuration = 20 * 60;
    public static final float luckEffectChance = 0.15F;
    
    public static final float saturation = 12F;
    public static final int hunger = 9;
    
    public SashimiFuguBowl()
    {
        super(new Settings().group(LItemGroups.LALEGA_FOOD)
              .maxCount(8)
              .food(new FoodComponent.Builder().hunger(hunger)
                    .saturationModifier(saturation).alwaysEdible()
                    .statusEffect(new StatusEffectInstance(speedEffect, speedEffectDuration), speedEffectChance)
                    .statusEffect(new StatusEffectInstance(luckEffect, luckEffectDuration), luckEffectChance)
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
