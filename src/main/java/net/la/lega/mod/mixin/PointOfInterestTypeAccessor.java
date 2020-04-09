package net.la.lega.mod.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.world.poi.PointOfInterestType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Set;

@Mixin(PointOfInterestType.class)
public interface PointOfInterestTypeAccessor 
{
	@Invoker("<init>")
    static PointOfInterestType accessor$create(String id, Set<BlockState> blockStates, int ticketCount, int searchDistance) 
    {
		throw new AssertionError("Untransformed Accessor!");
	}

	@Invoker("setup")
    static PointOfInterestType accessor$setup(PointOfInterestType pointOfInterestType) 
    {
		throw new AssertionError("Untransformed Accessor!");
	}
}