package com.rhseung.backpack.init

import com.rhseung.backpack.network.BackpackOpenPayload

object ModPayloadsC2S : IModInit {
    override fun load() {
        BackpackOpenPayload.register();
    }
}