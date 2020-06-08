package com.technorely.lastfm.repositories

import com.technorely.lastfm.createParser
import com.technorely.lastfm.data.parsers.ArtistListDeserializer
import com.technorely.lastfm.data.parsers.SongDeserializer
import com.technorely.lastfm.data.shared.SharedSettings
import com.technorely.lastfm.database.dao.ArtistDAO
import com.technorely.lastfm.database.dao.SongDAO
import com.technorely.lastfm.database.entity.SongEntity
import com.technorely.lastfm.network.LastFmApi
import com.technorely.lastfm.network.NetworkBoundResource
import com.technorely.lastfm.network.Resource
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.converter.gson.GsonConverterFactory

val songRepositoryModule = module {
    factory { SongRepository(get(), get()) }
}

class SongRepository(
    private val artistDao: ArtistDAO,
    private val songDao: SongDAO
): KoinComponent {

    private val api: LastFmApi by inject(named("parser")) { parametersOf(getSongListDeserializer()) }
    private val shared: SharedSettings by inject()

    suspend fun getSongsArtist(artistName: String): Resource<List<SongEntity>> {

        return object: NetworkBoundResource<List<SongEntity>>() {
            override suspend fun saveResultInDB(item: List<SongEntity>) {
                songDao.deleteAllArtists(artistName)
                item.forEach {
                    it.artistName = artistName
                    songDao.insertAll(it)
                }
            }

            override suspend fun shouldFetch(data: List<SongEntity>?): Boolean {
                return shared.isOfflineMode()
            }

            override suspend fun loadFromDb(): List<SongEntity> {
                return songDao.findAll(artistName)
            }

            override suspend fun createCall(): List<SongEntity> {
                val artist = artistDao.getArtistByName(artistName)
                return api.getTopSong(getRequestParam(artist?.name))
            }

        }.execute()
    }

    private fun getRequestParam(artistName: String?): HashMap<String, String> {
        val map = HashMap<String, String>()
        map["artist"] = artistName ?: ""
        return map
    }

    private fun getSongListDeserializer(): GsonConverterFactory? {
        return createParser(List::class.java, SongDeserializer())
    }
}