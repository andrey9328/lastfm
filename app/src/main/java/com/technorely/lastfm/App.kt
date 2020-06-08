package com.technorely.lastfm

import android.app.Application
import com.technorely.lastfm.data.shared.sharedModule
import com.technorely.lastfm.database.dataBaseModule
import com.technorely.lastfm.network.networkModule
import com.technorely.lastfm.repositories.artistRepositoryModule
import com.technorely.lastfm.repositories.songRepositoryModule
import com.technorely.lastfm.ui.fragments.songs.songViewModelModule
import com.technorely.lastfm.ui.fragments.artis.artistViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(sharedModule, networkModule, dataBaseModule))
            modules(listOf(artistViewModelModule, songViewModelModule))
            modules(listOf(artistRepositoryModule, songRepositoryModule))
        }
    }
}