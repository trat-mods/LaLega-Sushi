package net.la.lega.mod.gui;

import io.github.cottonmc.cotton.gui.CottonCraftingController;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import net.la.lega.mod.entity.ChillBlasterBlockEntity;
import net.la.lega.mod.recipe.ChillBlastingRecipe;
import net.minecraft.container.BlockContext;
import net.minecraft.container.SlotActionType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;

public class ChillBlasterBlockController extends CottonCraftingController 
{
    ChillBlasterBlockEntity bufferEntity;

    public ChillBlasterBlockController(int syncId, PlayerInventory playerInventory, BlockContext context) {
        
        super(ChillBlastingRecipe.Type.INSTANCE, syncId, playerInventory, getBlockInventory(context), getBlockPropertyDelegate(context));

        bufferEntity = getBufferEntity(context);

        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(200, 160);
        WItemSlot inputSlot = WItemSlot.of(blockInventory, 0);
        WItemSlot outputSlot = WItemSlot.outputOf(blockInventory, 1);
        WLabel title = new WLabel("Chill Blaster", WLabel.DEFAULT_TEXT_COLOR);
        root.add(title, 1, 1);
        root.add(inputSlot, 4, 1);
        root.add(outputSlot, 10, 1);
        root.add(this.createPlayerInventoryPanel(), 2, 4);
        root.validate(this);
    }

    public ChillBlasterBlockEntity getBufferEntity(BlockContext context) 
    {
        ChillBlasterBlockEntity[] lambdaBypass = new ChillBlasterBlockEntity[1];

        context.run((world, blockPosition) -> {
            ChillBlasterBlockEntity temporaryEntity = (ChillBlasterBlockEntity) world.getBlockEntity(blockPosition);
            lambdaBypass[0] = temporaryEntity;
        });
        return lambdaBypass[0];
    }

    @Override 
    public ItemStack onSlotClick(int slotNumber, int button, SlotActionType action, PlayerEntity player)
    {

        bufferEntity.slotClicked(slotNumber);
        return super.onSlotClick(slotNumber, button, action, player);
    }
    
}