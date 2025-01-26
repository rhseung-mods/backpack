package com.rhseung.backpack.init

import com.rhseung.backpack.backpack.network.payload.BackpackScreenS2CPayload

object ModPayloadsS2C : IModInit {
    override fun load() {
        BackpackScreenS2CPayload.register();
    }
}