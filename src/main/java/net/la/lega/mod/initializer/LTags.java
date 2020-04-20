package net.la.lega.mod.initializer;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.la.lega.mod.loader.LLoader;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public final class LTags
{
    public static Tag<Item> SUSHI_REQUIRED;
    private static String sushi_required_id = "sushi_required";
    public static Tag<Item> SUSHI_INGREDIENT;
    private static String sushi_ingredient_id = "sushi_ingredient";
    
    public static void intialize()
    {
        SUSHI_REQUIRED = TagRegistry.item(new Identifier(LLoader.MOD_ID, sushi_required_id));
        SUSHI_INGREDIENT = TagRegistry.item(new Identifier(LLoader.MOD_ID, sushi_ingredient_id));
    }
}