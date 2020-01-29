package net.la.lega.mod.item;

import net.la.lega.mod.loader.LaLegaLoader;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class NigiriSake extends Item
{
    public static final Identifier ID = new Identifier(LaLegaLoader.MOD_ID, "nigiri_sake");
    
    public static final float saturation = 0.9F;
    public static final int hunger = 5;

    public NigiriSake(Settings settings) 
    {
        super(settings);
    }
}