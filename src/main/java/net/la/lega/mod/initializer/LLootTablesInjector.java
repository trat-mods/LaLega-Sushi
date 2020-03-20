package net.la.lega.mod.initializer;

import net.minecraft.util.Identifier;

public abstract class LLootTablesInjector
{
    private static final Identifier SQUID_LOOT_TABLE_ID = new Identifier("minecraft", "entities/squid");
    
    public static void initialize()
    {
        //TODO insert squid fillet
        //LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) ->
        //{
        //    if(SQUID_LOOT_TABLE_ID.equals(id))
        //    {
        //        FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
        //              .withRolls(ConstantLootTableRange.create(1))
        //              .withEntry(ItemEntry.builder(LItems.RICE_ITEM));
        //
        //        supplier.withPool(poolBuilder);
        //    }
        //});
    }
}
