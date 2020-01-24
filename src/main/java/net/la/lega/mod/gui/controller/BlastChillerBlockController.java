package net.la.lega.mod.gui.controller;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.ValidatedSlot;
import io.github.cottonmc.cotton.gui.widget.WBar;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPanel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import net.la.lega.mod.entity.BlastChillerBlockEntity;
import net.la.lega.mod.gui.controller.abstraction.AbstractBlockController;
import net.la.lega.mod.recipe.BlastChillingRecipe;
import net.minecraft.container.BlockContext;
import net.minecraft.container.PropertyDelegate;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.Identifier;

public class BlastChillerBlockController extends AbstractBlockController
{
    public BlastChillerBlockController(int syncId, PlayerInventory playerInventory, BlockContext context) 
    {
        super(BlastChillingRecipe.Type.INSTANCE, syncId, playerInventory, getBlockInventory(context), getBlockPropertyDelegate(context), context);

        WPlainPanel root = new WPlainPanel();
        root.setSize(158, 140);

        setRootPanel(root);
        WItemSlot inputSlot = WItemSlot.of(blockInventory, 0);
        WItemSlot outputSlot = WItemSlot.outputOf(blockInventory, 1);
        WLabel title = new WLabel("Blast Chiller", WLabel.DEFAULT_TEXT_COLOR);

        WBar progressBar = new WBar(new Identifier("lalegamod:textures/progress_bg.png"), new Identifier("lalegamod:textures/progress_bar.png"), BlastChillerBlockEntity.CHILL_TIME, BlastChillerBlockEntity.UNIT_CHILL_TIME, WBar.Direction.RIGHT);

        ProgressBarDescriptor descriptor = new ProgressBarDescriptor(getBufferEntity().getPropertyDelegate());
        progressBar.createPeers(descriptor);

        root.add(title, 10, 2);
        root.add(inputSlot, 36, 32);
        root.add(outputSlot, 108, 32);
        root.add(progressBar, 70, 32);
        root.add(this.createPlayerInventoryPanel(), 0, 70);
        root.validate(this);
    }

    private class ProgressBarDescriptor implements GuiDescription
    {
        private PropertyDelegate delegate;

        public ProgressBarDescriptor(PropertyDelegate delegate)
        {
            this.delegate = delegate;
        }
        
        @Override
        public GuiDescription setPropertyDelegate(PropertyDelegate delegate) 
        {
            this.delegate = delegate;
            return this;
        }

        @Override
        public PropertyDelegate getPropertyDelegate() 
        {
            return this.delegate;
        }

        @Override
        public WPanel getRootPanel() 
        {
            return null;
        }

        @Override
        public int getTitleColor() 
        {
            return 0;
        }

        @Override
        public GuiDescription setRootPanel(WPanel panel) 
        {
            return null;
        }

        @Override
        public GuiDescription setTitleColor(int color) 
        {
            return null;
        }

        @Override
        public void addSlotPeer(ValidatedSlot slot) {}

        @Override
        public void addPainters() {}

        @Override
        public boolean isFocused(WWidget widget) 
        {
            return false;
        }

        @Override
        public WWidget getFocus() 
        {
            return null;
        }

        @Override
        public void requestFocus(WWidget widget) {}

        @Override
        public void releaseFocus(WWidget widget) {}
    }
}