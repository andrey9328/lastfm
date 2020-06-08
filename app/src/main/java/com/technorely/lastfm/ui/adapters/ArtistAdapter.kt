package com.technorely.lastfm.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.technorely.lastfm.database.entity.ArtistEntity
import com.technorely.lastfm.databinding.ItemArtisBinding

class ArtistAdapter: RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>() {

    private val artistList = ArrayList<ArtistEntity>()

    var itemCLick: ((ArtistEntity) -> Unit)? = null

    inner class ArtistViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding: ItemArtisBinding? = DataBindingUtil.bind(view)
        init {
            binding?.cardView?.setOnClickListener {
                itemCLick?.invoke(artistList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val item = ItemArtisBinding.inflate(inflater, parent, false)
        return ArtistViewHolder(item.root)
    }

    override fun getItemCount(): Int = artistList.size

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val item = artistList[position]
        holder.binding?.artist = item
        holder.binding?.nowListeners?.text = String.format("(%s) listeners", item.playCount)
    }

    fun setData(items: List<ArtistEntity>?) {
        if (items == null) return
        artistList.clear()
        artistList.addAll(items)
        notifyDataSetChanged()
    }
}