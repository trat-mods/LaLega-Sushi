package net.la.lega.mod.initializer;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.la.lega.mod.loader.LLoader;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public final class LItemGroups
{
    public static final ItemGroup LALEGA_PLANTS = FabricItemGroupBuilder.create(new Identifier(LLoader.MOD_ID, "lalega_plants")).icon(() -> new ItemStack(LBlocks.RICE_BLOCK)).build();
    public static final ItemGroup LALEGA_FOOD = FabricItemGroupBuilder.create(new Identifier(LLoader.MOD_ID, "lalega_food")).icon(() -> new ItemStack(LItems.GYOZA)).build();
    public static final ItemGroup LALEGA_INGREDIENTS = FabricItemGroupBuilder.create(new Identifier(LLoader.MOD_ID, "lalega_ingredients")).icon(() -> new ItemStack(LItems.WASABI)).build();
    public static final ItemGroup LALEGA_SUSHI = FabricItemGroupBuilder.create(new Identifier(LLoader.MOD_ID, "lalega_sushi")).icon(() -> new ItemStack(LItems.NIGIRI_SAKE)).build();
    public static final ItemGroup LALEGA_BLOCKS = FabricItemGroupBuilder.create(new Identifier(LLoader.MOD_ID, "lalega_blocks")).icon(() -> new ItemStack(LBlocks.SUSHI_CRAFTER_BLOCK)).build();
}
