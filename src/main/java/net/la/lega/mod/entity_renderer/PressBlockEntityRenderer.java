package net.la.lega.mod.entity_renderer;

import net.la.lega.mod.block.PressBlock;
import net.la.lega.mod.entity.PressBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Random;

public class PressBlockEntityRenderer extends BlockEntityRenderer<PressBlockEntity>
{
    protected final BlockRenderManager blockRenderManager = MinecraftClient.getInstance().getBlockRenderManager();
    
    public PressBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher)
    {
        super(dispatcher);
    }
    
    @Override
    public void render(PressBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay)
    {
        BlockState entityState = blockEntity.getCachedState();
        matrices.push();
        float extension = blockEntity.getExtensionAmount(tickDelta);
        BakedModel model = null;
        BlockState blockState = blockEntity.getCachedState();
        model = blockRenderManager.getModel(blockState.with(PressBlock.MODELS, 1));
        matrices.translate(0, -extension, 0);
        RenderLayer renderLayer = RenderLayers.getEntityBlockLayer(entityState);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(renderLayer);
        this.blockRenderManager.getModelRenderer().render(blockEntity.getWorld(), model, entityState, blockEntity.getPos().up(), matrices, vertexConsumer, true, new Random(), 4, overlay);
        matrices.pop();
    }
}
