package com.rhseung.backpack.network

import com.mojang.serialization.Codec
import com.rhseung.backpack.ModMain
import com.rhseung.backpack.backpack.BackpackItem
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.entity.EquipmentSlot
import net.minecraft.network.codec.PacketCodecs
import net.minecraft.network.packet.CustomPayload
import net.minecraft.util.Hand

class BackpackOpenPayload : CustomPayload {
    companion object : ServerPlayNetworking.PlayPayloadHandler<BackpackOpenPayload> {
        val ID = CustomPayload.Id<BackpackOpenPayload>(ModMain.of("backpack_open"));
        val CODEC = PacketCodecs.codec(Codec.unit(BackpackOpenPayload()));

        override fun receive(payload: BackpackOpenPayload, ctx: ServerPlayNetworking.Context) {
            val backpack = ctx.player().getEquippedStack(EquipmentSlot.CHEST);
            val item = backpack.item;

            if (item is BackpackItem) {
                BackpackItem.openScreen(ctx.player().serverWorld, ctx.player(), backpack, -1);
            }
        }

        fun register() {
            PayloadTypeRegistry.playC2S().register(ID, CODEC);
            ServerPlayNetworking.registerGlobalReceiver(ID, BackpackOpenPayload::receive);
        }
    }

    override fun getId(): CustomPayload.Id<BackpackOpenPayload> {
        return ID;
    }
}