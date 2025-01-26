package com.rhseung.backpack.init

import com.rhseung.backpack.backpack.network.payload.BackpackItemSelectedC2SPayload
import com.rhseung.backpack.backpack.network.payload.BackpackOpenKeyC2SPayload

object ModPayloadsC2S : IModInit {
    override fun load() {
        BackpackOpenKeyC2SPayload.register();
        BackpackItemSelectedC2SPayload.register();
    }
}