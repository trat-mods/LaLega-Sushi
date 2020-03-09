package net.la.lega.mod.initializer;

import com.google.common.collect.ImmutableSet;

import net.la.lega.mod.loader.LLoader;
import net.la.lega.mod.mixin.PointOfInterestTypeAccessor;
import net.la.lega.mod.mixin.VillagerProfessionAccessor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

public abstract class LVillagerProfessions
{
    public static final String sushiManId = "sushi_man";
    public static VillagerProfession SUSHI_MAN_PROFESSION;
    public static PointOfInterestType SUSHI_PI;

    public static void initialize()
    {
        SUSHI_PI = PointOfInterestTypeAccessor.accessor$setup(Registry.POINT_OF_INTEREST_TYPE.add(new Identifier(LLoader.MOD_ID, LVillagerProfessions.sushiManId), PointOfInterestTypeAccessor.accessor$create(LVillagerProfessions.sushiManId, ImmutableSet.copyOf(LBlocks.SUSHI_CRAFTER_BLOCK.getStateManager().getStates()), 1, 1)));
        SUSHI_MAN_PROFESSION = Registry.register(Registry.VILLAGER_PROFESSION, new Identifier(LLoader.MOD_ID, LVillagerProfessions.sushiManId), VillagerProfessionAccessor.accessor$create(LVillagerProfessions.sushiManId, LVillagerProfessions.SUSHI_PI, ImmutableSet.of(), ImmutableSet.of(), null));
    }
}