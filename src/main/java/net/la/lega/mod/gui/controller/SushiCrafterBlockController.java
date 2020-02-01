package net.la.lega.mod.gui.controller;

import io.github.cottonmc.cotton.gui.widget.WBar;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import net.la.lega.mod.entity.SushiCrafterBlockEntity;
import net.la.lega.mod.gui.controller.abstraction.AbstractBlockController;
import net.la.lega.mod.recipe.BlastChillingRecipe;
import net.minecraft.container.BlockContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.Identifier;

public class SushiCrafterBlockController extends AbstractBlockController 
{
    protected int currentProcessTime = -1;
    protected int unitProcessTime = -1;

    public SushiCrafterBlockController(int syncId, PlayerInventory playerInventory, BlockContext context) 
    {
        super(BlastChillingRecipe.Type.INSTANCE, syncId, playerInventory, getBlockInventory(context), getBlockPropertyDelegate(context), context);

        WPlainPanel root = new WPlainPanel();
        root.setSize(160, 160);
        setRootPanel(root);

        WItemSlot ing2Slot = WItemSlot.of(blockInventory, SushiCrafterBlockEntity.ING2_SLOT);
        WItemSlot ingSlot = WItemSlot.of(blockInventory, SushiCrafterBlockEntity.ING_SLOT);
        WItemSlot riceSlot = WItemSlot.of(blockInventory, SushiCrafterBlockEntity.RICE_SLOT);
        WItemSlot fishSlot = WItemSlot.of(blockInventory, SushiCrafterBlockEntity.FISH_SLOT);
        WItemSlot outputSlot = WItemSlot.outputOf(blockInventory, SushiCrafterBlockEntity.OUTPUT_SLOT);

        WLabel title = new WLabel("Sushi Crafter", WLabel.DEFAULT_TEXT_COLOR);

        WBar progressBar = new WBar(new Identifier("lalegamod:textures/ui/progress_bg.png"), new Identifier("lalegamod:textures/ui/progress_bar.png"), 0, 1, WBar.Direction.RIGHT);

        WSprite heartIcon = new WSprite(new Identifier("minecraft:textures/item/heart_of_the_sea.png"));
        WSprite fishIcon = new WSprite(new Identifier("lalegamod:textures/ui/sashimi_transparent.png"));
        fishIcon.setSize(16, 16);

        WSprite riceIcon = new WSprite(new Identifier("lalegamod:textures/ui/rice_transparent.png"));
        riceIcon.setSize(16, 16);

        root.add(fishIcon, 31, 21);
        root.add(riceIcon, 31, 63);
        root.add(heartIcon, 31, 41);

        root.add(title, 10, 2);

        root.add(ing2Slot, 52, 43);
        root.add(ingSlot, 10, 43);
        root.add(riceSlot, 32, 64);
        root.add(fishSlot, 32, 22);
        root.add(outputSlot, 130, 44);

        root.add(progressBar, 85, 43, 26, 17);

        root.add(this.createPlayerInventoryPanel(), 0, 92);
        root.validate(this);
    }
}