package com.technorely.lastfm.data.parsers

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.technorely.lastfm.database.entity.SongEntity
import java.lang.reflect.Type

class SongDeserializer:BaseDeserializer(), JsonDeserializer<List<SongEntity>> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): List<SongEntity> {
        val result = ArrayList<SongEntity>()

        try {
            val topTracks = json?.asJsonObject?.getAsJsonObject("toptracks")
            for (element in topTracks?.getAsJsonArray("track")!!) {
                val item = element.asJsonObject
                val name = item.getAsJsonPrimitive("name").asString
                val count = item.getAsJsonPrimitive("playcount").asString
                val listeners = item.getAsJsonPrimitive("playcount").asString
                val rank = item.getAsJsonObject("@attr").getAsJsonPrimitive("rank").asString
                val song = SongEntity("", name, count, listeners, getImageUrl(item), rank)
                result.add(song)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }
}