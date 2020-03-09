package net.la.lega.mod.recipe.jsonformat;

import com.google.gson.JsonObject;

public class InjectiveProcessingRecipeJsonFormat 
{
    private JsonObject input;
    private String output;
    private int processingTime;
    private int outputAmount;

    public JsonObject getInput() { return input; }
    public String getOutput() { return output; }
    public int getOutputAmount() { return outputAmount; }
    public int getProcessingTime() { return processingTime; }

    public void setOutputAmount(int outputAmount) { this.outputAmount = outputAmount; }
    public void setProcessingTime(int processingTime) { this.processingTime = processingTime; }
}