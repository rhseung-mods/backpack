package com.rhseung.backpack.init

import com.rhseung.backpack.backpack.network.BackpackItemSelectedC2SPayload
import com.rhseung.backpack.backpack.network.BackpackOpenKeyC2SPayload

object ModPayloadsC2S : IModInit {
    override fun load() {
        BackpackOpenKeyC2SPayload.register();
        BackpackItemSelectedC2SPayload.register();
    }
}