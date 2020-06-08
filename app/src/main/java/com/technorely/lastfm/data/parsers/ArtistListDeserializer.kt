package com.technorely.lastfm.data.parsers

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.technorely.lastfm.database.entity.ArtistEntity
import java.lang.reflect.Type

class ArtistListDeserializer: BaseDeserializer(), JsonDeserializer<List<ArtistEntity>> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): List<ArtistEntity> {
        val result = ArrayList<ArtistEntity>()

        try {
            val topArtists = json?.asJsonObject?.getAsJsonObject("topartists")
            val countryName = topArtists?.getAsJsonObject("@attr")
                ?.getAsJsonPrimitive("country")
                ?.asString
                ?.toLowerCase() ?: ""

            for (element in topArtists?.getAsJsonArray("artist")!!) {
                val item = element.asJsonObject
                val name = item.getAsJsonPrimitive("name").asString
                val count = item.getAsJsonPrimitive("listeners").asString
                val mbid = item.getAsJsonPrimitive("mbid").asString
                val artist = ArtistEntity(name, count, mbid, getImageUrl(item), countryName)
                result.add(artist)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }
}