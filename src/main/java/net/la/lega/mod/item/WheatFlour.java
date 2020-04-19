package net.la.lega.mod.item;

import net.la.lega.mod.initializer.LItemGroups;
import net.la.lega.mod.loader.LLoader;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class WheatFlour extends Item
{
    public static final Identifier ID = new Identifier(LLoader.MOD_ID, "wheat_flour");
    
    public WheatFlour()
    {
        super(new Item.Settings().group(LItemGroups.LALEGA_INGREDIENTS));
    }
}
