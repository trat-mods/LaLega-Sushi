package net.la.lega.mod.recipe.abstraction;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public abstract class AbstractInjectiveProcessingRecipe extends AbstractProcessingRecipe
{
    private final Ingredient input;
    
    public AbstractInjectiveProcessingRecipe(Ingredient input, ItemStack outputStack, int processingTime, Identifier id)
    {
        super(outputStack, processingTime, id);
        this.input = input;
    }
    
    public Ingredient getInput()
    {
        return input;
    }
    
    @Override
    public boolean matches(Inventory inv, World world)
    {
        if(inv.getInvSize() < 1) return false;
        return input.test(inv.getInvStack(0));
    }
    
    @Override
    public boolean fits(int width, int height)
    {
        return false;
    }
    
    @Override
    public abstract RecipeSerializer<?> getSerializer();
    
    @Override
    public abstract RecipeType<?> getType();
}