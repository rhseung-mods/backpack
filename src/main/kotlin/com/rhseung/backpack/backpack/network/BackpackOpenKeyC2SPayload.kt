package com.rhseung.backpack.backpack.network

import com.mojang.serialization.Codec
import com.rhseung.backpack.ModMain
import com.rhseung.backpack.backpack.BackpackItem
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.entity.EquipmentSlot
import net.minecraft.network.codec.PacketCodecs
import net.minecraft.network.packet.CustomPayload

class BackpackOpenKeyC2SPayload : CustomPayload {
    companion object : ServerPlayNetworking.PlayPayloadHandler<BackpackOpenKeyC2SPayload> {
        val PACKET_ID = CustomPayload.Id<BackpackOpenKeyC2SPayload>(ModMain.id("backpack_open"));
        val PACKET_CODEC = PacketCodecs.codec(Codec.unit(BackpackOpenKeyC2SPayload()));

        override fun receive(payload: BackpackOpenKeyC2SPayload, ctx: ServerPlayNetworking.Context) {
            val backpack = ctx.player().getEquippedStack(EquipmentSlot.CHEST);
            val item = backpack.item;

            if (item is BackpackItem) {
                BackpackItem.openScreen(ctx.player(), backpack);
            }
        }

        fun register() {
            PayloadTypeRegistry.playC2S().register(PACKET_ID, PACKET_CODEC);
            ServerPlayNetworking.registerGlobalReceiver(PACKET_ID, BackpackOpenKeyC2SPayload::receive);
        }
    }

    override fun getId(): CustomPayload.Id<BackpackOpenKeyC2SPayload> {
        return PACKET_ID;
    }
}