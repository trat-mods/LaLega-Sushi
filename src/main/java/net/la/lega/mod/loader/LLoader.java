package net.la.lega.mod.loader;

import net.fabricmc.api.ModInitializer;
import net.la.lega.mod.initializer.*;

public class LLoader implements ModInitializer
{
    
    public static final String MOD_ID = "lalegamod";
    
    @Override
    public void onInitialize()
    {
        LBlocks.initialize();
        LItems.initialize();
        LEntities.initialize();
        LVillagerProfessions.initialize();
        LUIControllers.initialize();
        LRecipes.initialize();
        LSounds.initialize();
        LTags.intialize();
        LFeatures.initialize();
        LBehaviors.initialize();
        LLootTablesInjector.initialize();
    }
}