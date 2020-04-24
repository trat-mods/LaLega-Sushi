package net.la.lega.mod.mixin;

import net.minecraft.block.entity.HopperBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(HopperBlockEntity.class)
public interface HopperBlockEntityAccessor
{
    @Accessor
    long getLastTickTime();
    
    @Invoker("isDisabled")
    boolean is$Disabled();
    
    @Invoker("setCooldown")
    void set$Cooldown(int cooldown);
}
