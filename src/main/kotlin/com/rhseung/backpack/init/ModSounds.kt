package com.rhseung.backpack.init

import com.rhseung.backpack.ModMain
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.sound.SoundEvent

object ModSounds : IModInit {
    fun register(name: String): SoundEvent {
        val id = ModMain.of(name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    fun registerRef(name: String): RegistryEntry.Reference<SoundEvent> {
        val id = ModMain.of(name);
        return Registry.registerReference(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    val OPEN_BACKPACK = registerRef("open_backpack");
    val CLOSE_BACKPACK = registerRef("close_backpack");
    val EQUIP_BACKPACK = registerRef("equip_backpack");
}