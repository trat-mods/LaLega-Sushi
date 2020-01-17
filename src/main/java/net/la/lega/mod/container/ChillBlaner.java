// package net.la.lega.mod.container;

// import net.fabricmc.api.EnvType;
// import net.fabricmc.api.Environment;
// import net.la.lega.mod.entity.ChillBlasterBlockEntity;
// import net.la.lega.mod.recipe.ChillBlastingRecipe;
// import net.minecraft.container.Container;
// import net.minecraft.container.ContainerType;
// import net.minecraft.container.PropertyDelegate;
// import net.minecraft.container.Slot;
// import net.minecraft.entity.player.PlayerEntity;
// import net.minecraft.entity.player.PlayerInventory;
// import net.minecraft.inventory.BasicInventory;
// import net.minecraft.inventory.Inventory;
// import net.minecraft.item.ItemStack;
// import net.minecraft.recipe.RecipeType;
// import net.minecraft.world.World;

// public class ChillBlasterContainer extends Container
//  {
//     private final Inventory inventory;
//     private final PropertyDelegate propertyDelegate;
//     protected final World world;
//     private final RecipeType<ChillBlastingRecipe> recipeType;

//     protected ChillBlasterContainer(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate)
//     {    
//         ContainerType<?> containerType = ChillBlastingRecipe.Type;
//         RecipeType<ChillBlastingRecipe> recipeType = ChillBlastingRecipe.Type.INSTANCE;
//         super(containerType, syncId);
//         this.recipeType = recipeType;
//         this.propertyDelegate = propertyDelegate;
//         this.inventory = inventory;
//         this.world = playerInventory.player.world;
//         checkContainerSize(inventory, 2);
//         checkContainerDataCount(propertyDelegate, 2);
//         this.addSlot(new Slot(inventory, 0, 56, 17));
//         this.addSlot(new Slot(inventory, 1, 116, 35));

//         int k;
//         for(k = 0; k < 3; ++k) {
//            for(int j = 0; j < 9; ++j) {
//               this.addSlot(new Slot(playerInventory, j + k * 9 + 9, 8 + j * 18, 84 + k * 18));
//            }
//         }
//         for(k = 0; k < 9; ++k) {
//            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
//         }
  
//         this.addProperties(propertyDelegate);
//     }

//     @Override
//     public void clearCraftingSlots() 
//     {
//         this.inventory.clear();
//     }

//     @Override
//     public int getCraftingResultSlotIndex() { return 1; }

//     @Override
//     public int getCraftingWidth() { return 1; }

//     @Override
//     public int getCraftingHeight() { return 1; }

//     @Environment(EnvType.CLIENT)
//     public int getCraftingSlotCount() { return 2; }

//     @Override
//     public boolean canUse(PlayerEntity player) 
//     {
//         return this.inventory.canPlayerUseInv(player);
//     }
    
//     protected boolean isChillable(ItemStack itemStack) 
//     {
//         return this.world.getRecipeManager().getFirstMatch(this.recipeType, new BasicInventory(new ItemStack[]{itemStack}), this.world).isPresent();
//     }

//     @Environment(EnvType.CLIENT)
//     public boolean isChilling() 
//     {
//        return this.propertyDelegate.get(ChillBlasterBlockEntity.CHILL_TIME) > 0;
//     }

// }