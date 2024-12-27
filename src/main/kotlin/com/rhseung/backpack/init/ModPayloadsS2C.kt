package com.rhseung.backpack.init

import com.rhseung.backpack.network.BackpackScreenPayload

object ModPayloadsS2C : IModInit {
    override fun load() {
        BackpackScreenPayload.register();
    }
}