package net.la.lega.mod.gui.controller;

import io.github.cottonmc.cotton.gui.CottonCraftingController;
import io.github.cottonmc.cotton.gui.widget.WBar;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.la.lega.mod.entity.abstraction.AProcessingEntity;
import net.la.lega.mod.recipe.BlastChillingRecipe;
import net.minecraft.container.BlockContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.Identifier;

public class BlastChillerBlockController extends CottonCraftingController
{
    public BlastChillerBlockController(int syncId, PlayerInventory playerInventory, BlockContext context)
    {
        super(BlastChillingRecipe.Type.INSTANCE, syncId, playerInventory, getBlockInventory(context), getBlockPropertyDelegate(context));
        
        WPlainPanel root = new WPlainPanel();
        root.setSize(158, 140);
        setRootPanel(root);
        WItemSlot inputSlot = WItemSlot.of(blockInventory, 0);
        WItemSlot outputSlot = WItemSlot.outputOf(blockInventory, 1);
        WLabel title = new WLabel("Blast Chiller", WLabel.DEFAULT_TEXT_COLOR);
        
        WBar progressBar = new WBar(new Identifier("lalegamod:textures/ui/progress_bg.png"), new Identifier("lalegamod:textures/ui/progress_bar.png"), AProcessingEntity.PROCESS_TIME, AProcessingEntity.UNIT_PROCESS_TIME, WBar.Direction.RIGHT);
        
        root.add(title, 10, 2);
        root.add(inputSlot, 34, 32);
        root.add(outputSlot, 110, 32);
        root.add(progressBar, 68, 32, 26, 17);
        root.add(this.createPlayerInventoryPanel(), 0, 70);
        root.validate(this);
    }
}