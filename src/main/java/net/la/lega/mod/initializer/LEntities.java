package net.la.lega.mod.initializer;

import net.la.lega.mod.block.BlastChillerBlock;
import net.la.lega.mod.block.SushiCrafterBlock;
import net.la.lega.mod.block.ThreadCutterBlock;
import net.la.lega.mod.entity.BlastChillerBlockEntity;
import net.la.lega.mod.entity.SushiCrafterBlockEntity;
import net.la.lega.mod.entity.ThreadCutterBlockEntity;
import net.la.lega.mod.loader.LLoader;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

public abstract class LEntities
{
    public static BlockEntityType<BlastChillerBlockEntity> BLAST_CHILLER_BLOCK_ENTITY;
    public static BlockEntityType<ThreadCutterBlockEntity> THREAD_CUTTER_BLOCK_ENTITY;
    public static BlockEntityType<SushiCrafterBlockEntity> SUSHI_CRAFTER_BLOCK_ENTITY;
    
    public static void initialize()
    {
        BLAST_CHILLER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, LLoader.MOD_ID + ":" + BlastChillerBlock.ID.getPath(), BlockEntityType.Builder.create(BlastChillerBlockEntity::new, LBlocks.BLAST_CHILLER_BLOCK).build(null));
        THREAD_CUTTER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, LLoader.MOD_ID + ":" + ThreadCutterBlock.ID.getPath(), BlockEntityType.Builder.create(ThreadCutterBlockEntity::new, LBlocks.THREAD_CUTTER_BLOCK).build(null));
        SUSHI_CRAFTER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, LLoader.MOD_ID + ":" + SushiCrafterBlock.ID.getPath(), BlockEntityType.Builder.create(SushiCrafterBlockEntity::new, LBlocks.SUSHI_CRAFTER_BLOCK).build(null));
    }
}