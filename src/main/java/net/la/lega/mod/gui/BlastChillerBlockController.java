package net.la.lega.mod.gui;

import io.github.cottonmc.cotton.gui.CottonCraftingController;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.la.lega.mod.entity.BlastChillerBlockEntity;
import net.la.lega.mod.recipe.BlastChillingRecipe;
import net.minecraft.container.BlockContext;
import net.minecraft.entity.player.PlayerInventory;

public class BlastChillerBlockController extends CottonCraftingController 
{
    BlastChillerBlockEntity bufferEntity;

    public BlastChillerBlockController(int syncId, PlayerInventory playerInventory, BlockContext context) 
    {
        super(BlastChillingRecipe.Type.INSTANCE, syncId, playerInventory, getBlockInventory(context), getBlockPropertyDelegate(context));

        bufferEntity = getBufferEntity(context);

        WPlainPanel root = new WPlainPanel();
        root.setSize(158, 144);
        //WGridPanel root = new WGridPanel();
        setRootPanel(root);
        WItemSlot inputSlot = WItemSlot.of(blockInventory, 0);
        WItemSlot outputSlot = WItemSlot.outputOf(blockInventory, 1);
        WLabel title = new WLabel("Blast Chiller", WLabel.DEFAULT_TEXT_COLOR);
        root.add(title, 10, 2);
        root.add(inputSlot, 36, 32);
        root.add(outputSlot, 108, 32);
        root.add(this.createPlayerInventoryPanel(), 0, 70);
        root.validate(this);
    }

    public BlastChillerBlockEntity getBufferEntity(BlockContext context) 
    {
        BlastChillerBlockEntity[] lambdaBypass = new BlastChillerBlockEntity[1];

        context.run((world, blockPosition) -> {
            BlastChillerBlockEntity temporaryEntity = (BlastChillerBlockEntity) world.getBlockEntity(blockPosition);
            lambdaBypass[0] = temporaryEntity;
        });
        return lambdaBypass[0];
    }
}