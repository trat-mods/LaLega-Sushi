package net.la.lega.mod.item;

import net.la.lega.mod.loader.LLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class SquidSkewer extends Item
{
    public static final Identifier ID = new Identifier(LLoader.MOD_ID, "squid_skewer");
    
    public static final float saturation = 1.4F;
    public static final int hunger = 4;
    
    public SquidSkewer()
    {
        super(new Item.Settings().group(ItemGroup.FOOD)
              .food(new FoodComponent.Builder()
                    .hunger(hunger)
                    .saturationModifier(saturation)
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
