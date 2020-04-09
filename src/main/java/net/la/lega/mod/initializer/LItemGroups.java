package net.la.lega.mod.initializer;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.la.lega.mod.loader.LLoader;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public abstract class LItemGroups
{
    public static final ItemGroup JAPANESE_PLANTS = FabricItemGroupBuilder.create(new Identifier(LLoader.MOD_ID, "japanese_plants")).icon(() -> new ItemStack(LBlocks.RICE_BLOCK)).build();
    public static final ItemGroup JAPANESE_FOOD = FabricItemGroupBuilder.create(new Identifier(LLoader.MOD_ID, "japanese_food")).icon(() -> new ItemStack(LItems.GYOZA)).build();
    public static final ItemGroup JAPANESE_INGREDIENTS = FabricItemGroupBuilder.create(new Identifier(LLoader.MOD_ID, "japanese_ingredients")).icon(() -> new ItemStack(LItems.WASABI)).build();
    public static final ItemGroup SUSHI = FabricItemGroupBuilder.create(new Identifier(LLoader.MOD_ID, "sushi")).icon(() -> new ItemStack(LItems.NIGIRI_SAKE)).build();
    public static final ItemGroup BLOCKS = FabricItemGroupBuilder.create(new Identifier(LLoader.MOD_ID, "blocks")).icon(() -> new ItemStack(LBlocks.SUSHI_CRAFTER_BLOCK)).build();
}
