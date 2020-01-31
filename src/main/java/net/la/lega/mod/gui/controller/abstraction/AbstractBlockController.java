package net.la.lega.mod.gui.controller.abstraction;

import io.github.cottonmc.cotton.gui.CottonCraftingController;
import net.la.lega.mod.entity.abstraction.AbstractProcessingOutputterEntity;
import net.minecraft.container.BlockContext;
import net.minecraft.container.PropertyDelegate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.RecipeType;

public abstract class AbstractBlockController extends CottonCraftingController 
{
    protected AbstractProcessingOutputterEntity bufferEntity;

    protected AbstractBlockController(RecipeType<?> recipeType, int syncId, PlayerInventory playerInventory, Inventory blockInventory, PropertyDelegate delegate, BlockContext context) 
    {
        super(recipeType, syncId, playerInventory, blockInventory, delegate);
        initializeBufferEntity(context);
    }
    
    protected AbstractBlockController(RecipeType<?> recipeType, int syncId, PlayerInventory playerInventory, BlockContext context) 
    {
        super(recipeType, syncId, playerInventory);
        initializeBufferEntity(context);
    }

    public AbstractProcessingOutputterEntity getBufferEntity()
    {
        return this.bufferEntity;
    }

    public void initializeBufferEntity(BlockContext context) 
    {
        AbstractProcessingOutputterEntity[] lambdaBypass = new AbstractProcessingOutputterEntity[1];

        context.run((world, blockPosition) -> {
            AbstractProcessingOutputterEntity temporaryEntity = (AbstractProcessingOutputterEntity) world.getBlockEntity(blockPosition);
            lambdaBypass[0] = temporaryEntity;
        });
        bufferEntity = lambdaBypass[0];
    }

    @Override
    public void close(PlayerEntity player)
    {
        bufferEntity = null;
        super.close(player);
    }
}