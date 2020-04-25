package net.la.lega.mod.initializer;

import net.la.lega.mod.block.*;
import net.la.lega.mod.item.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public final class LItems
{
    public static final Item AVOCADO = new Avocado();
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
    public static final Item RAW_GYOZA = new RawGyoza();
    public static final Item GYOZA = new Gyoza();
    public static final Item TORAFUGU_KARAAGE = new TorafuguKaraage();
    public static final Item FRIED_URAMAKI_SAKE = new FriedUramakiSake();
    public static final Item FRIED_FUTOMAKI_SAKE = new FriedFutomakiSake();
    public static final Item FRIED_HOSOMAKI_SAKE = new FriedHosomakiSake();
    
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
        Registry.register(Registry.ITEM, RawGyoza.ID, RAW_GYOZA);
        Registry.register(Registry.ITEM, Gyoza.ID, GYOZA);
        Registry.register(Registry.ITEM, TorafuguKaraage.ID, TORAFUGU_KARAAGE);
        Registry.register(Registry.ITEM, FriedUramakiSake.ID, FRIED_URAMAKI_SAKE);
        Registry.register(Registry.ITEM, FriedFutomakiSake.ID, FRIED_FUTOMAKI_SAKE);
        Registry.register(Registry.ITEM, FriedHosomakiSake.ID, FRIED_HOSOMAKI_SAKE);
        
        Registry.register(Registry.ITEM, WheatFlour.ID, WHEAT_FLOUR);
        Registry.register(Registry.ITEM, SunflowerFlour.ID, SUNFLOWER_FLOUR);
        Registry.register(Registry.ITEM, RiceFlour.ID, RICE_FLOUR);
        
        Registry.register(Registry.ITEM, NigiriSakeWasabi.ID, NIGIRI_SAKE_WASABI);
        Registry.register(Registry.ITEM, HosomakiSakeWasabi.ID, HOSOMAKI_SAKE_WASABI);
        Registry.register(Registry.ITEM, UramakiSakeWasabi.ID, URAMAKI_SAKE_WASABI);
        Registry.register(Registry.ITEM, FutomakiSakeWasabi.ID, FUTOMAKI_SAKE_WASABI);
        
        AVOCADO_SEED = Registry.register(Registry.ITEM, AvocadoBlock.ID, new BlockItem(LBlocks.AVOCADO_BLOCK, new Item.Settings().group(LItemGroups.LALEGA_PLANTS)));
        RICE_SEEDS = Registry.register(Registry.ITEM, RiceBlock.ID, new BlockItem(LBlocks.RICE_BLOCK, new Item.Settings().group(LItemGroups.LALEGA_PLANTS)));
        WASABI_ROOT = Registry.register(Registry.ITEM, WasabiBlock.ID, new BlockItem(LBlocks.WASABI_BLOCK, new Item.Settings().group(LItemGroups.LALEGA_PLANTS)));
        
        Registry.register(Registry.ITEM, SushiCrafterBlock.ID, new BlockItem(LBlocks.SUSHI_CRAFTER_BLOCK, new Item.Settings().group(LItemGroups.LALEGA_BLOCKS)));
        Registry.register(Registry.ITEM, ThreadCutterBlock.ID, new BlockItem(LBlocks.THREAD_CUTTER_BLOCK, new Item.Settings().group(LItemGroups.LALEGA_BLOCKS)));
        Registry.register(Registry.ITEM, BlastChillerBlock.ID, new BlockItem(LBlocks.BLAST_CHILLER_BLOCK, new Item.Settings().group(LItemGroups.LALEGA_BLOCKS)));
        Registry.register(Registry.ITEM, FryerBlock.ID, new BlockItem(LBlocks.FRYER_BLOCK, new Item.Settings().group(LItemGroups.LALEGA_BLOCKS)));
        Registry.register(Registry.ITEM, SteamCookerBlock.ID, new BlockItem(LBlocks.STEAM_COOKER_BLOCK, new Item.Settings().group(LItemGroups.LALEGA_BLOCKS)));
        Registry.register(Registry.ITEM, PressBlock.ID, new BlockItem(LBlocks.PRESS_BLOCK, new Item.Settings().group(LItemGroups.LALEGA_BLOCKS)));
        Registry.register(Registry.ITEM, QuadrhopperBlock.ID, new BlockItem(LBlocks.PENTAHOPPER_BLOCK, new Item.Settings().group(ItemGroup.REDSTONE)));
    }
}