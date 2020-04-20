package net.la.lega.mod.initializer;

import net.la.lega.mod.block.*;
import net.minecraft.util.registry.Registry;

public final class LBlocks
{
    public static final BlastChillerBlock BLAST_CHILLER_BLOCK = new BlastChillerBlock();
    public static final ThreadCutterBlock THREAD_CUTTER_BLOCK = new ThreadCutterBlock();
    public static final SushiCrafterBlock SUSHI_CRAFTER_BLOCK = new SushiCrafterBlock();
    public static final RiceBlock RICE_BLOCK = new RiceBlock();
    public static final AvocadoBlock AVOCADO_BLOCK = new AvocadoBlock();
    public static final WasabiBlock WASABI_BLOCK = new WasabiBlock();
    public static final FryerBlock FRYER_BLOCK = new FryerBlock();
    public static final SteamCookerBlock STEAM_COOKER_BLOCK = new SteamCookerBlock();
    public static final PressBlock PRESS_BLOCK = new PressBlock();
    
    public static void initialize()
    {
        Registry.register(Registry.BLOCK, BlastChillerBlock.ID, BLAST_CHILLER_BLOCK);
        Registry.register(Registry.BLOCK, ThreadCutterBlock.ID, THREAD_CUTTER_BLOCK);
        Registry.register(Registry.BLOCK, SushiCrafterBlock.ID, SUSHI_CRAFTER_BLOCK);
        Registry.register(Registry.BLOCK, RiceBlock.ID, RICE_BLOCK);
        Registry.register(Registry.BLOCK, AvocadoBlock.ID, AVOCADO_BLOCK);
        Registry.register(Registry.BLOCK, WasabiBlock.ID, WASABI_BLOCK);
        Registry.register(Registry.BLOCK, FryerBlock.ID, FRYER_BLOCK);
        Registry.register(Registry.BLOCK, SteamCookerBlock.ID, STEAM_COOKER_BLOCK);
        Registry.register(Registry.BLOCK, PressBlock.ID, PRESS_BLOCK);
    }
}