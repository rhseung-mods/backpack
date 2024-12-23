package com.rhseung.backpack

import com.rhseung.backpack.datagen.LanguageProvider
import com.rhseung.backpack.datagen.ModelProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

object ModData : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
		val pack = fabricDataGenerator.createPack();

		pack.addProvider(::ModelProvider);
		pack.addProvider(::LanguageProvider);
	}
}