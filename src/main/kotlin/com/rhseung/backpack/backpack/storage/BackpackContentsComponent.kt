package com.rhseung.backpack.backpack.storage

import com.mojang.serialization.Codec
import net.minecraft.component.type.ContainerComponent
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec

class BackpackContentsComponent(val containerComponent: ContainerComponent, val selectedStackIndex: Int) {
    constructor(containerComponent: ContainerComponent) : this(containerComponent, -1)

    override fun equals(other: Any?): Boolean {
        return other is BackpackContentsComponent && this.containerComponent == other.containerComponent && this.selectedStackIndex == other.selectedStackIndex;
    }

    companion object {
        val DEFAULT = BackpackContentsComponent(ContainerComponent.DEFAULT);
        val CODEC: Codec<BackpackContentsComponent> =
            ContainerComponent.CODEC.xmap(::BackpackContentsComponent, BackpackContentsComponent::containerComponent);
        val PACKET_CODEC: PacketCodec<RegistryByteBuf, BackpackContentsComponent> =
            ContainerComponent.PACKET_CODEC.xmap(::BackpackContentsComponent, BackpackContentsComponent::containerComponent);
    }
}