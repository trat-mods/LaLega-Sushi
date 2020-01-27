package net.la.lega.mod.loader;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.la.lega.mod.block.BlastChillerBlock;
import net.la.lega.mod.block.ExtremeLauncherBlock;
import net.la.lega.mod.block.LauncherBlock;
import net.la.lega.mod.block.PoweredLauncherBlock;
import net.la.lega.mod.block.ThreadCutterBlock;
import net.la.lega.mod.entity.BlastChillerBlockEntity;
import net.la.lega.mod.entity.ThreadCutterBlockEntity;
import net.la.lega.mod.gui.controller.BlastChillerBlockController;
import net.la.lega.mod.gui.controller.ThreadCutterBlockController;
import net.la.lega.mod.item.NigiriSake;
import net.la.lega.mod.item.RiceItem;
import net.la.lega.mod.item.SalmonFilletItem;
import net.la.lega.mod.item.SashimiItem;
import net.la.lega.mod.recipe.BlastChillingRecipe;
import net.la.lega.mod.recipe.ThreadCuttingRecipe;
import net.la.lega.mod.recipe.serializer.BlastChillingRecipeSerializer;
import net.la.lega.mod.recipe.serializer.ThreadCuttingRecipeSerializer;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.container.BlockContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

// DropperBlock
// HopperBlock
// FurnaceBlockEntity
// FurnaceBlock
// ItemStack
// Slot
// Inventory
// CraftingTableBlock
// HopperBlock
// StonecutterBlock
// SmokerBlock
// BlastFurnaceBlock
// CookingRecipeSerializer
// StonecutterBlock
// AbstractButtonBlock
// LivingEntity
// DropperBlock

//PlayerEntity

public class LaLegaLoader implements ModInitializer
{
    public static final String MOD_ID = "lalegamod";

    //Items
    public static final Item SASHIMI_ITEM = new SashimiItem(
        new Item.Settings().group(ItemGroup.FOOD)
        .food(new FoodComponent.Builder()
            .hunger(SashimiItem.hunger)
            .saturationModifier(SashimiItem.saturation)
            .snack().alwaysEdible()
            .statusEffect(
                new StatusEffectInstance(SashimiItem.effect, SashimiItem.effectDuration), SashimiItem.effectChance)
        .build()));

    public static final Item SALMON_FILLET_ITEM = new SalmonFilletItem(
        new Item.Settings().group(ItemGroup.FOOD)
        .food(new FoodComponent.Builder()
            .hunger(SalmonFilletItem.hunger)
            .saturationModifier(SalmonFilletItem.saturation)
            .snack().alwaysEdible()
            .statusEffect(
                new StatusEffectInstance(SalmonFilletItem.effect, SalmonFilletItem.effectDuration), SalmonFilletItem.effectChance)
        .build()));

    public static final Item RICE_ITEM = new RiceItem(
        new Item.Settings().group(ItemGroup.FOOD)
        .food(new FoodComponent.Builder()
            .hunger(RiceItem.hunger)
            .saturationModifier(RiceItem.saturation)
        .build()));

    public static final Item NIGIRI_SAKE_ITEM = new NigiriSake(
    new Item.Settings().group(ItemGroup.FOOD)
    .food(new FoodComponent.Builder()
        .hunger(NigiriSake.hunger)
        .saturationModifier(NigiriSake.saturation)
        .snack().alwaysEdible()
    .build()));

    //Blocks
    public static final Block LAUNCHER_BLOCK = new LauncherBlock();
    public static final Block POWERED_LAUNCHER_BLOCK = new PoweredLauncherBlock();
    public static final Block EXTREME_LAUNCHER_BLOCK = new ExtremeLauncherBlock();
    public static final Block BLAST_CHILLER_BLOCK = new BlastChillerBlock();
    public static final Block THREAD_CUTTER_BLOCK = new ThreadCutterBlock();

    //Entities
    public static BlockEntityType<BlastChillerBlockEntity> BLAST_CHILLER_BLOCK_ENTITY;
    public static BlockEntityType<ThreadCutterBlockEntity> THREAD_CUTTER_BLOCK_ENTITY;


    @Override
    public void onInitialize() 
    { 
        registerItems();
        registerBlocks();
        registerEntities();
        registerRecipes();
        registerControllers();
    }

    private void registerItems()
    {
        Registry.register(Registry.ITEM, SashimiItem.ID, SASHIMI_ITEM);
        Registry.register(Registry.ITEM, SalmonFilletItem.ID, SALMON_FILLET_ITEM);
        Registry.register(Registry.ITEM, RiceItem.ID, RICE_ITEM);
        Registry.register(Registry.ITEM, NigiriSake.ID, NIGIRI_SAKE_ITEM);
    }

    private void registerBlocks()
    {
        Registry.register(Registry.BLOCK,LauncherBlock.ID, LAUNCHER_BLOCK);
        Registry.register(Registry.ITEM, LauncherBlock.ID, new BlockItem(LAUNCHER_BLOCK, new Item.Settings().group(ItemGroup.REDSTONE)));

        Registry.register(Registry.BLOCK, PoweredLauncherBlock.ID, POWERED_LAUNCHER_BLOCK);
        Registry.register(Registry.ITEM, PoweredLauncherBlock.ID, new BlockItem(POWERED_LAUNCHER_BLOCK, new Item.Settings().group(ItemGroup.REDSTONE)));

        Registry.register(Registry.BLOCK, ExtremeLauncherBlock.ID, EXTREME_LAUNCHER_BLOCK);
        Registry.register(Registry.ITEM, ExtremeLauncherBlock.ID, new BlockItem(EXTREME_LAUNCHER_BLOCK, new Item.Settings().group(ItemGroup.REDSTONE)));

        Registry.register(Registry.BLOCK, BlastChillerBlock.ID, BLAST_CHILLER_BLOCK);
        Registry.register(Registry.ITEM, BlastChillerBlock.ID, new BlockItem(BLAST_CHILLER_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));

        Registry.register(Registry.BLOCK, ThreadCutterBlock.ID, THREAD_CUTTER_BLOCK);
        Registry.register(Registry.ITEM, ThreadCutterBlock.ID, new BlockItem(THREAD_CUTTER_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));
    }

    private void registerEntities()
    {
        BLAST_CHILLER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY, MOD_ID + BlastChillerBlock.ID.getPath(), BlockEntityType.Builder.create(BlastChillerBlockEntity::new, BLAST_CHILLER_BLOCK).build(null));
        THREAD_CUTTER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY, MOD_ID + ThreadCutterBlock.ID.getPath(), BlockEntityType.Builder.create(ThreadCutterBlockEntity::new, THREAD_CUTTER_BLOCK).build(null));
    }

    private void registerRecipes()
    {
        Registry.register(Registry.RECIPE_SERIALIZER, BlastChillingRecipeSerializer.ID, BlastChillingRecipeSerializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(MOD_ID, BlastChillingRecipe.Type.ID), BlastChillingRecipe.Type.INSTANCE);

        Registry.register(Registry.RECIPE_SERIALIZER, ThreadCuttingRecipeSerializer.ID, ThreadCuttingRecipeSerializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(MOD_ID, ThreadCuttingRecipe.Type.ID), ThreadCuttingRecipe.Type.INSTANCE);
    }

    private void registerControllers()
    {
        ContainerProviderRegistry.INSTANCE.registerFactory(BlastChillerBlock.ID, 
            (syncId, id, player, buf) -> new BlastChillerBlockController(syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos()))
        );

        ContainerProviderRegistry.INSTANCE.registerFactory(ThreadCutterBlock.ID, 
            (syncId, id, player, buf) -> new ThreadCutterBlockController(syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos()))
        );
    }
}