package net.la.lega.mod.initializer;

import net.la.lega.mod.block.*;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;

public abstract class LSounds
{
    public static SoundEvent THREAD_CUTTER_CUT_SOUNDEVENT = new SoundEvent(ThreadCutterBlock.CUT_SOUND);
    public static SoundEvent BLAST_CHILLER_HUM_SOUNDEVENT = new SoundEvent(BlastChillerBlock.HUM_SOUND);
    public static SoundEvent AVOCADO_HARVEST_SOUNDEVENT = new SoundEvent(AvocadoBlock.HARVEST_SOUND);
    public static SoundEvent FRYER_ON_SOUNDEVENT = new SoundEvent(FryerBlock.ON_SOUND);
    public static SoundEvent STEAM_COOKER_PRESSURE_SOUNDEVENT = new SoundEvent(SteamCookerBlock.PRESSURE_SOUND);
    
    public static void initialize()
    {
        Registry.register(Registry.SOUND_EVENT, ThreadCutterBlock.CUT_SOUND, THREAD_CUTTER_CUT_SOUNDEVENT);
        Registry.register(Registry.SOUND_EVENT, BlastChillerBlock.HUM_SOUND, BLAST_CHILLER_HUM_SOUNDEVENT);
        Registry.register(Registry.SOUND_EVENT, AvocadoBlock.HARVEST_SOUND, AVOCADO_HARVEST_SOUNDEVENT);
        Registry.register(Registry.SOUND_EVENT, FryerBlock.ON_SOUND, FRYER_ON_SOUNDEVENT);
        Registry.register(Registry.SOUND_EVENT, SteamCookerBlock.PRESSURE_SOUND, STEAM_COOKER_PRESSURE_SOUNDEVENT);
    }
}