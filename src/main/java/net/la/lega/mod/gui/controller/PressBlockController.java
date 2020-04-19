package net.la.lega.mod.gui.controller;

import io.github.cottonmc.cotton.gui.CottonCraftingController;
import io.github.cottonmc.cotton.gui.widget.*;
import net.la.lega.mod.entity.PressBlockEntity;
import net.la.lega.mod.recipe.PressingRecipe;
import net.minecraft.container.BlockContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.Identifier;

public class PressBlockController extends CottonCraftingController
{
    PressBlockEntity bufferEntity;
    
    public PressBlockController(int syncId, PlayerInventory playerInventory, BlockContext context)
    {
        super(PressingRecipe.Type.INSTANCE, syncId, playerInventory, getBlockInventory(context), getBlockPropertyDelegate(context));
        
        initializeBufferEntity(context);
        WPlainPanel root = new WPlainPanel();
        setRootPanel(root);
        
        //WDynamicLabel label = new WDynamicLabel(bufferEntity.oilTypeSupplier);
        //label.setAlignment(Alignment.CENTER);
        WItemSlot inputSlot = WItemSlot.of(blockInventory, PressBlockEntity.INPUT_SLOT);
        WItemSlot input2Slot = WItemSlot.of(blockInventory, PressBlockEntity.INPUT2_SLOT);
        WItemSlot outputSlot = WItemSlot.outputOf(blockInventory, PressBlockEntity.OUTPUT_SLOT);
        WBar progressBar = new WBar(new Identifier("lalegamod:textures/ui/press_progress_bg.png"), new Identifier("lalegamod:textures/ui/press_progress_bar.png"), PressBlockEntity.PROCESS_TIME, PressBlockEntity.UNIT_PROCESS_TIME, WBar.Direction.DOWN);
        WPlayerInvPanel playerInvPanel = this.createPlayerInventoryPanel();
        
        WLabel title = new WLabel("Press", WLabel.DEFAULT_TEXT_COLOR);
        
        root.add(title, 10, 2);
        root.add(inputSlot, 20, 36);
        root.add(input2Slot, 78, 44);
        root.add(outputSlot, 130, 36);
        //root.add(label, 72, 20);
        root.add(progressBar, 68, 2, 30, 40);
        root.add(playerInvPanel, 0, 70);
        root.validate(this);
    }
    
    private void initializeBufferEntity(BlockContext context)
    {
        PressBlockEntity[] lambdaBypass = new PressBlockEntity[1];
        
        context.run((world, blockPosition) ->
        {
            PressBlockEntity temporaryEntity = (PressBlockEntity) world.getBlockEntity(blockPosition);
            lambdaBypass[0] = temporaryEntity;
        });
        bufferEntity = lambdaBypass[0];
    }
}
