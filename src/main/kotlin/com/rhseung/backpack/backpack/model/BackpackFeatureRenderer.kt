package com.rhseung.backpack.backpack.model

import com.rhseung.backpack.ModMain
import com.rhseung.backpack.backpack.BackpackItem
import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.feature.FeatureRenderer
import net.minecraft.client.render.entity.feature.FeatureRendererContext
import net.minecraft.client.render.entity.model.PlayerEntityModel
import net.minecraft.client.render.entity.state.PlayerEntityRenderState
import net.minecraft.client.render.item.ItemRenderer
import net.minecraft.client.util.math.MatrixStack

class BackpackFeatureRenderer(
    context: FeatureRendererContext<PlayerEntityRenderState, PlayerEntityModel>
) : FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel>(context) {

    val backpackModel = BackpackEntityModel.INSTANCE;

    override fun render(
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int,
        state: PlayerEntityRenderState,
        limbAngle: Float,
        limbDistance: Float
    ) {
        val backpackStack = state.equippedChestStack;
        if (backpackStack.item !is BackpackItem)
            return;

        matrices.push();

        val backpackItem = backpackStack.item as BackpackItem;
        val playerModel = this.contextModel;
        val bodyModelPart = playerModel.body;
        val color = BackpackItem.getColor(backpackStack);
        bodyModelPart.rotate(matrices);
        matrices.translate(0f, -0.9f, 0.2f);

        // vertexConsumer를 render하면 해당 vertexConsumer는 BufferBuilder의 building이 false가 되면서 더 이상 렌더링이 되지 않고
        // 시도할 경우 오류가 발생하므로 아래 코드와 같이 (vertexConsumer 선언 - 렌더링)을 반복하는 순서로 코딩해야함

        val backpackVertexConsumer = ItemRenderer.getItemGlintConsumer(
            vertexConsumers,
            backpackModel.getLayer(ModMain.of("textures/entity/backpack.png")),
            false,
            backpackStack.hasGlint()
        );
        backpackModel.render(matrices, backpackVertexConsumer, light, OverlayTexture.DEFAULT_UV, color);

        val backpackCoverVertexConsumer = ItemRenderer.getItemGlintConsumer(
            vertexConsumers,
            backpackModel.getLayer(backpackItem.type.backpackCoverTextureId.withPath { "textures/entity/$it.png" }),
            false,
            backpackStack.hasGlint()
        );
        backpackModel.render(matrices, backpackCoverVertexConsumer, light, OverlayTexture.DEFAULT_UV);

        matrices.pop();
    }
}