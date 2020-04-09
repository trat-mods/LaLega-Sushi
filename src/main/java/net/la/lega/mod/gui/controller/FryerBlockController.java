package net.la.lega.mod.gui.controller;

import io.github.cottonmc.cotton.gui.CottonCraftingController;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Alignment;
import net.la.lega.mod.entity.FryerBlockEntity;
import net.la.lega.mod.recipe.FryingRecipe;
import net.minecraft.container.BlockContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.Identifier;

public class FryerBlockController extends CottonCraftingController
{
    FryerBlockEntity bufferEntity;
    
    public FryerBlockController(int syncId, PlayerInventory playerInventory, BlockContext context)
    {
        super(FryingRecipe.Type.INSTANCE, syncId, playerInventory, getBlockInventory(context), getBlockPropertyDelegate(context));
        
        initializeBufferEntity(context);
        WPlainPanel root = new WPlainPanel();
        setRootPanel(root);
        
        WDynamicLabel label = new WDynamicLabel(bufferEntity.oilTypeSupplier);
        label.setAlignment(Alignment.CENTER);
        WItemSlot inputSlot = WItemSlot.of(blockInventory, FryerBlockEntity.INPUT_SLOT);
        WItemSlot processingSlot = WItemSlot.of(blockInventory, FryerBlockEntity.PROCESSING_SLOT);
        processingSlot.setModifiable(false);
        WItemSlot outputSlot = WItemSlot.outputOf(blockInventory, FryerBlockEntity.OUTPUT_SLOT);
        WBar progressBar = new WBar(new Identifier("lalegamod:textures/ui/rec_progress_bg.png"), new Identifier("lalegamod:textures/ui/rec_progress_bar.png"), FryerBlockEntity.CURRENT_OIL_USAGE, FryerBlockEntity.MAX_USAGE, WBar.Direction.RIGHT);
        WPlayerInvPanel playerInvPanel = this.createPlayerInventoryPanel();
        
        WLabel title = new WLabel("Fryer", WLabel.DEFAULT_TEXT_COLOR);
        
        root.add(title, 10, 2);
        root.add(inputSlot, 20, 36);
        root.add(processingSlot, 72, 34);
        root.add(outputSlot, 130, 36);
        root.add(label, 72, 20);
        root.add(progressBar, 56, 50, 48, 20);
        root.add(playerInvPanel, 0, 70);
        root.validate(this);
    }
    
    private void initializeBufferEntity(BlockContext context)
    {
        FryerBlockEntity[] lambdaBypass = new FryerBlockEntity[1];
        
        context.run((world, blockPosition) ->
                    {
                        FryerBlockEntity temporaryEntity = (FryerBlockEntity) world.getBlockEntity(blockPosition);
                        lambdaBypass[0] = temporaryEntity;
                    });
        bufferEntity = lambdaBypass[0];
    }
}
