package net.la.lega.mod.block;

import java.util.Optional;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.la.lega.mod.recipe.ChillBlastingRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


public class LauncherBlock extends Block 
{
   private double launchForce = 1F;

   protected int maxStackable = 4;
   protected double stackMultiplierStride = 0.3F;
   protected double powerMultiplier = 1F;

   public LauncherBlock() {
      super(FabricBlockSettings.of(Material.METAL).breakByHand((true)).sounds(BlockSoundGroup.METAL)
            .strength(0.8F, 0.5F).build());
   }

   @Override
   public void onLandedUpon(World world, BlockPos pos, Entity entity, float distance) {
      entity.handleFallDamage(distance, 0.0F);
   }

   public void launchPlayer(World world, BlockPos pos, LivingEntity entity)
   {
      double force = launchForce * powerMultiplier;
      BlockPos currentPos = pos.down();
      int currentIndex = 1;

      double stackMultiplier = 1F;
      while (currentIndex < maxStackable && world.getBlockState(currentPos).getBlock().getClass().equals(this.getClass())) {
         stackMultiplier += stackMultiplierStride;
         currentPos = currentPos.down();
         currentIndex++;
      }

      force *= stackMultiplier;
      System.out.println("Entity: "+entity+"Stepped on " + (currentIndex) + " " + this.getClass() + ", F: " + force + ", SM: " + stackMultiplier);
      entity.setVelocity(0F, force, 0F);
      return;
   }

//    @Override
//    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
//          BlockHitResult result) 
//    {
//       // Something that gives the player items should always go through the server.
//       // If you need to notify the client in some way, check in the server and then send a packet to the client.
//       if (!world.isClient()) {
//           // For the sake of simplicity we draw the items off of the player's hands and create an inventory from that.
//           // Usually you use an inventory of yours instead.
//           BasicInventory inventory = new BasicInventory(player.getMainHandStack());

//           // Or use .getAllMatches if you want all of the matches
//           Optional<ChillBlastingRecipe> match = world.getRecipeManager().getFirstMatch(ChillBlastingRecipe.Type.INSTANCE, inventory, world);

//           if (match.isPresent()) {
//               // Give the player the item and remove from what he has. Make sure to copy the ItemStack to not ruin it!
//               player.inventory.offerOrDrop(world, match.get().getOutput().copy());
//               player.getMainHandStack().decrement(1);
//           } else {
//               // If it doesn't match we tell the player
//               player.sendMessage(new LiteralText("No match!"));
//           }
//       }

//       return ActionResult.SUCCESS;
//   }

}