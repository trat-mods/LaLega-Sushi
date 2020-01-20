package net.la.lega.mod.loader;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.la.lega.mod.block.BlastChillerBlock;
import net.la.lega.mod.block.ExtremeLauncherBlock;
import net.la.lega.mod.block.LauncherBlock;
import net.la.lega.mod.block.PoweredLauncherBlock;
import net.la.lega.mod.entity.BlastChillerBlockEntity;
import net.la.lega.mod.gui.BlastChillerBlockController;
import net.la.lega.mod.item.SalmonFilletItem;
import net.la.lega.mod.item.SashimiItem;
import net.la.lega.mod.recipe.BlastChillingRecipe;
import net.la.lega.mod.recipe.serializer.BlastChillingRecipeSerializer;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.container.BlockContext;
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
    //Items
    public static final Item SASHIMI_ITEM = new SashimiItem(new Item.Settings().group(ItemGroup.FOOD));
    public static final Item SALMON_FILLET_ITEM = new SalmonFilletItem(new Item.Settings().group(ItemGroup.FOOD));

    //Blocks
    public static final Block LAUNCHER_BLOCK = new LauncherBlock();
    public static final Block POWERED_LAUNCHER_BLOCK = new PoweredLauncherBlock();
    public static final Block EXTREME_LAUNCHER_BLOCK = new ExtremeLauncherBlock();
    public static final Block CHILL_BLASTER_BLOCK = new BlastChillerBlock();

    //Entities
    public static BlockEntityType<BlastChillerBlockEntity> CHILL_BLASTER_BLOCK_ENTITY;


    @Override
    public void onInitialize() 
    {
        Registry.register(Registry.ITEM, SashimiItem.ID, SASHIMI_ITEM);
        Registry.register(Registry.ITEM, SalmonFilletItem.ID, SALMON_FILLET_ITEM);

        Registry.register(Registry.BLOCK, new Identifier("lalegamod", "launcher_block"), LAUNCHER_BLOCK);
        Registry.register(Registry.ITEM, new Identifier("lalegamod", "launcher_block"), new BlockItem(LAUNCHER_BLOCK, new Item.Settings().group(ItemGroup.REDSTONE)));

        Registry.register(Registry.BLOCK, new Identifier("lalegamod", "powered_launcher_block"), POWERED_LAUNCHER_BLOCK);
        Registry.register(Registry.ITEM, new Identifier("lalegamod", "powered_launcher_block"), new BlockItem(POWERED_LAUNCHER_BLOCK, new Item.Settings().group(ItemGroup.REDSTONE)));

        Registry.register(Registry.BLOCK, new Identifier("lalegamod", "extreme_launcher_block"), EXTREME_LAUNCHER_BLOCK);
        Registry.register(Registry.ITEM, new Identifier("lalegamod", "extreme_launcher_block"), new BlockItem(EXTREME_LAUNCHER_BLOCK, new Item.Settings().group(ItemGroup.REDSTONE)));

        ContainerProviderRegistry.INSTANCE.registerFactory(BlastChillerBlock.ID, 
            (syncId, id, player, buf) -> new BlastChillerBlockController(syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos()))
        );

        Registry.register(Registry.BLOCK, BlastChillerBlock.ID, CHILL_BLASTER_BLOCK);
        Registry.register(Registry.ITEM, BlastChillerBlock.ID, new BlockItem(CHILL_BLASTER_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));

        CHILL_BLASTER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY, "lalegamod:blast_chiller_block_entity", BlockEntityType.Builder.create(BlastChillerBlockEntity::new, CHILL_BLASTER_BLOCK).build(null));

        Registry.register(Registry.RECIPE_SERIALIZER, BlastChillingRecipeSerializer.ID, BlastChillingRecipeSerializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier("lalegamod", BlastChillingRecipe.Type.ID), BlastChillingRecipe.Type.INSTANCE);

    }
}