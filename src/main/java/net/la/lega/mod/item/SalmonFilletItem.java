package net.la.lega.mod.item;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class SalmonFilletItem extends Item
{
    public static final Identifier ID = new Identifier("lalegamod", "salmon_fillet_item");
    
    public SalmonFilletItem(Settings settings) 
    {
        super(settings);
    }

}