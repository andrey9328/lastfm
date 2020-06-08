package com.technorely.lastfm.ui.fragments.songs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technorely.lastfm.database.entity.SongEntity
import com.technorely.lastfm.network.Resource
import com.technorely.lastfm.repositories.SongRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.dsl.module

val songViewModelModule = module {
    factory { (artistName: String) -> SongsViewModel(get(), artistName) }
}

class SongsViewModel(repo: SongRepository, artistName: String): ViewModel() {

    private val songList = MutableLiveData<Resource<List<SongEntity>>>()
    val songListLiveData: LiveData<Resource<List<SongEntity>>> = songList

    init {
        viewModelScope.launch(Dispatchers.Default) {
            songList.postValue(Resource.loading(null))
            songList.postValue(repo.getSongsArtist(artistName))
        }
    }
}