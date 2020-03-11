package net.la.lega.mod.initializer;

import net.la.lega.mod.block.*;
import net.la.lega.mod.item.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public abstract class LItems
{
    public static final Item SASHIMI_SAKE_ITEM = new SashimiSake();
    public static final Item SALMON_FILLET_ITEM = new SalmonFillet();
    public static final Item NIGIRI_SAKE_ITEM = new NigiriSake();
    public static final Item HOSOMAKI_SAKE_ITEM = new HosomakiSake();
    public static final Item URAMAKI_SAKE = new UramakiSake();
    public static final Item RICE_ITEM = new Rice();
    public static Item RICE_SEEDS;
    public static Item AVOCADO = new Avocado();
    public static Item AVOCADO_SEED;
    
    public static void initialize()
    {
        Registry.register(Registry.ITEM, SashimiSake.ID, SASHIMI_SAKE_ITEM);
        Registry.register(Registry.ITEM, SalmonFillet.ID, SALMON_FILLET_ITEM);
        Registry.register(Registry.ITEM, Rice.ID, RICE_ITEM);
        Registry.register(Registry.ITEM, NigiriSake.ID, NIGIRI_SAKE_ITEM);
        Registry.register(Registry.ITEM, HosomakiSake.ID, HOSOMAKI_SAKE_ITEM);
        Registry.register(Registry.ITEM, Avocado.ID, AVOCADO);
        Registry.register(Registry.ITEM, UramakiSake.ID, URAMAKI_SAKE);
        
        AVOCADO_SEED = Registry.register(Registry.ITEM, AvocadoBlock.ID, new BlockItem(LBlocks.AVOCADO_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));
        RICE_SEEDS = Registry.register(Registry.ITEM, RiceBlock.ID, new BlockItem(LBlocks.RICE_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));
        Registry.register(Registry.ITEM, SushiCrafterBlock.ID, new BlockItem(LBlocks.SUSHI_CRAFTER_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));
        Registry.register(Registry.ITEM, ThreadCutterBlock.ID, new BlockItem(LBlocks.THREAD_CUTTER_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));
        Registry.register(Registry.ITEM, BlastChillerBlock.ID, new BlockItem(LBlocks.BLAST_CHILLER_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));
    }
}