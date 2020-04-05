package net.la.lega.mod.item;

import net.la.lega.mod.initializer.LItemGroups;
import net.la.lega.mod.loader.LLoader;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class RawGyoza extends Item
{
    public static final Identifier ID = new Identifier(LLoader.MOD_ID, "raw_gyoza");
    
    public RawGyoza()
    {
        super(new Settings().group(LItemGroups.JAPANESE_FOOD));
    }
}
