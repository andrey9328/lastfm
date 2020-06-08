package com.technorely.lastfm.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.technorely.lastfm.R
import com.technorely.lastfm.database.entity.SongEntity
import com.technorely.lastfm.databinding.ItemImageArtisFullBinding
import com.technorely.lastfm.databinding.ItemSongBinding

class SongAdapter(private val artistImage: String): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val songList = ArrayList<SongEntity>()

    inner class SongViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding: ItemSongBinding? = DataBindingUtil.bind(view)
    }

    inner class FullImageViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding: ItemImageArtisFullBinding? = DataBindingUtil.bind(view)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == ARTIST_IMAGE_TYPE) return ARTIST_IMAGE_TYPE else SONG_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            ARTIST_IMAGE_TYPE -> FullImageViewHolder(getView(R.layout.item_image_artis_full, parent))
            else -> SongViewHolder(getView(R.layout.item_song, parent))
        }
    }

    private fun getView(layout: Int, parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(layout, parent, false)
    }

    override fun getItemCount() = songList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == ARTIST_IMAGE_TYPE) {
            (holder as FullImageViewHolder).binding?.url = artistImage
            return
        }
        val item = songList[position]
        val songHolder = (holder as SongViewHolder).binding
        songHolder?.song = item
        songHolder?.listeners?.text = String.format("(%s) listeners", item.playCount)
    }

    fun setData(items: List<SongEntity>?) {
        if (items == null) return
        songList.clear()
        songList.add(SongEntity("", "", "", "", "", ""))
        songList.addAll(items)
        notifyDataSetChanged()
    }

    companion object {
        const val ARTIST_IMAGE_TYPE = 0
        const val SONG_TYPE = 1
    }
}