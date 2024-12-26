package com.rhseung.backpack

import com.rhseung.backpack.init.ModBlockEntityTypes
import com.rhseung.backpack.init.ModBlocks
import com.rhseung.backpack.init.ModComponents
import com.rhseung.backpack.init.ModItems
import com.rhseung.backpack.init.ModPayloadsC2S
import com.rhseung.backpack.init.ModRecipeSerializers
import com.rhseung.backpack.init.ModScreenHandlerTypes
import com.rhseung.backpack.init.ModSounds
import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object ModMain : ModInitializer {
	const val MOD_ID = "backpack";
    val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID);

	fun of(path: String): Identifier = Identifier.of(MOD_ID, path);

	override fun onInitialize() {
		ModBlockEntityTypes.load();
		ModBlocks.load();
		ModComponents.load();
		ModItems.load();
		ModPayloadsC2S.load();
		ModRecipeSerializers.load();
		ModScreenHandlerTypes.load();
		ModSounds.load();
	}
}