package net.la.lega.mod.loader;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.la.lega.mod.block.RiceBlock;
import net.la.lega.mod.block.BlastChillerBlock;
import net.la.lega.mod.block.ExtremeLauncherBlock;
import net.la.lega.mod.block.LauncherBlock;
import net.la.lega.mod.block.PoweredLauncherBlock;
import net.la.lega.mod.block.SushiCrafterBlock;
import net.la.lega.mod.block.ThreadCutterBlock;
import net.la.lega.mod.block.AvocadoBlock;
import net.la.lega.mod.entity.BlastChillerBlockEntity;
import net.la.lega.mod.entity.SushiCrafterBlockEntity;
import net.la.lega.mod.entity.ThreadCutterBlockEntity;
import net.la.lega.mod.gui.controller.BlastChillerBlockController;
import net.la.lega.mod.gui.controller.SushiCrafterBlockController;
import net.la.lega.mod.gui.controller.ThreadCutterBlockController;
import net.la.lega.mod.item.Avocado;
import net.la.lega.mod.item.HosomakiSake;
import net.la.lega.mod.item.NigiriSake;
import net.la.lega.mod.item.Rice;
import net.la.lega.mod.item.SalmonFillet;
import net.la.lega.mod.item.SashimiSake;
import net.la.lega.mod.item.UramakiSake;
import net.la.lega.mod.recipe.BlastChillingRecipe;
import net.la.lega.mod.recipe.SushiCraftingRecipe;
import net.la.lega.mod.recipe.ThreadCuttingRecipe;
import net.la.lega.mod.recipe.serializer.BlastChillingRecipeSerializer;
import net.la.lega.mod.recipe.serializer.SushiCraftingRecipeSerializer;
import net.la.lega.mod.recipe.serializer.ThreadCuttingRecipeSerializer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.container.BlockContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleStateProvider;

// MinecartSoundInstance
// MinecartEntity
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
// ShapedRecipe
// ShapelessRecipe
// Blocks
// Items
// Pumpking
// PlayerEntity
// SugarCane
// SugarcaneFeature
// DefaultBiomeFeatures
// BeehiveBlock

public class LaLegaLoader implements ModInitializer 
{
        public static final String MOD_ID = "lalegamod";

        public static Tag<Item> SUSHI_FISH;
        private String sushi_fish_id = "sushi_fish";
        public static Tag<Item> SUSHI_INGREDIENT;
        private String sushi_ingredient_id = "sushi_ingredient";

        // SOUND EVENTS
        public static SoundEvent THREAD_CUTTER_CUT_SOUNDEVENT = new SoundEvent(ThreadCutterBlock.CUT_SOUND);
        public static SoundEvent BLAST_CHILLER_HUM_SOUNDEVENT = new SoundEvent(BlastChillerBlock.HUM_SOUND);
        public static SoundEvent AVOCADO_HARVEST_SOUNDEVENT = new SoundEvent(AvocadoBlock.HARVEST_SOUND);

        // #region ITEMS
        // Items
        public static final Item SASHIMI_SAKE_ITEM = new SashimiSake();
        public static final Item SALMON_FILLET_ITEM = new SalmonFillet();
        public static final Item RICE_ITEM = new Rice();
        public static final Item NIGIRI_SAKE_ITEM = new NigiriSake();
        public static final Item HOSOMAKI_SAKE_ITEM = new HosomakiSake();
        public static final Item URAMAKI_SAKE = new UramakiSake();
        public static Item RICE_SEEDS;

        public static Item AVOCADO = new Avocado();
        public static Item AVOCADO_SEED;
        // #endregion

        // Blocks
        public static final Block LAUNCHER_BLOCK = new LauncherBlock();
        public static final Block POWERED_LAUNCHER_BLOCK = new PoweredLauncherBlock();
        public static final Block EXTREME_LAUNCHER_BLOCK = new ExtremeLauncherBlock();
        public static final Block BLAST_CHILLER_BLOCK = new BlastChillerBlock();
        public static final Block THREAD_CUTTER_BLOCK = new ThreadCutterBlock();
        public static final Block SUSHI_CRAFTER_BLOCK = new SushiCrafterBlock();
        public static final Block RICE_BLOCK = new RiceBlock();

        public static final Block AVOCADO_BLOCK = new AvocadoBlock();

        public static RandomPatchFeatureConfig RICE_CONFIG;
        public static RandomPatchFeatureConfig AVOCADO_CONFIG;

        // Entities
        public static BlockEntityType<BlastChillerBlockEntity> BLAST_CHILLER_BLOCK_ENTITY;
        public static BlockEntityType<ThreadCutterBlockEntity> THREAD_CUTTER_BLOCK_ENTITY;
        public static BlockEntityType<SushiCrafterBlockEntity> SUSHI_CRAFTER_BLOCK_ENTITY;

        @Override
        public void onInitialize() 
        {
                registerBlocks();
                registerItems();
                registerEntities();
                registerRecipes();
                registerControllers();
                registerSounds();
                registerTags();
                registerFeatures();
        }

        private void registerItems() 
        {
                Registry.register(Registry.ITEM, SashimiSake.ID, SASHIMI_SAKE_ITEM);
                Registry.register(Registry.ITEM, SalmonFillet.ID, SALMON_FILLET_ITEM);
                Registry.register(Registry.ITEM, Rice.ID, RICE_ITEM);
                Registry.register(Registry.ITEM, NigiriSake.ID, NIGIRI_SAKE_ITEM);
                Registry.register(Registry.ITEM, HosomakiSake.ID, HOSOMAKI_SAKE_ITEM);
                Registry.register(Registry.ITEM, Avocado.ID, AVOCADO);
                Registry.register(Registry.ITEM, UramakiSake.ID, URAMAKI_SAKE);
        }

        private void registerBlocks() 
        {
                Registry.register(Registry.BLOCK, LauncherBlock.ID, LAUNCHER_BLOCK);
                Registry.register(Registry.ITEM, LauncherBlock.ID, new BlockItem(LAUNCHER_BLOCK, new Item.Settings().group(ItemGroup.REDSTONE)));

                Registry.register(Registry.BLOCK, PoweredLauncherBlock.ID, POWERED_LAUNCHER_BLOCK);
                Registry.register(Registry.ITEM, PoweredLauncherBlock.ID, new BlockItem(POWERED_LAUNCHER_BLOCK, new Item.Settings().group(ItemGroup.REDSTONE)));

                Registry.register(Registry.BLOCK, ExtremeLauncherBlock.ID, EXTREME_LAUNCHER_BLOCK);
                Registry.register(Registry.ITEM, ExtremeLauncherBlock.ID, new BlockItem(EXTREME_LAUNCHER_BLOCK, new Item.Settings().group(ItemGroup.REDSTONE)));

                Registry.register(Registry.BLOCK, BlastChillerBlock.ID, BLAST_CHILLER_BLOCK);
                Registry.register(Registry.ITEM, BlastChillerBlock.ID, new BlockItem(BLAST_CHILLER_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));

                Registry.register(Registry.BLOCK, ThreadCutterBlock.ID, THREAD_CUTTER_BLOCK);
                Registry.register(Registry.ITEM, ThreadCutterBlock.ID, new BlockItem(THREAD_CUTTER_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));

                Registry.register(Registry.BLOCK, SushiCrafterBlock.ID, SUSHI_CRAFTER_BLOCK);
                Registry.register(Registry.ITEM, SushiCrafterBlock.ID, new BlockItem(SUSHI_CRAFTER_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));

                Registry.register(Registry.BLOCK, RiceBlock.ID, RICE_BLOCK);
                RICE_SEEDS = Registry.register(Registry.ITEM, RiceBlock.ID, new BlockItem(RICE_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));

                Registry.register(Registry.BLOCK, AvocadoBlock.ID, AVOCADO_BLOCK);
                AVOCADO_SEED = Registry.register(Registry.ITEM, AvocadoBlock.ID, new BlockItem(AVOCADO_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));
        }

        private void registerEntities() 
        {
                BLAST_CHILLER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                                MOD_ID + BlastChillerBlock.ID.getPath(), BlockEntityType.Builder
                                                .create(BlastChillerBlockEntity::new, BLAST_CHILLER_BLOCK).build(null));
                THREAD_CUTTER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                                MOD_ID + ThreadCutterBlock.ID.getPath(), BlockEntityType.Builder
                                                .create(ThreadCutterBlockEntity::new, THREAD_CUTTER_BLOCK).build(null));
                SUSHI_CRAFTER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                                MOD_ID + SushiCrafterBlock.ID.getPath(), BlockEntityType.Builder
                                                .create(SushiCrafterBlockEntity::new, SUSHI_CRAFTER_BLOCK).build(null));
        }

        private void registerRecipes() 
        {
                Registry.register(Registry.RECIPE_SERIALIZER, BlastChillingRecipeSerializer.ID,
                                BlastChillingRecipeSerializer.INSTANCE);
                Registry.register(Registry.RECIPE_TYPE, new Identifier(MOD_ID, BlastChillingRecipe.Type.ID),
                                BlastChillingRecipe.Type.INSTANCE);

                Registry.register(Registry.RECIPE_SERIALIZER, ThreadCuttingRecipeSerializer.ID,
                                ThreadCuttingRecipeSerializer.INSTANCE);
                Registry.register(Registry.RECIPE_TYPE, new Identifier(MOD_ID, ThreadCuttingRecipe.Type.ID),
                                ThreadCuttingRecipe.Type.INSTANCE);

                Registry.register(Registry.RECIPE_SERIALIZER, SushiCraftingRecipeSerializer.ID,
                                SushiCraftingRecipeSerializer.INSTANCE);
                Registry.register(Registry.RECIPE_TYPE, new Identifier(MOD_ID, SushiCraftingRecipe.Type.ID),
                                SushiCraftingRecipe.Type.INSTANCE);
        }

        private void registerControllers() 
        {
                ContainerProviderRegistry.INSTANCE.registerFactory(BlastChillerBlock.ID,
                                (syncId, id, player, buf) -> new BlastChillerBlockController(syncId, player.inventory,
                                                BlockContext.create(player.world, buf.readBlockPos())));

                ContainerProviderRegistry.INSTANCE.registerFactory(ThreadCutterBlock.ID,
                                (syncId, id, player, buf) -> new ThreadCutterBlockController(syncId, player.inventory,
                                                BlockContext.create(player.world, buf.readBlockPos())));

                ContainerProviderRegistry.INSTANCE.registerFactory(SushiCrafterBlock.ID,
                                (syncId, id, player, buf) -> new SushiCrafterBlockController(syncId, player.inventory,
                                                BlockContext.create(player.world, buf.readBlockPos())));
        }

        private void registerSounds() 
        {
                Registry.register(Registry.SOUND_EVENT, ThreadCutterBlock.CUT_SOUND, THREAD_CUTTER_CUT_SOUNDEVENT);
                Registry.register(Registry.SOUND_EVENT, BlastChillerBlock.HUM_SOUND, BLAST_CHILLER_HUM_SOUNDEVENT);
                Registry.register(Registry.SOUND_EVENT, AvocadoBlock.HARVEST_SOUND, AVOCADO_HARVEST_SOUNDEVENT);
        }

        private void registerTags() 
        {
                SUSHI_FISH = TagRegistry.item(new Identifier(MOD_ID, sushi_fish_id));
                SUSHI_INGREDIENT = TagRegistry.item(new Identifier(MOD_ID, sushi_ingredient_id));
        }

        private void registerFeatures()
        {
                BlockState RICE_STATE = RICE_BLOCK.getDefaultState();
                RICE_CONFIG = (new RandomPatchFeatureConfig.Builder(new SimpleStateProvider(RICE_STATE), new SimpleBlockPlacer())).tries(15).spreadX(6).spreadY(0).spreadZ(6).cannotProject().needsWater().build();
                Registry.BIOME.get(Registry.BIOME.getId(Biomes.JUNGLE)).addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(RICE_CONFIG).createDecoratedFeature(Decorator.COUNT_CHANCE_HEIGHTMAP.configure(new CountChanceDecoratorConfig(4, 0.35F))));
                Registry.BIOME.get(Registry.BIOME.getId(Biomes.BAMBOO_JUNGLE)).addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(RICE_CONFIG).createDecoratedFeature(Decorator.COUNT_CHANCE_HEIGHTMAP.configure(new CountChanceDecoratorConfig(4, 0.25F))));
                Registry.BIOME.get(Registry.BIOME.getId(Biomes.BAMBOO_JUNGLE_HILLS)).addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(RICE_CONFIG).createDecoratedFeature(Decorator.COUNT_CHANCE_HEIGHTMAP.configure(new CountChanceDecoratorConfig(12, 0.45F))));
                Registry.BIOME.get(Registry.BIOME.getId(Biomes.RIVER)).addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(RICE_CONFIG).createDecoratedFeature(Decorator.COUNT_CHANCE_HEIGHTMAP.configure(new CountChanceDecoratorConfig(8, 0.275F))));
                Registry.BIOME.get(Registry.BIOME.getId(Biomes.BIRCH_FOREST_HILLS)).addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(RICE_CONFIG).createDecoratedFeature(Decorator.COUNT_CHANCE_HEIGHTMAP.configure(new CountChanceDecoratorConfig(5, 0.15F))));
                Registry.BIOME.get(Registry.BIOME.getId(Biomes.DARK_FOREST_HILLS)).addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(RICE_CONFIG).createDecoratedFeature(Decorator.COUNT_CHANCE_HEIGHTMAP.configure(new CountChanceDecoratorConfig(5, 0.1F))));

                BlockState AVOCADO_STATE = AVOCADO_BLOCK.getDefaultState();
                AVOCADO_CONFIG = (new RandomPatchFeatureConfig.Builder(new SimpleStateProvider(AVOCADO_STATE), new SimpleBlockPlacer())).tries(15).spreadX(6).spreadY(0).spreadZ(6).cannotProject().needsWater().build();
                Registry.BIOME.get(Registry.BIOME.getId(Biomes.JUNGLE)).addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(AVOCADO_CONFIG).createDecoratedFeature(Decorator.COUNT_CHANCE_HEIGHTMAP.configure(new CountChanceDecoratorConfig(2, 0.6F))));
                Registry.BIOME.get(Registry.BIOME.getId(Biomes.BAMBOO_JUNGLE)).addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(AVOCADO_CONFIG).createDecoratedFeature(Decorator.COUNT_CHANCE_HEIGHTMAP.configure(new CountChanceDecoratorConfig(3, 0.5F))));
                Registry.BIOME.get(Registry.BIOME.getId(Biomes.BAMBOO_JUNGLE_HILLS)).addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(AVOCADO_CONFIG).createDecoratedFeature(Decorator.COUNT_CHANCE_HEIGHTMAP.configure(new CountChanceDecoratorConfig(8, 0.75F))));
                Registry.BIOME.get(Registry.BIOME.getId(Biomes.RIVER)).addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(AVOCADO_CONFIG).createDecoratedFeature(Decorator.COUNT_CHANCE_HEIGHTMAP.configure(new CountChanceDecoratorConfig(6, 0.25F))));
                Registry.BIOME.get(Registry.BIOME.getId(Biomes.BIRCH_FOREST_HILLS)).addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(AVOCADO_CONFIG).createDecoratedFeature(Decorator.COUNT_CHANCE_HEIGHTMAP.configure(new CountChanceDecoratorConfig(3, 0.25F))));
                Registry.BIOME.get(Registry.BIOME.getId(Biomes.DARK_FOREST_HILLS)).addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(AVOCADO_CONFIG).createDecoratedFeature(Decorator.COUNT_CHANCE_HEIGHTMAP.configure(new CountChanceDecoratorConfig(3, 0.187F))));
        }
}