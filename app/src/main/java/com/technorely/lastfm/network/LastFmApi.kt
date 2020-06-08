package com.technorely.lastfm.network

import com.technorely.lastfm.database.entity.ArtistEntity
import com.technorely.lastfm.database.entity.SongEntity
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap

interface LastFmApi {
    @Headers("Content-Type:application/json")
    @GET("?method=geo.gettopartists")
    suspend fun getTopArtist(@QueryMap params: HashMap<String, String>): List<ArtistEntity>

    @Headers("Content-Type:application/json")
    @GET("?method=artist.gettoptracks")
    suspend fun getTopSong(@QueryMap params: HashMap<String, String>): List<SongEntity>
}