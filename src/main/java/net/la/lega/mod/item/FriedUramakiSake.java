package net.la.lega.mod.item;

import net.la.lega.mod.initializer.LItemGroups;
import net.la.lega.mod.loader.LLoader;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class FriedUramakiSake extends Item
{
    public static final Identifier ID = new Identifier(LLoader.MOD_ID, "fried_uramaki_sake");
    
    public static final float saturation = 4.125F;
    public static final int hunger = 7;
    
    public FriedUramakiSake()
    {
        super(new Settings().group(LItemGroups.LALEGA_SUSHI)
              .food(new FoodComponent.Builder()
                    .hunger(hunger)
                    .saturationModifier(saturation)
                    .alwaysEdible()
                    .build())
             );
    }
}