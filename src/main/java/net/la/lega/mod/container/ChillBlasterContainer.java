package net.la.lega.mod.container;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.la.lega.mod.entity.ChillBlasterBlockEntity;
import net.la.lega.mod.recipe.ChillBlastingRecipe;
import net.minecraft.container.ArrayPropertyDelegate;
import net.minecraft.container.Container;
import net.minecraft.container.PropertyDelegate;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class ChillBlasterContainer extends Container
{
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    protected final World world;
    private final RecipeType<ChillBlastingRecipe> recipeType;
    private Text name;
    
    public ChillBlasterContainer(RecipeType<ChillBlastingRecipe> recipeType, int syncId, PlayerInventory playerInventory, Text name) 
    {
        this(syncId, playerInventory, new BasicInventory(2), new ArrayPropertyDelegate(2), name);
    }

    public ChillBlasterContainer(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate, Text name)
    {
        super(null, syncId);
        this.name = name;
		this.inventory = inventory;
        this.inventory.onInvOpen(playerInventory.player);
        this.propertyDelegate = propertyDelegate;
        this.recipeType = ChillBlastingRecipe.Type.INSTANCE;
        this.world = playerInventory.player.world;

        checkContainerSize(inventory, 2);
        checkContainerDataCount(propertyDelegate, 2);

        this.addSlot(new Slot(inventory, 0, 56, 17));
        this.addSlot(new Slot(inventory, 1, 116, 35));

        int k;
        for(k = 0; k < 3; ++k) {
           for(int j = 0; j < 9; ++j) {
              this.addSlot(new Slot(playerInventory, j + k * 9 + 9, 8 + j * 18, 84 + k * 18));
           }
        }
        for(k = 0; k < 9; ++k) {
           this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
  
        this.addProperties(propertyDelegate);
	}

    public void clearCraftingSlots() 
    {
        this.inventory.clear();
    }

    public Text getContainerName() { return name; }

    public int getCraftingResultSlotIndex() { return 1; }

    public int getCraftingWidth() { return 1; }

    public int getCraftingHeight() { return 1; }

    @Environment(EnvType.CLIENT)
    public int getCraftingSlotCount() { return 2; }

    @Override
    public boolean canUse(PlayerEntity player) 
    {
        return this.inventory.canPlayerUseInv(player);
    }
    
    protected boolean isChillable(ItemStack itemStack) 
    {
        return this.world.getRecipeManager().getFirstMatch(this.recipeType, new BasicInventory(new ItemStack[]{itemStack}), this.world).isPresent();
    }

    @Environment(EnvType.CLIENT)
    public boolean isChilling() 
    {
       return this.propertyDelegate.get(ChillBlasterBlockEntity.CHILL_TIME) > 0;
    }
    
}