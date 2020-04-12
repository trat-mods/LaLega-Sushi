package net.la.lega.mod.item;

import net.la.lega.mod.initializer.LItemGroups;
import net.la.lega.mod.loader.LLoader;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class TorafuguKaraage extends Item
{
    public static final Identifier ID = new Identifier(LLoader.MOD_ID, "torafugu_karaage");
    
    public static final float saturation = 5.85F;
    public static final int hunger = 10;
    
    public TorafuguKaraage()
    {
        super(new Settings().group(LItemGroups.JAPANESE_FOOD)
                    .food(new FoodComponent.Builder()
                                .hunger(hunger)
                                .saturationModifier(saturation)
                                .build())
             );
    }
}