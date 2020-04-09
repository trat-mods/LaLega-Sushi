package net.la.lega.mod.gui.controller;

import io.github.cottonmc.cotton.gui.CottonCraftingController;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Alignment;
import net.la.lega.mod.entity.SteamCookerBlockEntity;
import net.la.lega.mod.recipe.SteamCookingRecipe;
import net.minecraft.container.BlockContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.Identifier;

public class SteamCookerBlockController extends CottonCraftingController
{
    public SteamCookerBlockController(int syncId, PlayerInventory playerInventory, BlockContext context)
    {
        super(SteamCookingRecipe.Type.INSTANCE, syncId, playerInventory, getBlockInventory(context), getBlockPropertyDelegate(context));
        WPlainPanel root = new WPlainPanel();
        setRootPanel(root);
        
        WLabel label = new WLabel("Water level");
        label.setAlignment(Alignment.CENTER);
        WItemSlot inputSlot = WItemSlot.of(blockInventory, SteamCookerBlockEntity.INPUT_SLOT);
        WItemSlot processingSlot = WItemSlot.of(blockInventory, SteamCookerBlockEntity.PROCESSING_SLOT);
        processingSlot.setModifiable(false);
        WItemSlot outputSlot = WItemSlot.outputOf(blockInventory, SteamCookerBlockEntity.OUTPUT_SLOT);
        WBar progressBar = new WBar(new Identifier("lalegamod:textures/ui/rec_progress_bg.png"), new Identifier("lalegamod:textures/ui/rec_progress_bar.png"), SteamCookerBlockEntity.CURRENT_WATER_LEVEL, SteamCookerBlockEntity.MAX_LEVEL, WBar.Direction.RIGHT);
        WPlayerInvPanel playerInvPanel = this.createPlayerInventoryPanel();
        
        WLabel title = new WLabel("Steam Cooker", WLabel.DEFAULT_TEXT_COLOR);
        
        root.add(title, 10, 2);
        root.add(inputSlot, 20, 36);
        root.add(processingSlot, 72, 34);
        root.add(outputSlot, 130, 36);
        root.add(label, 72, 20);
        root.add(progressBar, 56, 50, 48, 20);
        root.add(playerInvPanel, 0, 70);
        root.validate(this);
    }
}
