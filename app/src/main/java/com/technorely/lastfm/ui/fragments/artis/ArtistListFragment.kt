package com.technorely.lastfm.ui.fragments.artis

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.technorely.lastfm.R
import com.technorely.lastfm.databinding.FragmentArtistListBinding
import com.technorely.lastfm.network.EStatus
import com.technorely.lastfm.ui.activity.ContentActivity
import com.technorely.lastfm.ui.adapters.ArtistAdapter
import com.technorely.lastfm.ui.dialogs.SettingsDialog
import com.technorely.lastfm.ui.fragments.songs.SongsDetailFragment
import kotlinx.android.synthetic.main.fragment_artist_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ArtistListFragment : Fragment() {

    private var binding: FragmentArtistListBinding? = null

    private val artistViewModel: ArtistListViewModel by viewModel {
        parametersOf(arguments?.getString(COUNTRY_REQUEST))
    }

    private val artistAdapter = ArtistAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_artist_list, container, false)
        binding = DataBindingUtil.bind(view)
        binding?.lifecycleOwner = this
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as ContentActivity).showCountrySelector()
        recycler.adapter = artistAdapter
        artistAdapter.itemCLick = {artist ->
            findNavController().navigate(R.id.action_artistListFragment_to_albumDetailFragment,
                bundleOf(SongsDetailFragment.ARTIST_DATA to artist)
            )
        }
        artistViewModel.artistListLiveData.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            binding?.state = it.status
            if (it.status == EStatus.SUCCESS) {
                if (it.data.isNullOrEmpty())
                    Toast.makeText(requireActivity(), "No cache", Toast.LENGTH_LONG).show()
                artistAdapter.setData(it.data)
                return@Observer
            }

            if (it.status == EStatus.ERROR) {
                Toast.makeText(requireActivity(), it.error, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SettingsDialog.APPLY_SETTING && resultCode == Activity.RESULT_OK) {
            requireActivity().recreate()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_settings, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_dialog) {
            SettingsDialog.showDialog(this, parentFragmentManager)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val COUNTRY_REQUEST = "COUNTRY_NAME"
    }
}