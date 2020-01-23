package net.la.lega.mod.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.la.lega.mod.entity.ThreadCutterBlockEntity;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.world.BlockView;

public class ThreadCutterBlock extends BlockWithEntity 
{
    public static final Identifier ID = new Identifier("lalegamod", "thread_cutter_block");

    public ThreadCutterBlock() 
    {
        super(FabricBlockSettings.of(Material.METAL).breakByHand((true)).sounds(BlockSoundGroup.METAL).strength(0.8F, 0.8F).build());
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) 
    {
        return new ThreadCutterBlockEntity();
    }
       
}