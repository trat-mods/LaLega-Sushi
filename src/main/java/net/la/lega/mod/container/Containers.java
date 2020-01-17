package net.la.lega.mod.container;

import net.la.lega.mod.recipe.ChillBlastingRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;


public class Containers
{
    public static ChillBlasterContainer createChillBlasterContainer(int syncId, Identifier identifier, PlayerEntity player, PacketByteBuf buf) 
    {
		Text name = buf.readText();
		return new ChillBlasterContainer(ChillBlastingRecipe.Type.INSTANCE, syncId, player.inventory, name);
	}
}