package net.la.lega.mod.gui.controller;

import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.la.lega.mod.gui.controller.abstraction.AbstractBlockController;
import net.la.lega.mod.recipe.ThreadCuttingRecipe;
import net.minecraft.container.BlockContext;
import net.minecraft.entity.player.PlayerInventory;

public class ThreadCutterBlockController extends AbstractBlockController 
{
    public ThreadCutterBlockController(int syncId, PlayerInventory playerInventory, BlockContext context) 
    {
        super(ThreadCuttingRecipe.Type.INSTANCE, syncId, playerInventory, getBlockInventory(context), null, context);

        WPlainPanel root = new WPlainPanel();
        root.setSize(160, 65);

        setRootPanel(root);
        WItemSlot inputSlot = WItemSlot.outputOf(blockInventory, 0);
        WLabel title = new WLabel("Thread Cutter", WLabel.DEFAULT_TEXT_COLOR);

        root.add(title, 10, 2);
        root.add(inputSlot, 72, 32);

        root.add(this.createPlayerInventoryPanel(), 0, 70);
        root.validate(this);
    }
}