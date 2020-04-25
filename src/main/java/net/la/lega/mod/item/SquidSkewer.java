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

public class SquidSkewer extends Item
{
    public static final Identifier ID = new Identifier(LLoader.MOD_ID, "squid_skewer");
    
    public static final StatusEffect effect = StatusEffects.LUCK;
    public static final int effectDuration = 20 * 60;
    public static final float effectChance = 0.0925F;
    public static final float saturation = 1.85F;
    public static final int hunger = 4;
    
    public SquidSkewer()
    {
        super(new Item.Settings().group(LItemGroups.LALEGA_FOOD)
              .food(new FoodComponent.Builder()
                    .hunger(hunger)
                    .saturationModifier(saturation)
                    .statusEffect(new StatusEffectInstance(effect, effectDuration), effectChance)
                    .snack()
                    .alwaysEdible()
                    .build())
             );
    }
    
    @Override public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user)
    {
        if(user instanceof ServerPlayerEntity)
        {
            ((ServerPlayerEntity) user).inventory.offerOrDrop(world, new ItemStack(Items.STICK, 1));
        }
        return super.finishUsing(stack, world, user);
    }
}
