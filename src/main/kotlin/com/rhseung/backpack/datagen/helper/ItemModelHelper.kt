package com.rhseung.backpack.datagen.helper

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.minecraft.data.client.Model
import net.minecraft.data.client.TextureKey
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier
import java.util.*
import java.util.function.BiConsumer
import java.util.function.Supplier

object ItemModelHelper {
    data class ItemModelBuilder(private var suffix: String? = null) {
        private lateinit var item: Item;
        private lateinit var parent: Identifier;
        private var textures: Map<TextureKey, Identifier> = mapOf();
        private var overrides: List<Pair<Map<Identifier, Int>, ItemModelBuilder>> = listOf();

        fun item(item: Item): ItemModelBuilder {
            this.item = item;
            return this;
        }

        fun parent(parent: String): ItemModelBuilder {
            this.parent = Identifier.ofVanilla(parent).withPrefixedPath("item/");
            return this;
        }

        fun textures(textures: Map<TextureKey, Identifier>): ItemModelBuilder {
            this.textures = textures.mapValues { it.value.withPrefixedPath("item/") };
            return this;
        }

        fun overrides(overrides: List<Pair<Map<Identifier, Int>, ItemModelBuilder>>): ItemModelBuilder {
            require(overrides.all { it.second.suffix != null }) { "Overrides must have a suffix" }

            this.overrides = overrides;
            return this;
        }

        fun getId(): Identifier = Registries.ITEM.getId(item).withPrefixedPath("item/").withSuffixedPath(suffix ?: "");

        fun upload(modelCollector: BiConsumer<Identifier, Supplier<JsonElement>>) {
            val model = Model(
                Optional.of(parent),
                Optional.empty(),
                *textures.keys.toTypedArray()
            );

            val id = getId();

            modelCollector.accept(id) {
                val jsonObject = model.createJson(id, textures);

                val overridesArray = JsonArray();
                for ((predicates, overrideModel) in overrides) {
                    val predicateObject = JsonObject();
                    for ((id, value) in predicates)
                        predicateObject.addProperty(id.toString(), value);

                    val overrideObject = JsonObject();
                    overrideObject.add("predicate", predicateObject);
                    overrideObject.addProperty("model", overrideModel.getId().toString());

                    overridesArray.add(overrideObject);
                }

                if (overrides.isNotEmpty())
                    jsonObject.add("overrides", overridesArray);

                return@accept jsonObject;
            }

            if (overrides.isNotEmpty())
                overrides.forEach { it.second.upload(modelCollector) }
        }
    }

    fun itemModel(suffix: String? = null): ItemModelBuilder {
        return ItemModelBuilder(suffix);
    }
}