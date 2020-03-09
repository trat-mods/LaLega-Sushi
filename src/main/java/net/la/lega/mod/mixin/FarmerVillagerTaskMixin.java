package net.la.lega.mod.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.la.lega.mod.initializer.LBlocks;
import net.la.lega.mod.initializer.LItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.block.CropBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.ai.brain.BlockPosLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.FarmerVillagerTask;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

@Mixin(FarmerVillagerTask.class)
public abstract class FarmerVillagerTaskMixin
{
    public FarmerVillagerTaskMixin() 
    {
    }

    @Shadow private BlockPos currentTarget;
    @Shadow private List<BlockPos> targetPositions;
    @Shadow private boolean ableToPlant;
    @Shadow private boolean ableToPickUpSeed;
    @Shadow private long nextResponseTime;
    @Shadow private int ticksRan;
    @Shadow abstract BlockPos chooseRandomTarget(ServerWorld world);

    @Inject(at = @At("HEAD"), method = "keepRunning", cancellable = true)
    protected void keepRunning(ServerWorld serverWorld, VillagerEntity villagerEntity, long l, CallbackInfo info) 
    {
        if (this.currentTarget != null && l > this.nextResponseTime) 
        {
           BlockState blockState = serverWorld.getBlockState(this.currentTarget);
           Block block = blockState.getBlock();
           Block block2 = serverWorld.getBlockState(this.currentTarget.down()).getBlock();
           if (block instanceof CropBlock && ((CropBlock)block).isMature(blockState) && this.ableToPickUpSeed) 
           {
              serverWorld.breakBlock(this.currentTarget, true, villagerEntity);
           }
           if (blockState.isAir() && (block2 instanceof FarmlandBlock) && this.ableToPlant) 
           {
                BasicInventory basicInventory = villagerEntity.getInventory();
  
                for(int i = 0; i < basicInventory.getInvSize(); ++i) 
                {
                    ItemStack itemStack = basicInventory.getInvStack(i);
                    boolean bl = false;
                    if (!itemStack.isEmpty()) 
                    {
                        if (itemStack.getItem() == Items.WHEAT_SEEDS) 
                        {
                            serverWorld.setBlockState(this.currentTarget, Blocks.WHEAT.getDefaultState(), 3);
                            bl = true;
                        } else if (itemStack.getItem() == Items.POTATO) 
                        {
                            serverWorld.setBlockState(this.currentTarget, Blocks.POTATOES.getDefaultState(), 3);
                            bl = true;
                        } 
                        else if (itemStack.getItem() == Items.CARROT) 
                        {
                            serverWorld.setBlockState(this.currentTarget, Blocks.CARROTS.getDefaultState(), 3);
                            bl = true;
                        } 
                        else if (itemStack.getItem() == Items.BEETROOT_SEEDS) 
                        {
                            serverWorld.setBlockState(this.currentTarget, Blocks.BEETROOTS.getDefaultState(), 3);
                            bl = true;
                        }
                        else if(itemStack.getItem() == LItems.RICE_SEEDS)
                        {
                            serverWorld.setBlockState(this.currentTarget, LBlocks.RICE_BLOCK.getDefaultState(), 3);
                            bl = true;
                        }
                    }
  
                    if (bl) 
                    {
                        serverWorld.playSound((PlayerEntity)null, (double)this.currentTarget.getX(), (double)this.currentTarget.getY(), (double)this.currentTarget.getZ(), SoundEvents.ITEM_CROP_PLANT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        itemStack.decrement(1);
                        if (itemStack.isEmpty()) 
                        {
                        basicInventory.setInvStack(i, ItemStack.EMPTY);
                        }
                        break;
                    }
                }
            }
            if (block instanceof CropBlock && !((CropBlock)block).isMature(blockState)) 
            {
                this.targetPositions.remove(this.currentTarget);
                this.currentTarget = this.chooseRandomTarget(serverWorld);
                if (this.currentTarget != null) 
                {
                    this.nextResponseTime = l + 20L;
                    villagerEntity.getBrain().putMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new BlockPosLookTarget(this.currentTarget), 0.5F, 1));
                    villagerEntity.getBrain().putMemory(MemoryModuleType.LOOK_TARGET, new BlockPosLookTarget(this.currentTarget));
                }
            }
        }
        ++this.ticksRan;
        info.cancel();
    }
    
}