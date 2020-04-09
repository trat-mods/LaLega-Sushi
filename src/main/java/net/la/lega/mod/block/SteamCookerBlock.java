package net.la.lega.mod.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.la.lega.mod.block.abstraction.AHorizontalFacingInventoryBlock;
import net.la.lega.mod.entity.SteamCookerBlockEntity;
import net.la.lega.mod.loader.LLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class SteamCookerBlock extends AHorizontalFacingInventoryBlock
{
    public static final Identifier ID = new Identifier(LLoader.MOD_ID, "steam_cooker");
    
    public static final BooleanProperty ON;
    public static final IntProperty WATER_FILL_LEVEL;
    
    static
    {
        ON = BooleanProperty.of("on");
        WATER_FILL_LEVEL = IntProperty.of("water_fill_level", 0, 5);
    }
    
    public SteamCookerBlock()
    {
        super(FabricBlockSettings.of(Material.METAL).breakByHand((true)).sounds(BlockSoundGroup.METAL).strength(1F, 1F).nonOpaque().build());
        this.setDefaultState(this.stateManager.getDefaultState().with(ON, false).with(WATER_FILL_LEVEL, 0));
    }
    
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
    {
        if(world.isClient) return ActionResult.SUCCESS;
        
        Item mainHandItem = player.inventory.getMainHandStack().getItem();
        SteamCookerBlockEntity steamCookerEntity = (SteamCookerBlockEntity) world.getBlockEntity(pos);
        boolean used = false;
        if(mainHandItem.equals(Items.WATER_BUCKET))
        {
            if(!steamCookerEntity.isWaterNew())
            {
                steamCookerEntity.fillWater();
                used = true;
            }
        }
        else if(mainHandItem.equals(Items.SPONGE))
        {
            if(steamCookerEntity.getWaterFillLevel() > 0)
            {
                steamCookerEntity.drainWater();
                player.getMainHandStack().decrement(1);
                if(player.getMainHandStack().getCount() == 0)
                {
                    player.setStackInHand(Hand.MAIN_HAND, new ItemStack(Items.WET_SPONGE));
                }
                else
                {
                    player.inventory.offerOrDrop(world, new ItemStack(Items.WET_SPONGE));
                }
                return ActionResult.SUCCESS;
            }
        }
        if(used)
        {
            player.getMainHandStack().decrement(1);
            if(player.getMainHandStack().getCount() == 0)
            {
                player.setStackInHand(Hand.MAIN_HAND, new ItemStack(Items.BUCKET));
            }
            else
            {
                player.inventory.offerOrDrop(world, new ItemStack(Items.BUCKET));
            }
            return ActionResult.SUCCESS;
        }
        
        BlockEntity be = world.getBlockEntity(pos);
        if(be instanceof SteamCookerBlockEntity)
        {
            ContainerProviderRegistry.INSTANCE.openContainer(ID, player, (packetByteBuf -> packetByteBuf.writeBlockPos(pos)));
        }
        return ActionResult.SUCCESS;
    }
    
    @Override public int getComparatorOutput(BlockState state, World world, BlockPos pos)
    {
        return 5 - world.getBlockState(pos).get(this.WATER_FILL_LEVEL);
    }
    
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
    {
        super.appendProperties(stateManager);
        stateManager.add(ON).add(WATER_FILL_LEVEL);
    }
    
    @Override public BlockEntity createBlockEntity(BlockView view)
    {
        return new SteamCookerBlockEntity();
    }
}
