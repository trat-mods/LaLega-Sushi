package net.la.lega.mod.item;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class RiceItem extends Item
{
    public static final Identifier ID = new Identifier("lalegamod", "rice_item");
    
    public static final float saturation = 1F;
    public static final int hunger = 2;

    public RiceItem(Settings settings) 
    {
        super(settings);
    }
}