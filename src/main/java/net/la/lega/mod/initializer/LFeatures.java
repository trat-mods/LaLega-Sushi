package net.la.lega.mod.initializer;

import net.la.lega.mod.feature.RandomAgeCropFeature;
import net.la.lega.mod.loader.LLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorConfig;
import net.minecraft.world.gen.feature.*;

public abstract class LFeatures
{
    private static RandomAgeCropFeature RICE_FEATURE;
    private static RandomAgeCropFeature AVOCADO_FEATURE;
    private static RandomAgeCropFeature WASABI_FEATURE;
    
    public static void initialize()
    {
        RICE_FEATURE = Registry.register(Registry.FEATURE, new Identifier(LLoader.MOD_ID, "rice_feature"), new RandomAgeCropFeature(DefaultFeatureConfig::deserialize, LBlocks.RICE_BLOCK));
        AVOCADO_FEATURE = Registry.register(Registry.FEATURE, new Identifier(LLoader.MOD_ID, "avocado_feature"), new RandomAgeCropFeature(DefaultFeatureConfig::deserialize, LBlocks.AVOCADO_BLOCK));
        WASABI_FEATURE = Registry.register(Registry.FEATURE, new Identifier(LLoader.MOD_ID, "wasabi_feature"), new RandomAgeCropFeature(DefaultFeatureConfig::deserialize, LBlocks.WASABI_BLOCK));
        
        ConfiguredFeature configuredRiceFeature = configureFeature(RICE_FEATURE, new DefaultFeatureConfig(), Decorator.COUNT_CHANCE_HEIGHTMAP, new CountChanceDecoratorConfig(25, 0.5F));
        ConfiguredFeature configuredAvocadoFeature = configureFeature(AVOCADO_FEATURE, new DefaultFeatureConfig(), Decorator.COUNT_CHANCE_HEIGHTMAP, new CountChanceDecoratorConfig(3, 0.175F));
        ConfiguredFeature configuredWasabiFeature = configureFeature(WASABI_FEATURE, new DefaultFeatureConfig(), Decorator.COUNT_CHANCE_HEIGHTMAP, new CountChanceDecoratorConfig(16, 0.2F));
        
        Registry.BIOME.get(Registry.BIOME.getId(Biomes.JUNGLE)).addFeature(GenerationStep.Feature.RAW_GENERATION, configuredRiceFeature);
        Registry.BIOME.get(Registry.BIOME.getId(Biomes.JUNGLE_EDGE)).addFeature(GenerationStep.Feature.RAW_GENERATION, configuredRiceFeature);
        Registry.BIOME.get(Registry.BIOME.getId(Biomes.JUNGLE_HILLS)).addFeature(GenerationStep.Feature.RAW_GENERATION, configuredRiceFeature);
        Registry.BIOME.get(Registry.BIOME.getId(Biomes.BAMBOO_JUNGLE)).addFeature(GenerationStep.Feature.RAW_GENERATION, configuredRiceFeature);
        Registry.BIOME.get(Registry.BIOME.getId(Biomes.BAMBOO_JUNGLE_HILLS)).addFeature(GenerationStep.Feature.RAW_GENERATION, configuredRiceFeature);
        
        Registry.BIOME.get(Registry.BIOME.getId(Biomes.JUNGLE)).addFeature(GenerationStep.Feature.RAW_GENERATION, configuredAvocadoFeature);
        Registry.BIOME.get(Registry.BIOME.getId(Biomes.JUNGLE_EDGE)).addFeature(GenerationStep.Feature.RAW_GENERATION, configuredAvocadoFeature);
        Registry.BIOME.get(Registry.BIOME.getId(Biomes.JUNGLE_HILLS)).addFeature(GenerationStep.Feature.RAW_GENERATION, configuredAvocadoFeature);
        Registry.BIOME.get(Registry.BIOME.getId(Biomes.BAMBOO_JUNGLE)).addFeature(GenerationStep.Feature.RAW_GENERATION, configuredAvocadoFeature);
        Registry.BIOME.get(Registry.BIOME.getId(Biomes.BAMBOO_JUNGLE_HILLS)).addFeature(GenerationStep.Feature.RAW_GENERATION, configuredAvocadoFeature);
        
        Registry.BIOME.get(Registry.BIOME.getId(Biomes.JUNGLE)).addFeature(GenerationStep.Feature.RAW_GENERATION, configuredWasabiFeature);
        Registry.BIOME.get(Registry.BIOME.getId(Biomes.JUNGLE_EDGE)).addFeature(GenerationStep.Feature.RAW_GENERATION, configuredWasabiFeature);
        Registry.BIOME.get(Registry.BIOME.getId(Biomes.JUNGLE_HILLS)).addFeature(GenerationStep.Feature.RAW_GENERATION, configuredWasabiFeature);
        Registry.BIOME.get(Registry.BIOME.getId(Biomes.BAMBOO_JUNGLE)).addFeature(GenerationStep.Feature.RAW_GENERATION, configuredWasabiFeature);
        Registry.BIOME.get(Registry.BIOME.getId(Biomes.BAMBOO_JUNGLE_HILLS)).addFeature(GenerationStep.Feature.RAW_GENERATION, configuredWasabiFeature);
    }
    
    public static <F extends FeatureConfig, D extends DecoratorConfig> ConfiguredFeature<?, ?> configureFeature(Feature<F> feature, F featureConfig, Decorator<D> decorator, D decoratorConfig)
    {
        Feature<DecoratedFeatureConfig> feature2 = feature instanceof FlowerFeature ? Feature.DECORATED_FLOWER : Feature.DECORATED;
        return new ConfiguredFeature(feature2, new DecoratedFeatureConfig(feature.configure(featureConfig), decorator.configure(decoratorConfig)));
    }
}