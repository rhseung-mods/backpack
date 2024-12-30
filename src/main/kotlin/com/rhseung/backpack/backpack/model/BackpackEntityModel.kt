package com.rhseung.backpack.backpack.model

import net.minecraft.client.model.Dilation
import net.minecraft.client.model.ModelData
import net.minecraft.client.model.ModelPart
import net.minecraft.client.model.ModelPartBuilder
import net.minecraft.client.model.ModelTransform
import net.minecraft.client.model.TexturedModelData
import net.minecraft.client.render.entity.model.EntityModel
import net.minecraft.client.render.entity.state.PlayerEntityRenderState
import kotlin.math.PI

class BackpackEntityModel(root: ModelPart) : EntityModel<PlayerEntityRenderState>(root.getChild("base")) {
    companion object {
        val INSTANCE = BackpackEntityModel(getTexturedModelData().createModel());

        fun getTexturedModelData(): TexturedModelData {
            val pi = PI.toFloat();
            val modelData = ModelData();
            val modelPartData = modelData.root;

            val base = modelPartData
                .addChild(
                    "base",
                    ModelPartBuilder.create(),
                    ModelTransform.pivot(0f, 24f, 0f)
                );

            base.addChild(
                "cube_r1",
                ModelPartBuilder.create()
                    .uv(50, 0)
                    .mirrored()
                    .cuboid(-5f, -5f, -4f, 5f, 10f, 2f, Dilation(0f))
                    .mirrored(false),
                ModelTransform.of(-1f, -5f, -1f, -pi, -1.3963f, pi)
            );

            base.addChild(
                "cube_r2",
                ModelPartBuilder.create()
                    .uv(50, 0)
                    .mirrored()
                    .cuboid(-7f, -5f, 0f, 5f, 10f, 2f, Dilation(0f))
                    .mirrored(false),
                ModelTransform.of(-1f, -5f, 1f, 0f, -1.3963f, 0f)
            );

            base.addChild(
                "cube_r3",
                ModelPartBuilder.create()
                    .uv(38, 0)
                    .cuboid(0f, -5f, 3f, 5f, 5f, 1f, Dilation(0f))
                    .uv(38, 0)
                    .cuboid(0f, -5f, -2f, 5f, 5f, 1f, Dilation(0f)),
                ModelTransform.of(1f, -11f, -2.5f, -pi / 2, -pi / 4, pi / 2)
            );

            base.addChild(
                "cube_r4",
                ModelPartBuilder.create()
                    .uv(0, 0)
                    .cuboid(-1f, -4f, -5f, 4f, 4f, 1f, Dilation(0f))
                    .uv(0, 0)
                    .mirrored()
                    .cuboid(-1f, -4f, 4f, 4f, 4f, 1f, Dilation(0f))
                    .mirrored(false)
                    .uv(54, 12)
                    .cuboid(3f, -9f, -2f, 1f, 3f, 4f, Dilation(0f))
                    .uv(0, 14)
                    .cuboid(3f, -5f, -3f, 2f, 5f, 6f, Dilation(0f))
                    .uv(25, 6)
                    .mirrored()
                    .cuboid(-1f, -10f, -4f, 4f, 10f, 8f, Dilation(0.01f))
                    .mirrored(false),
                ModelTransform.of(0f, 0f, 0f, 0f, -pi / 2, 0f)
            );

            base.addChild(
                "cube_r5",
                ModelPartBuilder.create()
                    .uv(1, 0)
                    .cuboid(-3.75f, -0.25f, -4f, 4f, 4f, 10f, Dilation(0f)),
                ModelTransform.of(1f, -11f, 3.5f, -pi / 2, -pi / 4, pi / 2)
            );

            return TexturedModelData.of(modelData, 64, 64);
        }
    }
}