package com.rhseung.backpack.backpack.network

import com.rhseung.backpack.ModMain
import com.rhseung.backpack.backpack.BackpackItem
import com.rhseung.backpack.backpack.screen.BackpackScreenHandler
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
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
            val screenHandler = ctx.player().currentScreenHandler;
            val stack = screenHandler.getSlot(payload.slot).stack;

            if (stack.item is BackpackItem)
                BackpackItem.setSelectedStackIndex(stack, payload.selectedItemIndex);
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