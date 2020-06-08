package com.technorely.lastfm.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.technorely.lastfm.database.entity.SongEntity

@Dao
abstract class SongDAO {
    @Insert
    abstract suspend fun insertAll(artist: SongEntity)

    @Query("DELETE FROM songs WHERE artistName=:artistName")
    abstract suspend fun deleteAllArtists(artistName: String?)

    @Query("SELECT * FROM songs WHERE artistName=:artistName ORDER BY name")
    abstract suspend fun findAll(artistName: String?): List<SongEntity>
}