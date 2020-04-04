package net.la.lega.mod.initializer;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.la.lega.mod.block.*;
import net.la.lega.mod.item.*;
import net.la.lega.mod.loader.LLoader;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public abstract class LItems
{
    public static final Item AVOCADO = new Avocado();
    public static final ItemGroup JAPAN_RELATED = FabricItemGroupBuilder.create(new Identifier(LLoader.MOD_ID, "japan_related")).icon(() -> new ItemStack(AVOCADO)).build();
    
    public static final Item SASHIMI_SAKE = new SashimiSake();
    public static final Item SALMON_FILLET = new SalmonFillet();
    public static final Item WASABI = new Wasabi();
    public static final Item NIGIRI_SAKE = new NigiriSake();
    public static final Item HOSOMAKI_SAKE = new HosomakiSake();
    public static final Item URAMAKI_SAKE = new UramakiSake();
    public static final Item FUTOMAKI_SAKE = new FutomakiSake();
    public static final Item RAW_SQUID = new RawSquid();
    public static final Item COOKED_SQUID = new CookedSquid();
    public static final Item SQUID_SKEWER = new SquidSkewer();
    public static final Item RICE = new Rice();
    public static final Item NIGIRI_SAKE_WASABI = new NigiriSakeWasabi();
    public static final Item HOSOMAKI_SAKE_WASABI = new HosomakiSakeWasabi();
    public static final Item URAMAKI_SAKE_WASABI = new UramakiSakeWasabi();
    public static final Item FUTOMAKI_SAKE_WASABI = new FutomakiSakeWasabi();
    public static final Item RICE_FLOUR = new RiceFlour();
    public static final Item SUNFLOWER_FLOUR = new SunflowerFlour();
    public static final Item WHEAT_FLOUR = new WheatFlour();
    public static final Item MINCHED_PORK = new MinchedPork();
    public static final Item SUNFLOWER_OIL_BOTTLE = new SunflowerOilBottle();
    public static final Item RICE_OIL_BOTTLE = new RiceOilBottle();
    public static final Item PUFFER_FISH_FILLET = new PufferFishFillet();
    public static final Item SASHIMI_FUGU = new SashimiFugu();
    
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
        Registry.register(Registry.ITEM, RawSquid.ID, RAW_SQUID);
        Registry.register(Registry.ITEM, CookedSquid.ID, COOKED_SQUID);
        Registry.register(Registry.ITEM, SquidSkewer.ID, SQUID_SKEWER);
        Registry.register(Registry.ITEM, MinchedPork.ID, MINCHED_PORK);
        Registry.register(Registry.ITEM, SunflowerOilBottle.ID, SUNFLOWER_OIL_BOTTLE);
        Registry.register(Registry.ITEM, RiceOilBottle.ID, RICE_OIL_BOTTLE);
        Registry.register(Registry.ITEM, SashimiFugu.ID, SASHIMI_FUGU);
        Registry.register(Registry.ITEM, PufferFishFillet.ID, PUFFER_FISH_FILLET);
        
        Registry.register(Registry.ITEM, WheatFlour.ID, WHEAT_FLOUR);
        Registry.register(Registry.ITEM, SunflowerFlour.ID, SUNFLOWER_FLOUR);
        Registry.register(Registry.ITEM, RiceFlour.ID, RICE_FLOUR);
        
        Registry.register(Registry.ITEM, NigiriSakeWasabi.ID, NIGIRI_SAKE_WASABI);
        Registry.register(Registry.ITEM, HosomakiSakeWasabi.ID, HOSOMAKI_SAKE_WASABI);
        Registry.register(Registry.ITEM, UramakiSakeWasabi.ID, URAMAKI_SAKE_WASABI);
        Registry.register(Registry.ITEM, FutomakiSakeWasabi.ID, FUTOMAKI_SAKE_WASABI);
        
        AVOCADO_SEED = Registry.register(Registry.ITEM, AvocadoBlock.ID, new BlockItem(LBlocks.AVOCADO_BLOCK, new Item.Settings().group(JAPAN_RELATED)));
        RICE_SEEDS = Registry.register(Registry.ITEM, RiceBlock.ID, new BlockItem(LBlocks.RICE_BLOCK, new Item.Settings().group(JAPAN_RELATED)));
        WASABI_ROOT = Registry.register(Registry.ITEM, WasabiBlock.ID, new BlockItem(LBlocks.WASABI_BLOCK, new Item.Settings().group(JAPAN_RELATED)));
        Registry.register(Registry.ITEM, SushiCrafterBlock.ID, new BlockItem(LBlocks.SUSHI_CRAFTER_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));
        Registry.register(Registry.ITEM, ThreadCutterBlock.ID, new BlockItem(LBlocks.THREAD_CUTTER_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));
        Registry.register(Registry.ITEM, BlastChillerBlock.ID, new BlockItem(LBlocks.BLAST_CHILLER_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));
    }
}