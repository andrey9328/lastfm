package com.technorely.lastfm.ui.fragments.artis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technorely.lastfm.database.entity.ArtistEntity
import com.technorely.lastfm.network.Resource
import com.technorely.lastfm.repositories.ArtistListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.dsl.module

val artistViewModelModule = module {
    factory { (country: String) -> ArtistListViewModel(get(), country) }
}

class ArtistListViewModel (
    private val repo: ArtistListRepository,
    private val countryName: String) : ViewModel() {

    private val artistList = MutableLiveData<Resource<List<ArtistEntity>>>()
    val artistListLiveData: LiveData<Resource<List<ArtistEntity>>>  = artistList

    init {
        viewModelScope.launch(Dispatchers.Default) {
            artistList.postValue(Resource.loading(null))
            artistList.postValue(repo.getArtistTop(countryName))
        }
    }
}