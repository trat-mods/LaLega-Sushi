package net.la.lega.mod.gui.controller;

import io.github.cottonmc.cotton.gui.CottonCraftingController;
import io.github.cottonmc.cotton.gui.widget.WBar;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.la.lega.mod.recipe.ThreadCuttingRecipe;
import net.minecraft.container.BlockContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.Identifier;

public class ThreadCutterBlockController extends CottonCraftingController
{
    public ThreadCutterBlockController(int syncId, PlayerInventory playerInventory, BlockContext context)
    {
        super(ThreadCuttingRecipe.Type.INSTANCE, syncId, playerInventory, getBlockInventory(context), getBlockPropertyDelegate(context));
        
        WPlainPanel root = new WPlainPanel();
        root.setSize(160, 65);
        
        setRootPanel(root);
        WItemSlot inputSlot = WItemSlot.outputOf(blockInventory, 0);
        WLabel title = new WLabel("Thread Cutter", WLabel.DEFAULT_TEXT_COLOR);
        WBar progressBar = new WBar(new Identifier("lalegamod:textures/ui/progress_knife_bg.png"), new Identifier("lalegamod:textures/ui/progress_knife_bar.png"), 0, 1, WBar.Direction.RIGHT);
        
        root.add(title, 10, 2);
        root.add(progressBar, 100, 31);
        root.add(inputSlot, 72, 32);
        
        root.add(this.createPlayerInventoryPanel(), 0, 70);
        root.validate(this);
    }
}