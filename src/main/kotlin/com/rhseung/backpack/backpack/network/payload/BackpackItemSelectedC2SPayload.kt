package com.rhseung.backpack.backpack.network.payload

import com.rhseung.backpack.ModMain
import com.rhseung.backpack.backpack.BackpackItem
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.client.MinecraftClient
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.packet.CustomPayload

class BackpackItemSelectedC2SPayload(val slot: Int, val selectedItemIndex: Int) : CustomPayload {
    companion object : ServerPlayNetworking.PlayPayloadHandler<BackpackItemSelectedC2SPayload> {
        val PACKET_ID = CustomPayload.Id<BackpackItemSelectedC2SPayload>(ModMain.id("backpack_item_selected"));
        val PACKET_CODEC = PacketCodec.of<RegistryByteBuf, BackpackItemSelectedC2SPayload>(
            { value, buf -> buf.writeVarInt(value.slot).writeVarInt(value.selectedItemIndex) },
            { buf -> BackpackItemSelectedC2SPayload(buf.readVarInt(), buf.readVarInt()) }
        );

        override fun receive(payload: BackpackItemSelectedC2SPayload, ctx: ServerPlayNetworking.Context) {
            ctx.server().execute {
                val slot = payload.slot;
                val selectedItemIndex = payload.selectedItemIndex;

                val clientPlayer = MinecraftClient.getInstance().player;
                val serverPlayer = ctx.player();

                val clientScreenHandler = clientPlayer?.currentScreenHandler;
                val serverScreenHandler = serverPlayer.currentScreenHandler;

                if (clientScreenHandler != null && slot in clientScreenHandler.slots.indices) {
                    val stack = clientScreenHandler.getSlot(slot).stack;
                    if (stack.item is BackpackItem) {
                        BackpackItem.setSelectedStackIndex(stack, selectedItemIndex);
                    }
                }

                if (slot in serverScreenHandler.slots.indices) {
                    val stack = serverScreenHandler.getSlot(slot).stack;
                    if (stack.item is BackpackItem) {
                        serverPlayer.updateLastActionTime();
                        serverScreenHandler.disableSyncing();
                        BackpackItem.setSelectedStackIndex(stack, selectedItemIndex);
                        serverScreenHandler.enableSyncing();
                    }
                }
            }
        }

        fun register() {
            PayloadTypeRegistry.playC2S().register(PACKET_ID, PACKET_CODEC);
            ServerPlayNetworking.registerGlobalReceiver(PACKET_ID, BackpackItemSelectedC2SPayload::receive);
        }
    }

    override fun getId(): CustomPayload.Id<BackpackItemSelectedC2SPayload> {
        return PACKET_ID;
    }
}