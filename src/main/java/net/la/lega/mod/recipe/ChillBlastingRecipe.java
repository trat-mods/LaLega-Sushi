package net.la.lega.mod.recipe;

import net.la.lega.mod.recipe.serializer.ChillBlastingRecipeSerializer;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ChillBlastingRecipe implements Recipe<Inventory> {

    private final Ingredient input;
    private final ItemStack outputStack;
    private final int chillTime;
    private final Identifier id;

    public ChillBlastingRecipe(Ingredient input, ItemStack outputStack, int chillTime, Identifier id)
    {
        this.input = input;
        this.outputStack = outputStack;
        this.chillTime = chillTime;
        this.id = id;
    }

    public Ingredient getInput() { return input; }
    public int getChillTime() { return chillTime; }

    @Override
    public boolean matches(Inventory inv, World world) {
        if (inv.getInvSize() < 1) return false;
        return input.test(inv.getInvStack(0));
    }

    @Override
    public ItemStack craft(Inventory inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getOutput() {
        return outputStack;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ChillBlastingRecipeSerializer.INSTANCE;
    }
    
    public static class Type implements RecipeType<ChillBlastingRecipe>
    {
       private Type() {}
       public static final Type INSTANCE = new Type();
       public static final String ID = "chill_blasting_recipe";
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }
}