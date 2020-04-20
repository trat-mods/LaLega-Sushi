package net.la.lega.mod.initializer;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.loot.UniformLootTableRange;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;

public final class LLootTablesInjector
{
    private static final Identifier SQUID_LOOT_TABLE_ID = new Identifier("minecraft", "entities/squid");
    
    public static void initialize()
    {
        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) ->
        {
            if(SQUID_LOOT_TABLE_ID.equals(id))
            {
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder().withRolls(UniformLootTableRange.between(2, 4)).withEntry(ItemEntry.builder(LItems.RAW_SQUID));
                supplier.withPool(poolBuilder);
            }
        });
    }
}
