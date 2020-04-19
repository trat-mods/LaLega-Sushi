package net.la.lega.mod.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.la.lega.mod.block.abstraction.AHorizontalFacingInventoryBlock;
import net.la.lega.mod.entity.FryerBlockEntity;
import net.la.lega.mod.initializer.LItems;
import net.la.lega.mod.initializer.LSounds;
import net.la.lega.mod.loader.LLoader;
import net.la.lega.mod.model_enum.OilType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class FryerBlock extends AHorizontalFacingInventoryBlock
{
    public static final Identifier ID = new Identifier(LLoader.MOD_ID, "fryer");
    public static final Identifier ON_SOUND = new Identifier(LLoader.MOD_ID, "fryer_on");
    
    public static final EnumProperty<OilType> OIL_TYPE;
    public static final BooleanProperty ON;
    
    static
    {
        OIL_TYPE = EnumProperty.of("oil_type", OilType.class);
        ON = BooleanProperty.of("on");
    }
    
    public FryerBlock()
    {
        super(FabricBlockSettings.of(Material.METAL).breakByHand((true)).sounds(BlockSoundGroup.METAL).strength(1F, 1F).nonOpaque().build());
        this.setDefaultState(this.stateManager.getDefaultState().with(OIL_TYPE, OilType.NONE).with(ON, false));
    }
    
    @Override public BlockEntity createBlockEntity(BlockView view)
    {
        return new FryerBlockEntity();
    }
    
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit)
    {
        if(world.isClient) return ActionResult.SUCCESS;
        
        Item mainHandItem = player.inventory.getMainHandStack().getItem();
        FryerBlockEntity fryerEntity = (FryerBlockEntity) world.getBlockEntity(pos);
        boolean used = false;
        if(mainHandItem.equals(LItems.RICE_OIL_BOTTLE))
        {
            if(!fryerEntity.isOilNew())
            {
                fryerEntity.fillOil(OilType.RICE_OIL);
                used = true;
            }
        }
        else if(mainHandItem.equals(LItems.SUNFLOWER_OIL_BOTTLE))
        {
            if(!fryerEntity.isOilNew())
            {
                fryerEntity.fillOil(OilType.SUNFLOWER_OIL);
                used = true;
            }
        }
        else if(mainHandItem.equals(Items.WATER_BUCKET))
        {
            if(fryerEntity.getOilType() != OilType.NONE)
            {
                fryerEntity.drainOil();
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
        }
        if(used)
        {
            player.getMainHandStack().decrement(1);
            if(player.getMainHandStack().getCount() == 0)
            {
                player.setStackInHand(Hand.MAIN_HAND, new ItemStack(Items.GLASS_BOTTLE));
            }
            else
            {
                player.inventory.offerOrDrop(world, new ItemStack(Items.GLASS_BOTTLE));
            }
            return ActionResult.SUCCESS;
        }
        
        BlockEntity be = world.getBlockEntity(pos);
        if(be instanceof FryerBlockEntity)
        {
            ContainerProviderRegistry.INSTANCE.openContainer(ID, player, (packetByteBuf -> packetByteBuf.writeBlockPos(pos)));
        }
        return ActionResult.SUCCESS;
    }
    
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager)
    {
        super.appendProperties(stateManager);
        stateManager.add(OIL_TYPE).add(ON);
    }
    
    @Override public int getComparatorOutput(BlockState state, World world, BlockPos pos)
    {
        FryerBlockEntity fryerEntity = (FryerBlockEntity) world.getBlockEntity(pos);
        System.out.println(fryerEntity.getComparatorOutput());
        return fryerEntity.getComparatorOutput();
    }
    
    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
    {
        if(world.getBlockState(pos).get(ON))
        {
            if(random.nextDouble() < 0.45D)
            {
                double x = (double) pos.getX() + 0.3D + (random.nextDouble() * 0.375D);
                double y = (double) pos.getY() + 0.95D;
                double z = (double) pos.getZ() + 0.3D + (random.nextDouble() * 0.4375);
                double vy = random.nextDouble() * 0.1D;
                world.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0D, vy, 0.0D);
            }
            if(random.nextDouble() < 0.14D)
            {
                world.playSound(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, LSounds.FRYER_ON_SOUNDEVENT, SoundCategory.BLOCKS, 0.25F, 0.825F, false);
            }
        }
    }
}
