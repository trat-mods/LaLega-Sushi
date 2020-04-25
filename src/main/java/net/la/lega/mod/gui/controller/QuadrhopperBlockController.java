package net.la.lega.mod.gui.controller;

import io.github.cottonmc.cotton.gui.CottonCraftingController;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.la.lega.mod.entity.QuadrhopperBlockEntity;
import net.minecraft.container.BlockContext;
import net.minecraft.entity.player.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

public class QuadrhopperBlockController extends CottonCraftingController
{
    QuadrhopperBlockEntity bufferEntity;
    
    public QuadrhopperBlockController(int syncId, PlayerInventory playerInventory, BlockContext context)
    {
        super(null, syncId, playerInventory, getBlockInventory(context), null);
        initializeBufferEntity(context);
        WPlainPanel root = new WPlainPanel();
        root.setSize(168, 80);
        setRootPanel(root);
        WLabel title = new WLabel("Quadrhopper", WLabel.DEFAULT_TEXT_COLOR);
        WLabel invTitle = new WLabel("Inventory", WLabel.DEFAULT_TEXT_COLOR);
        List<WItemSlot> slots = new ArrayList<>();
        for(int i = 0; i < bufferEntity.getItems().size(); i++)
        {
            slots.add(WItemSlot.of(blockInventory, i));
        }
        for(int i = 0; i < slots.size(); i++)
        {
            root.add(slots.get(i), 32 + (i * 30), 16);
        }
        root.add(invTitle, 4, 36);
        root.add(title, 4, 2);
        root.add(this.createPlayerInventoryPanel(), 4, 48);
        root.validate(this);
    }
    
    private void initializeBufferEntity(BlockContext context)
    {
        QuadrhopperBlockEntity[] lambdaBypass = new QuadrhopperBlockEntity[1];
        
        context.run((world, blockPosition) ->
        {
            QuadrhopperBlockEntity temporaryEntity = (QuadrhopperBlockEntity) world.getBlockEntity(blockPosition);
            lambdaBypass[0] = temporaryEntity;
        });
        bufferEntity = lambdaBypass[0];
    }
}