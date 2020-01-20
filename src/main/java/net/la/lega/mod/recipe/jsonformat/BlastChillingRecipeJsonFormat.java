package net.la.lega.mod.recipe.jsonformat;

import com.google.gson.JsonObject;

public class BlastChillingRecipeJsonFormat
{
    private JsonObject input;
    private String output;
    private int chillTime;
    private int outputAmount;

    public JsonObject getInput() { return input; }
    public String getOutput() { return output; }
    public int getOutputAmount() { return outputAmount; }
    public int getChillTime() { return chillTime; }

    public void setOutputAmount(int outputAmount) { this.outputAmount = outputAmount; }
    public void setChillTime(int chillTime) { this.chillTime = chillTime; }
}