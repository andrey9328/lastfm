package com.technorely.lastfm.ui.fragments.songs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.technorely.lastfm.R
import com.technorely.lastfm.database.entity.ArtistEntity
import com.technorely.lastfm.databinding.FragmentSongDetailBinding
import com.technorely.lastfm.network.EStatus
import com.technorely.lastfm.ui.activity.ContentActivity
import com.technorely.lastfm.ui.adapters.SongAdapter
import kotlinx.android.synthetic.main.fragment_song_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SongsDetailFragment : Fragment() {

    private var binding: FragmentSongDetailBinding? = null

    private val artistData: ArtistEntity?
    get() = (requireArguments().getParcelable(ARTIST_DATA) as ArtistEntity?)

    private val songsViewModel: SongsViewModel by viewModel {
        parametersOf(artistData?.name)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_song_detail, container, false)
        binding = DataBindingUtil.bind(view)
        binding?.lifecycleOwner = this
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val songAdapter = SongAdapter(artistData?.imageUrl ?: "")
        recycler.adapter = songAdapter
        (requireActivity() as ContentActivity).hideCountrySelector(artistData?.name)
        songsViewModel.songListLiveData.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            binding?.state = it.status

            if (it.status == EStatus.SUCCESS) {
                if (it.data.isNullOrEmpty())
                    Toast.makeText(requireActivity(), "No cache", Toast.LENGTH_LONG).show()
                songAdapter.setData(it.data)
                return@Observer
            }

            if (it.status == EStatus.ERROR) {
                Toast.makeText(requireActivity(), it.error, Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        const val ARTIST_DATA = "ARTIST_DATA"
    }
}