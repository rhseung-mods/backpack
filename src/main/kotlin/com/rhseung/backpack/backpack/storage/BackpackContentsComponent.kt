package com.rhseung.backpack.backpack.storage

import net.minecraft.component.type.ContainerComponent

class BackpackContentsComponent(val containerComponent: ContainerComponent, val selectedStackIndex: Int) {
    constructor(containerComponent: ContainerComponent) : this(containerComponent, -1)

    companion object {
        val DEFAULT = BackpackContentsComponent(ContainerComponent.DEFAULT);
        val CODEC = ContainerComponent.CODEC
            .xmap(::BackpackContentsComponent, BackpackContentsComponent::containerComponent);
        val PACKET_CODEC = ContainerComponent.PACKET_CODEC
            .xmap(::BackpackContentsComponent, BackpackContentsComponent::containerComponent);
    }
}