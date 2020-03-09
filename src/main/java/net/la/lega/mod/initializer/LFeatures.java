package net.la.lega.mod.initializer;

import net.minecraft.block.BlockState;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleStateProvider;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;

public abstract class LFeatures
{
    public static RandomPatchFeatureConfig RICE_CONFIG;
    public static RandomPatchFeatureConfig AVOCADO_CONFIG;

    public static void initialize()
    {
        BlockState RICE_STATE = LBlocks.RICE_BLOCK.getDefaultState();
        RICE_CONFIG = (new RandomPatchFeatureConfig.Builder(new SimpleStateProvider(RICE_STATE), new SimpleBlockPlacer())).tries(15).spreadX(6).spreadY(0).spreadZ(6).cannotProject().needsWater().build();
        Registry.BIOME.get(Registry.BIOME.getId(Biomes.BAMBOO_JUNGLE_HILLS)).addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(RICE_CONFIG).createDecoratedFeature(Decorator.COUNT_CHANCE_HEIGHTMAP.configure(new CountChanceDecoratorConfig(12, 0.45F))));
        Registry.BIOME.get(Registry.BIOME.getId(Biomes.JUNGLE)).addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(RICE_CONFIG).createDecoratedFeature(Decorator.COUNT_CHANCE_HEIGHTMAP.configure(new CountChanceDecoratorConfig(8, 0.275F))));
        Registry.BIOME.get(Registry.BIOME.getId(Biomes.BAMBOO_JUNGLE)).addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(RICE_CONFIG).createDecoratedFeature(Decorator.COUNT_CHANCE_HEIGHTMAP.configure(new CountChanceDecoratorConfig(4, 0.325F))));
        Registry.BIOME.get(Registry.BIOME.getId(Biomes.RIVER)).addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(RICE_CONFIG).createDecoratedFeature(Decorator.COUNT_CHANCE_HEIGHTMAP.configure(new CountChanceDecoratorConfig(4, 0.25F))));
        Registry.BIOME.get(Registry.BIOME.getId(Biomes.BIRCH_FOREST_HILLS)).addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(RICE_CONFIG).createDecoratedFeature(Decorator.COUNT_CHANCE_HEIGHTMAP.configure(new CountChanceDecoratorConfig(5, 0.15F))));
        Registry.BIOME.get(Registry.BIOME.getId(Biomes.DARK_FOREST_HILLS)).addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(RICE_CONFIG).createDecoratedFeature(Decorator.COUNT_CHANCE_HEIGHTMAP.configure(new CountChanceDecoratorConfig(5, 0.1F))));

        BlockState AVOCADO_STATE = LBlocks.AVOCADO_BLOCK.getDefaultState();
        AVOCADO_CONFIG = (new RandomPatchFeatureConfig.Builder(new SimpleStateProvider(AVOCADO_STATE), new SimpleBlockPlacer())).tries(16).spreadX(10).spreadY(0).spreadZ(10).cannotProject().build();
        Registry.BIOME.get(Registry.BIOME.getId(Biomes.BAMBOO_JUNGLE_HILLS)).addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(AVOCADO_CONFIG).createDecoratedFeature(Decorator.COUNT_CHANCE_HEIGHTMAP.configure(new CountChanceDecoratorConfig(8, 0.65F))));
        Registry.BIOME.get(Registry.BIOME.getId(Biomes.BAMBOO_JUNGLE)).addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(AVOCADO_CONFIG).createDecoratedFeature(Decorator.COUNT_CHANCE_HEIGHTMAP.configure(new CountChanceDecoratorConfig(5, 0.4F))));
        Registry.BIOME.get(Registry.BIOME.getId(Biomes.JUNGLE)).addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(AVOCADO_CONFIG).createDecoratedFeature(Decorator.COUNT_CHANCE_HEIGHTMAP.configure(new CountChanceDecoratorConfig(4, 0.425F))));
        Registry.BIOME.get(Registry.BIOME.getId(Biomes.RIVER)).addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(AVOCADO_CONFIG).createDecoratedFeature(Decorator.COUNT_CHANCE_HEIGHTMAP.configure(new CountChanceDecoratorConfig(4, 0.275F))));
        Registry.BIOME.get(Registry.BIOME.getId(Biomes.BIRCH_FOREST_HILLS)).addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(AVOCADO_CONFIG).createDecoratedFeature(Decorator.COUNT_CHANCE_HEIGHTMAP.configure(new CountChanceDecoratorConfig(3, 0.25F))));
        Registry.BIOME.get(Registry.BIOME.getId(Biomes.DARK_FOREST_HILLS)).addFeature(GenerationStep.Feature.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configure(AVOCADO_CONFIG).createDecoratedFeature(Decorator.COUNT_CHANCE_HEIGHTMAP.configure(new CountChanceDecoratorConfig(3, 0.187F))));
    }
}