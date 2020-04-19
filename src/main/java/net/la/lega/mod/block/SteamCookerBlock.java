package net.la.lega.mod.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.la.lega.mod.block.abstraction.AHorizontalFacingInventoryBlock;
import net.la.lega.mod.entity.SteamCookerBlockEntity;
import net.la.lega.mod.initializer.LSounds;
import net.la.lega.mod.loader.LLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class SteamCookerBlock extends AHorizontalFacingInventoryBlock
{
    public static final Identifier ID = new Identifier(LLoader.MOD_ID, "steam_cooker");
    public static final Identifier PRESSURE_SOUND = new Identifier(LLoader.MOD_ID, "steam_cooker_pressure");
    
    public static final BooleanProperty ON;
    public static final IntProperty WATER_FILL_LEVEL;
    
    static
    {
        ON = BooleanProperty.of("on");
        WATER_FILL_LEVEL = IntProperty.of("water_fill_level", 0, 4);
    }
    
    public SteamCookerBlock()
    {
        super(FabricBlockSettings.of(Material.WOOD).breakByHand((true)).sounds(BlockSoundGroup.WOOD).strength(1F, 1F).nonOpaque().build());
        this.setDefaultState(this.stateManager.getDefaultState().with(ON, false).with(WATER_FILL_LEVEL, 0));
    }
    
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos)
    {
        return Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 15.0D, 15.0D);
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
        return ((SteamCookerBlockEntity) world.getBlockEntity(pos)).getComparatorOutput();
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
    
    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
    {
        if(world.getBlockState(pos).get(ON))
        {
            if(random.nextDouble() < 0.55D)
            {
                double x = (double) pos.getX() + 0.3D + (random.nextDouble() * 0.375D);
                double y = (double) pos.getY() + 0.65D;
                double z = (double) pos.getZ() + 0.3D + (random.nextDouble() * 0.4375);
                double vy = random.nextDouble() * 0.1D;
                world.addParticle(ParticleTypes.CLOUD, x, y, z, 0.0D, vy, 0.0D);
            }
            if(random.nextDouble() < 0.125D)
            {
                world.playSound(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, LSounds.STEAM_COOKER_PRESSURE_SOUNDEVENT, SoundCategory.BLOCKS, 0.25F, 1F, false);
            }
        }
    }
}
