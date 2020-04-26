package net.la.lega.mod.item;

import net.la.lega.mod.initializer.LItemGroups;
import net.la.lega.mod.loader.LLoader;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class Avocado extends Item
{
    public static final Identifier ID = new Identifier(LLoader.MOD_ID, "avocado");
    
    public static final float saturation = 0.95F;
    public static final int hunger = 1;
    
    public Avocado()
    {
        super(new Item.Settings().group(LItemGroups.LALEGA_INGREDIENTS)
              .food(new FoodComponent.Builder()
                    .hunger(hunger)
                    .saturationModifier(saturation)
                    .build())
             );
    }
}