package com.technorely.lastfm.repositories

import com.technorely.lastfm.createParser
import com.technorely.lastfm.data.parsers.ArtistListDeserializer
import com.technorely.lastfm.data.parsers.SongDeserializer
import com.technorely.lastfm.data.shared.SharedSettings
import com.technorely.lastfm.database.dao.ArtistDAO
import com.technorely.lastfm.database.entity.ArtistEntity
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

val artistRepositoryModule = module {
    factory { ArtistListRepository(get()) }
}

class ArtistListRepository(private val artistDao: ArtistDAO): KoinComponent {

    private val api: LastFmApi by inject(named("parser")) { parametersOf(getArtistListDeserializer()) }
    private val shared: SharedSettings by inject()

    suspend fun getArtistTop(countryName: String): Resource<List<ArtistEntity>> {
        return object :NetworkBoundResource<List<ArtistEntity>>() {

            override suspend fun saveResultInDB(item: List<ArtistEntity>) {
                artistDao.deleteAllArtists(countryName)
                item.forEach {
                    artistDao.insertAll(it) }
            }

            override suspend fun shouldFetch(data: List<ArtistEntity>?): Boolean {
                return shared.isOfflineMode().not()
            }

            override suspend fun loadFromDb(): List<ArtistEntity> {
                return artistDao.getAllArtists(countryName)
            }

            override suspend fun createCall(): List<ArtistEntity> {
               return api.getTopArtist(getRequestParam(countryName))
            }

        }.execute()
    }

    private fun getRequestParam(country: String): HashMap<String, String> {
        val map = HashMap<String, String>()
        map["country"] = country
        return map
    }

    private fun getArtistListDeserializer(): GsonConverterFactory? {
        return createParser(List::class.java, ArtistListDeserializer())
    }
}