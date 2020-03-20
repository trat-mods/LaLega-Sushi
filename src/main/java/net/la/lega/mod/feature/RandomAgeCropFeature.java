package net.la.lega.mod.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.CropBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;
import java.util.function.Function;

public class RandomAgeCropFeature extends Feature<DefaultFeatureConfig>
{
    private CropBlock cropBlock;
    
    public RandomAgeCropFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configDeserializer, CropBlock cropBlock)
    {
        super(configDeserializer);
        this.cropBlock = cropBlock;
    }
    
    @Override
    public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, BlockPos pos, DefaultFeatureConfig config)
    {
        if(cropBlock.canPlaceAt(null, world, pos))
        {
            int age = random.nextInt(cropBlock.getMaxAge());
            world.setBlockState(pos, cropBlock.getDefaultState().with(CropBlock.AGE, age), 4);
            
            return true;
        }
        return false;
    }
}
