package com.rhseung.backpack.init

import com.rhseung.backpack.ModMain
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.sound.SoundEvent

object ModSounds : IModInit {
    fun register(name: String): SoundEvent {
        val id = ModMain.of(name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    val OPEN_BACKPACK = register("open_backpack");
    val CLOSE_BACKPACK = register("close_backpack");
}