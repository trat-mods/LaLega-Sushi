package net.la.lega.mod.recipe;

import it.unimi.dsi.fastutil.ints.IntList;
import net.la.lega.mod.recipe.abstraction.AbstractProcessingRecipe;
import net.la.lega.mod.recipe.serializer.SushiCraftingRecipeSerializer;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class SushiCraftingRecipe extends AbstractProcessingRecipe
{
    public static final String recipeID = "sushi_crafting";
    
    private final DefaultedList<Ingredient> input;
    
    public SushiCraftingRecipe(DefaultedList<Ingredient> input, ItemStack outputStack, int processingTime, Identifier id)
    {
        super(outputStack, processingTime, id);
        this.input = input;
    }
    
    @Override
    public DefaultedList<Ingredient> getPreviewInputs()
    {
        return input;
    }
    
    @Override
    public boolean fits(int arg0, int arg1)
    {
        return false;
    }
    
    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return SushiCraftingRecipeSerializer.INSTANCE;
    }
    
    @Override
    public boolean matches(Inventory inv, World world)
    {
        RecipeFinder recipeFinder = new RecipeFinder();
        int i = 0;
        
        for(int j = 0; j < inv.getInvSize(); j++)
        {
            ItemStack itemStack = inv.getInvStack(j);
            if(!itemStack.isEmpty())
            {
                ++i;
                recipeFinder.method_20478(itemStack, 1);
            }
        }
        return i == this.input.size() && recipeFinder.findRecipe(this, (IntList) null);
    }
    
    @Override
    public RecipeType<?> getType()
    {
        return Type.INSTANCE;
    }
    
    public static class Type implements RecipeType<SushiCraftingRecipe>
    {
        private Type()
        {
        }
        
        public static final Type INSTANCE = new Type();
        public static final String ID = "sushi_crafting_recipe";
    }
}