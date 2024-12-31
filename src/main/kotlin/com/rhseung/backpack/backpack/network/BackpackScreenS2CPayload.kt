package com.rhseung.backpack.backpack.network

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
class BackpackScreenS2CPayload(val stack: ItemStack) : CustomPayload {
    companion object : ClientPlayNetworking.PlayPayloadHandler<BackpackScreenS2CPayload> {
        val PACKET_ID = CustomPayload.Id<BackpackScreenS2CPayload>(ModMain.id("backpack_screen"));
        val PACKET_CODEC: PacketCodec<RegistryByteBuf, BackpackScreenS2CPayload> = PacketCodec.of(
            { value, buf -> ItemStack.PACKET_CODEC.encode(buf, value.stack) },
            { buf -> BackpackScreenS2CPayload(ItemStack.PACKET_CODEC.decode(buf)) }
        );

        override fun receive(payload: BackpackScreenS2CPayload, ctx: ClientPlayNetworking.Context) {
            // TODO: backpack screen
        }

        fun register() {
            PayloadTypeRegistry.playS2C().register(PACKET_ID, PACKET_CODEC);
            ClientPlayNetworking.registerGlobalReceiver(PACKET_ID, BackpackScreenS2CPayload::receive);
        }
    }

    override fun getId(): CustomPayload.Id<BackpackScreenS2CPayload> {
        return PACKET_ID;
    }
}