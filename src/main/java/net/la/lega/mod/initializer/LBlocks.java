package net.la.lega.mod.initializer;

import net.la.lega.mod.block.*;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;

public abstract class LBlocks
{
    public static final Block BLAST_CHILLER_BLOCK = new BlastChillerBlock();
    public static final Block THREAD_CUTTER_BLOCK = new ThreadCutterBlock();
    public static final Block SUSHI_CRAFTER_BLOCK = new SushiCrafterBlock();
    public static final Block RICE_BLOCK = new RiceBlock();
    public static final Block AVOCADO_BLOCK = new AvocadoBlock();
    
    public static void initialize()
    {
        Registry.register(Registry.BLOCK, BlastChillerBlock.ID, BLAST_CHILLER_BLOCK);
        Registry.register(Registry.BLOCK, ThreadCutterBlock.ID, THREAD_CUTTER_BLOCK);
        Registry.register(Registry.BLOCK, SushiCrafterBlock.ID, SUSHI_CRAFTER_BLOCK);
        Registry.register(Registry.BLOCK, RiceBlock.ID, RICE_BLOCK);
        Registry.register(Registry.BLOCK, AvocadoBlock.ID, AVOCADO_BLOCK);
    }
}