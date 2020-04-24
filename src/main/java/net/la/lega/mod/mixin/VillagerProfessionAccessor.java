package net.la.lega.mod.mixin;

import blue.endless.jankson.annotation.Nullable;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(VillagerProfession.class)
public interface VillagerProfessionAccessor
{
    @Invoker("<init>")
    static VillagerProfession accessor$create(String id, PointOfInterestType type, ImmutableSet<Item> gatherableItems, ImmutableSet<Block> secondaryJobSites, @Nullable SoundEvent soundEvent)
    {
        throw new AssertionError("Untransformed accessor!");
    }
}