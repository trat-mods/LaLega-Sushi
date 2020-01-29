package net.la.lega.mod.item;

import net.la.lega.mod.loader.LaLegaLoader;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class HosomakiSake extends Item
{
    public static final Identifier ID = new Identifier(LaLegaLoader.MOD_ID, "hosomaki_sake");
    
    public static final float saturation = 0.75F;
    public static final int hunger = 4;

    public HosomakiSake(Settings settings) 
    {
        super(settings);
    }
}