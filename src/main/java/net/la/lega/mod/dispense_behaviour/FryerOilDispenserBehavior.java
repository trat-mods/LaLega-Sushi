package net.la.lega.mod.dispense_behaviour;

import net.la.lega.mod.block.FryerBlock;
import net.la.lega.mod.entity.FryerBlockEntity;
import net.la.lega.mod.initializer.LItems;
import net.la.lega.mod.model_enum.OilType;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class FryerOilDispenserBehavior extends ItemDispenserBehavior
{
    @Override
    protected ItemStack dispenseSilently(BlockPointer blockPointer, ItemStack stack)
    {
        World world = blockPointer.getWorld();
        BlockPos blockPos = blockPointer.getBlockPos().offset((Direction) blockPointer.getBlockState().get(DispenserBlock.FACING));
        BlockState blockState = world.getBlockState(blockPos);
        
        if(blockState.getBlock() instanceof FryerBlock && !((FryerBlockEntity) world.getBlockEntity(blockPos)).isOilNew())
        {
            FryerBlockEntity fryerBlockEntity = (FryerBlockEntity) world.getBlockEntity(blockPos);
            if(stack.getItem().equals(LItems.RICE_OIL_BOTTLE))
            {
                fryerBlockEntity.fillOil(OilType.RICE_OIL);
            }
            else if(stack.getItem().equals(LItems.SUNFLOWER_OIL_BOTTLE))
            {
                fryerBlockEntity.fillOil(OilType.SUNFLOWER_OIL);
            }
            
            ItemStack emptyBottleStack = new ItemStack(Items.GLASS_BOTTLE);
            stack.decrement(1);
            this.dispense(blockPointer, emptyBottleStack.copy());
            return stack;
        }
        else
        {
            return super.dispenseSilently(blockPointer, stack);
        }
    }
}