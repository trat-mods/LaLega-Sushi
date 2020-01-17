package net.la.lega.mod.loader;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.la.lega.mod.block.ChillBlasterBlock;
import net.la.lega.mod.block.ExtremeLauncherBlock;
import net.la.lega.mod.block.LauncherBlock;
import net.la.lega.mod.block.PoweredLauncherBlock;
import net.la.lega.mod.container.Containers;
import net.la.lega.mod.entity.ChillBlasterBlockEntity;
import net.la.lega.mod.recipe.ChillBlastingRecipe;
import net.la.lega.mod.recipe.serializer.ChillBlastingRecipeSerializer;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

// FurnaceBlockEntity
// FurnaceBlock
// ItemStack
// Slot
// Inventory
// CraftingTableBlock
// Hopper
// StonecutterBlock
// SmokerBlock
// BlastFurnaceBlock
// CookingRecipeSerializer
// StonecutterBlock
// AbstractButtonBlock
// LivingEntity

//PlayerEntity

public class LaLegaLoader implements ModInitializer
{
    public static final Block LAUNCHER_BLOCK = new LauncherBlock();
    public static final Block POWERED_LAUNCHER_BLOCK = new PoweredLauncherBlock();
    public static final Block EXTREME_LAUNCHER_BLOCK = new ExtremeLauncherBlock();
    public static final Block CHILL_BLASTER_BLOCK = new ChillBlasterBlock();

    public static BlockEntityType<ChillBlasterBlockEntity> CHILL_BLASTER_BLOCK_ENTITY;

    public static Identifier CHILL_BLASTER_CONTAINER_ID = new Identifier("lalegamod", "chill_blaster_container");

    @Override
    public void onInitialize() 
    {
        Registry.register(Registry.BLOCK, new Identifier("lalegamod", "launcher_block"), LAUNCHER_BLOCK);
        Registry.register(Registry.ITEM, new Identifier("lalegamod", "launcher_block"), new BlockItem(LAUNCHER_BLOCK, new Item.Settings().group(ItemGroup.REDSTONE)));

        Registry.register(Registry.BLOCK, new Identifier("lalegamod", "powered_launcher_block"), POWERED_LAUNCHER_BLOCK);
        Registry.register(Registry.ITEM, new Identifier("lalegamod", "powered_launcher_block"), new BlockItem(POWERED_LAUNCHER_BLOCK, new Item.Settings().group(ItemGroup.REDSTONE)));

        Registry.register(Registry.BLOCK, new Identifier("lalegamod", "extreme_launcher_block"), EXTREME_LAUNCHER_BLOCK);
        Registry.register(Registry.ITEM, new Identifier("lalegamod", "extreme_launcher_block"), new BlockItem(EXTREME_LAUNCHER_BLOCK, new Item.Settings().group(ItemGroup.REDSTONE)));

        // Registry.register(Registry.BLOCK, new Identifier("lalegamod", "chill_blaster_block"), CHILL_BLASTER_BLOCK);
        // Registry.register(Registry.ITEM, new Identifier("lalegamod", "chill_blaster_block"), new BlockItem(CHILL_BLASTER_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));

        // CHILL_BLASTER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY, "lalegamod:chill_blaster_block_entity", BlockEntityType.Builder.create(ChillBlasterBlockEntity::new, CHILL_BLASTER_BLOCK).build(null));

        // Registry.register(Registry.RECIPE_SERIALIZER, ChillBlastingRecipeSerializer.ID, ChillBlastingRecipeSerializer.INSTANCE);
        // Registry.register(Registry.RECIPE_TYPE, new Identifier("lalegamod", ChillBlastingRecipe.Type.ID), ChillBlastingRecipe.Type.INSTANCE);

        // ContainerProviderRegistry.INSTANCE.registerFactory(CHILL_BLASTER_CONTAINER_ID, Containers::createChillBlasterContainer);
    }
}