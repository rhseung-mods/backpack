package com.rhseung.backpack.init

import com.rhseung.backpack.network.BackpackOpenKeyPayload

object ModPayloadsC2S : IModInit {
    override fun load() {
        BackpackOpenKeyPayload.register();
    }
}