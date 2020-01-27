package net.la.lega.mod.gui.controller;

import io.github.cottonmc.cotton.gui.widget.WBar;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.la.lega.mod.gui.controller.abstraction.AbstractBlockController;
import net.la.lega.mod.recipe.BlastChillingRecipe;
import net.minecraft.container.BlockContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.Identifier;

public class BlastChillerBlockController extends AbstractBlockController 
{
    protected int currentProcessTime = -1;
    protected int unitProcessTime = -1;

    public BlastChillerBlockController(int syncId, PlayerInventory playerInventory, BlockContext context) 
    {
        super(BlastChillingRecipe.Type.INSTANCE, syncId, playerInventory, getBlockInventory(context), getBlockPropertyDelegate(context), context);

        WPlainPanel root = new WPlainPanel();
        root.setSize(158, 140);
        setRootPanel(root);
        WItemSlot inputSlot = WItemSlot.of(blockInventory, 0);
        WItemSlot outputSlot = WItemSlot.outputOf(blockInventory, 1);
        WLabel title = new WLabel("Blast Chiller", WLabel.DEFAULT_TEXT_COLOR);

        WBar progressBar = new WBar(new Identifier("lalegamod:textures/progress_bg.png"), new Identifier("lalegamod:textures/progress_bar.png"), 0, 1, WBar.Direction.RIGHT);

        //ProgressBarDescriptor descriptor = new ProgressBarDescriptor(getPropertyDelegate());
        //progressBar.createPeers(descriptor);

        root.add(title, 10, 2);
        root.add(inputSlot, 36, 32);
        root.add(outputSlot, 108, 32);
        root.add(progressBar, 70, 32);
        root.add(this.createPlayerInventoryPanel(), 0, 70);
        root.validate(this);
    }
}