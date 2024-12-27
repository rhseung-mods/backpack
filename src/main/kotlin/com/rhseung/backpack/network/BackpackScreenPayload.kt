package com.rhseung.backpack.network

import com.rhseung.backpack.ModMain
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.minecraft.item.ItemStack
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.packet.CustomPayload

@Environment(EnvType.CLIENT)
class BackpackScreenPayload(val stack: ItemStack) : CustomPayload {
    companion object : ClientPlayNetworking.PlayPayloadHandler<BackpackScreenPayload> {
        val PACKET_ID = CustomPayload.Id<BackpackScreenPayload>(ModMain.of("backpack_screen"));
        val PACKET_CODEC: PacketCodec<RegistryByteBuf, BackpackScreenPayload> = PacketCodec.of(
            { value, buf -> ItemStack.PACKET_CODEC.encode(buf, value.stack) },
            { buf -> BackpackScreenPayload(ItemStack.PACKET_CODEC.decode(buf)) }
        );

        override fun receive(payload: BackpackScreenPayload, ctx: ClientPlayNetworking.Context) {
            // TODO: backpack screen
        }

        fun register() {
            PayloadTypeRegistry.playS2C().register(PACKET_ID, PACKET_CODEC);
            ClientPlayNetworking.registerGlobalReceiver(PACKET_ID, BackpackScreenPayload::receive);
        }
    }

    override fun getId(): CustomPayload.Id<BackpackScreenPayload> {
        return PACKET_ID;
    }
}