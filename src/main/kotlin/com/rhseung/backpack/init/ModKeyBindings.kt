package com.rhseung.backpack.init

import com.rhseung.backpack.backpack.BackpackItem
import com.rhseung.backpack.backpack.network.payload.BackpackOpenKeyC2SPayload
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.entity.EquipmentSlot
import org.lwjgl.glfw.GLFW

object ModKeyBindings : IModInit {
    val backpackOpenKey: KeyBinding = KeyBindingHelper.registerKeyBinding(KeyBinding(
        BackpackItem.KEY_OPEN,
        InputUtil.Type.KEYSYM,
        GLFW.GLFW_KEY_B,
        BackpackItem.KEY_CATEGORY
    ));

    override fun load() {
        ClientTickEvents.END_CLIENT_TICK.register { client ->
            if (backpackOpenKey.wasPressed() && client.player != null) {
                val stack = client.player!!.getEquippedStack(EquipmentSlot.CHEST);
                if (stack.item is BackpackItem) {
                    BackpackItem.playOpenSound(client.player!!);
                    ClientPlayNetworking.send(BackpackOpenKeyC2SPayload());
                }
            }
        };
    }
}