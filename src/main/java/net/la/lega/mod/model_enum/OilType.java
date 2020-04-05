package net.la.lega.mod.model_enum;

import net.minecraft.util.StringIdentifiable;

public enum OilType implements StringIdentifiable
{
    NONE("none"),
    SUNFLOWER_OIL("sunflower_oil"),
    RICE_OIL("rice_oil");
    
    private final String name;
    
    private OilType(String name)
    {
        this.name = name;
    }
    
    public String asString()
    {
        return this.name;
    }
}