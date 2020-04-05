package net.la.lega.mod.api;

import net.la.lega.mod.recipe.abstraction.AProcessingRecipe;
import net.minecraft.item.Item;

public class ProcessableRecipeObject<T extends AProcessingRecipe>
{
    private Item inputType;
    private T recipe;
    private int currentProcessingTime;
    private int unitProcessingTime;
    
    public ProcessableRecipeObject(Item input, T recipe)
    {
        this.inputType = input;
        this.recipe = recipe;
        this.unitProcessingTime = recipe.getProcessingTime();
        currentProcessingTime = 0;
    }
    
    public Item getInputType()
    {
        return inputType;
    }
    
    public T getRecipe()
    {
        return recipe;
    }
    
    public float getProgress()
    {
        return currentProcessingTime / unitProcessingTime;
    }
    
    public void processStep()
    {
        currentProcessingTime++;
    }
    
    public boolean isCompleted()
    {
        return currentProcessingTime >= unitProcessingTime;
    }
}
