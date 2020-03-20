package net.la.lega.mod.initializer;

import net.la.lega.mod.block.*;
import net.la.lega.mod.item.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public abstract class LItems
{
    public static final Item SASHIMI_SAKE = new SashimiSake();
    public static final Item SALMON_FILLET = new SalmonFillet();
    public static final Item WASABI = new Wasabi();
    
    public static final Item NIGIRI_SAKE = new NigiriSake();
    public static final Item HOSOMAKI_SAKE = new HosomakiSake();
    public static final Item URAMAKI_SAKE = new UramakiSake();
    public static final Item FUTOMAKI_SAKE = new FutomakiSake();
    
    public static final Item RICE = new Rice();
    public static final Item AVOCADO = new Avocado();
    
    public static Item AVOCADO_SEED;
    public static Item RICE_SEEDS;
    public static Item WASABI_ROOT;
    
    public static void initialize()
    {
        Registry.register(Registry.ITEM, SashimiSake.ID, SASHIMI_SAKE);
        Registry.register(Registry.ITEM, SalmonFillet.ID, SALMON_FILLET);
        Registry.register(Registry.ITEM, Rice.ID, RICE);
        Registry.register(Registry.ITEM, NigiriSake.ID, NIGIRI_SAKE);
        Registry.register(Registry.ITEM, HosomakiSake.ID, HOSOMAKI_SAKE);
        Registry.register(Registry.ITEM, UramakiSake.ID, URAMAKI_SAKE);
        Registry.register(Registry.ITEM, FutomakiSake.ID, FUTOMAKI_SAKE);
        Registry.register(Registry.ITEM, Wasabi.ID, WASABI);
        Registry.register(Registry.ITEM, Avocado.ID, AVOCADO);
        
        AVOCADO_SEED = Registry.register(Registry.ITEM, AvocadoBlock.ID, new BlockItem(LBlocks.AVOCADO_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));
        RICE_SEEDS = Registry.register(Registry.ITEM, RiceBlock.ID, new BlockItem(LBlocks.RICE_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));
        WASABI_ROOT = Registry.register(Registry.ITEM, WasabiBlock.ID, new BlockItem(LBlocks.WASABI_BLOCK, new Item.Settings().group(ItemGroup.FOOD)));
        Registry.register(Registry.ITEM, SushiCrafterBlock.ID, new BlockItem(LBlocks.SUSHI_CRAFTER_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));
        Registry.register(Registry.ITEM, ThreadCutterBlock.ID, new BlockItem(LBlocks.THREAD_CUTTER_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));
        Registry.register(Registry.ITEM, BlastChillerBlock.ID, new BlockItem(LBlocks.BLAST_CHILLER_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));
    }
}