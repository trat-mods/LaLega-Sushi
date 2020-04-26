package net.la.lega.mod.initializer;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.la.lega.mod.api.LUtils;
import net.la.lega.mod.villager.trade_offers.BuyItemFactory;
import net.la.lega.mod.villager.trade_offers.SellItemFactory;
import net.minecraft.item.Items;
import net.minecraft.util.Unit;
import net.minecraft.village.TradeOffers;

public final class LTradeOffers
{
    //#region SUSHI_MAN
    private final static TradeOffers.Factory[] SUSHIMAN_LVL_1 = new TradeOffers.Factory[]
          {
                new SellItemFactory(Items.PUFFERFISH, 3, 1, 10, 3),
                new BuyItemFactory(LItems.RICE_FLOUR, 18, 1, 8, 2)
          };
    private final static TradeOffers.Factory[] SUSHIMAN_LVL_2 = new TradeOffers.Factory[]
          {
                new SellItemFactory(LItems.AVOCADO, 22, 1, 2, 12),
                new BuyItemFactory(Items.KELP, 32, 1, 10, 8)
          };
    private final static TradeOffers.Factory[] SUSHIMAN_LVL_3 = new TradeOffers.Factory[]
          {
                new SellItemFactory(LItems.WASABI, 4, 5, 5, 8),
                new BuyItemFactory(Items.IRON_INGOT, 16, 1, 4, 6)
          };
    private final static TradeOffers.Factory[] SUSHIMAN_LVL_4 = new TradeOffers.Factory[]
          {
                new SellItemFactory(LItems.WASABI_ROOT, 52, 1, 1, 16)
          };
    private final static TradeOffers.Factory[] SUSHIMAN_LVL_5 = new TradeOffers.Factory[]
          {
                new BuyItemFactory(Items.HEART_OF_THE_SEA, 1, 38, 1, 22),
                new SellItemFactory(LItems.TORAFUGU_KARAAGE, 4, 1, 16, 16)
          };
    private final static ImmutableMap<Integer, TradeOffers.Factory[]> SUSHIMAN_TRADES = ImmutableMap.<Integer, TradeOffers.Factory[]>builder()
          .put(1, SUSHIMAN_LVL_1)
          .put(2, SUSHIMAN_LVL_2)
          .put(3, SUSHIMAN_LVL_3)
          .put(4, SUSHIMAN_LVL_4)
          .put(5, SUSHIMAN_LVL_5)
          .build();
    private static Unit SUSHIMAN_REGISTER = LUtils.executeRunnable(() -> TradeOffers.PROFESSION_TO_LEVELED_TRADE.put(LVillagerProfessions.SUSHI_MAN_PROFESSION, copyToFastUtilMap(SUSHIMAN_TRADES)));
    //#endregion
    
    public static void initialize()
    {
        //No-op
    }
    
    private static Int2ObjectMap<TradeOffers.Factory[]> copyToFastUtilMap(ImmutableMap<Integer, TradeOffers.Factory[]> immutableMap)
    {
        return new Int2ObjectOpenHashMap<>(immutableMap);
    }
}
