package net.la.lega.mod.screen;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.la.lega.mod.container.ChillBlasterContainer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.AbstractContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class ChillBlasterScreen extends AbstractContainerScreen<ChillBlasterContainer> {

	public ChillBlasterScreen(ChillBlasterContainer container, PlayerInventory playerInventory, Text name) 
	{
		super(container, playerInventory, name);
		System.out.println("SCREEN CREATED");
    }

	public static AbstractContainerScreen<ChillBlasterContainer> createScreen(ChillBlasterContainer container) 
	{
		return new ChillBlasterScreen(container, MinecraftClient.getInstance().player.inventory, container.getContainerName());
	}

	@Override
	public void render(int mouseX, int mouseY, float delta) 
	{
		this.renderBackground();
		// this.drawBackground(delta, mouseX, mouseX);
		super.render(mouseX, mouseY, delta);
		this.drawMouseoverTooltip(mouseX, mouseY);
	}

	@Override
	protected void drawForeground(int mouseX, int mouseY)
	{
		this.font.draw(this.title.asFormattedString(), 8.0F, 7.0F, 4210752);
		this.font.draw(this.playerInventory.getDisplayName().asFormattedString(), 8.0F, (float) (this.containerHeight - 114 + 2), 4210752);
	}

	@Override
	protected void drawBackground(float delta, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		//this.minecraft.getTextureManager().bindTexture(TEXTURE);
		int i = (this.width - this.containerWidth) / 2;
		int j = (this.height - this.containerHeight) / 2;
		this.blit(i, j, 0, 0, this.containerWidth, 7 * 18 + 17);
		this.blit(i, j + 7 * 18 /*+ 17*/, 0, 126, this.containerWidth, 114); // was 114, 96
	}

}