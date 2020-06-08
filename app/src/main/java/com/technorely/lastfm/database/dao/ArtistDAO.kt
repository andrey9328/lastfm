package com.technorely.lastfm.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.technorely.lastfm.database.entity.ArtistEntity

@Dao
abstract class ArtistDAO {
    @Insert
    abstract suspend fun insertAll(artist: ArtistEntity)

    @Update
    abstract suspend fun update(artist: ArtistEntity)

    @Query("SELECT * FROM artists WHERE country=:countryName ORDER BY name")
    abstract suspend fun getAllArtists(countryName: String): List<ArtistEntity>

    @Query("SELECT * FROM artists WHERE id=:id")
    abstract suspend fun getArtistById(id: Long): ArtistEntity?

    @Query("SELECT * FROM artists WHERE name=:name")
    abstract suspend fun getArtistByName(name: String): ArtistEntity?

    @Query("DELETE FROM artists WHERE country=:countryName")
    abstract suspend fun deleteAllArtists(countryName: String)
}