package net.la.lega.mod.item;

import net.la.lega.mod.initializer.LItemGroups;
import net.la.lega.mod.loader.LLoader;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class Gyoza extends Item
{
    public static final Identifier ID = new Identifier(LLoader.MOD_ID, "gyoza");
    
    public static final float saturation = 5.65F;
    public static final int hunger = 8;
    
    public Gyoza()
    {
        super(new Settings().group(LItemGroups.LALEGA_FOOD)
                    .food(new FoodComponent.Builder()
                                .hunger(hunger)
                                .saturationModifier(saturation)
                                .build())
             );
    }
}