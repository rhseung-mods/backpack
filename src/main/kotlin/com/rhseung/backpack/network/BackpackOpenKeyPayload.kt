package com.rhseung.backpack.network

import com.mojang.serialization.Codec
import com.rhseung.backpack.ModMain
import com.rhseung.backpack.backpack.BackpackItem
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.entity.EquipmentSlot
import net.minecraft.network.codec.PacketCodecs
import net.minecraft.network.packet.CustomPayload

class BackpackOpenKeyPayload : CustomPayload {
    companion object : ServerPlayNetworking.PlayPayloadHandler<BackpackOpenKeyPayload> {
        val PACKET_ID = CustomPayload.Id<BackpackOpenKeyPayload>(ModMain.of("backpack_open"));
        val PACKET_CODEC = PacketCodecs.codec(Codec.unit(BackpackOpenKeyPayload()));

        override fun receive(payload: BackpackOpenKeyPayload, ctx: ServerPlayNetworking.Context) {
            val backpack = ctx.player().getEquippedStack(EquipmentSlot.CHEST);
            val item = backpack.item;

            if (item is BackpackItem) {
                BackpackItem.openScreen(ctx.player(), backpack);
            }
        }

        fun register() {
            PayloadTypeRegistry.playC2S().register(PACKET_ID, PACKET_CODEC);
            ServerPlayNetworking.registerGlobalReceiver(PACKET_ID, BackpackOpenKeyPayload::receive);
        }
    }

    override fun getId(): CustomPayload.Id<BackpackOpenKeyPayload> {
        return PACKET_ID;
    }
}