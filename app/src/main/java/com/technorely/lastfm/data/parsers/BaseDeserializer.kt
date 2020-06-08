package com.technorely.lastfm.data.parsers

import com.google.gson.JsonObject

abstract class BaseDeserializer {
    fun getImageUrl(element: JsonObject): String {
        val images = element.getAsJsonArray("image")
        val allImages = ArrayList<Pair<String, String>>()
        for (image in images) {
            allImages.add(
                image.asJsonObject.getAsJsonPrimitive("#text").asString to
                        image.asJsonObject.getAsJsonPrimitive("size").asString)
        }

        allImages.filter { it.second == "mega" }
        return if (allImages.size > 0) allImages.first().first else ""
    }
}