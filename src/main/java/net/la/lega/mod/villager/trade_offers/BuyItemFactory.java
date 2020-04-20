package net.la.lega.mod.villager.trade_offers;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;

import java.util.Random;

public class BuyItemFactory implements TradeOffers.Factory
{
    private final ItemStack buy;
    private final int price;
    private final int count;
    private final int maxUses;
    private final int experience;
    private final float multiplier;
    
    public BuyItemFactory(Block block, int price, int count, int maxUses, int experience)
    {
        this(new ItemStack(block), price, count, maxUses, experience);
    }
    
    public BuyItemFactory(Item item, int price, int count, int experience)
    {
        this(new ItemStack(item), price, count, 12, experience);
    }
    
    public BuyItemFactory(Item item, int price, int count, int maxUses, int experience)
    {
        this(new ItemStack(item), price, count, maxUses, experience);
    }
    
    public BuyItemFactory(ItemStack itemStack, int price, int count, int maxUses, int expirence)
    {
        this(itemStack, price, count, maxUses, expirence, 0.05F);
    }
    
    public BuyItemFactory(ItemStack itemStack, int price, int count, int maxUses, int experience, float multiplier)
    {
        this.buy = itemStack;
        this.price = price;
        this.count = count;
        this.maxUses = maxUses;
        this.experience = experience;
        this.multiplier = multiplier;
    }
    
    @Override public TradeOffer create(Entity entity, Random random)
    {
        return new TradeOffer(new ItemStack(this.buy.getItem(), this.price), new ItemStack(Items.EMERALD, this.count), this.maxUses, this.experience, this.multiplier);
    }
}
