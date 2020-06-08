package com.technorely.lastfm.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.technorely.lastfm.R
import com.technorely.lastfm.data.coutries.CountryItem
import com.technorely.lastfm.databinding.ItemCountrySelectBinding

class CountryAdapter(
    private val context: Context,
    val array: Array<CountryItem>
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val vh: CountryViewHolder
        val view: View

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_country_select, parent, false)
            vh = CountryViewHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as CountryViewHolder
        }
        vh.bind(array[position])
        return view
    }

    override fun getItem(position: Int): Any? = null

    override fun getItemId(position: Int) = 0L

    override fun getCount(): Int = array.size

    inner class CountryViewHolder(itemView: View) {

        private val binding: ItemCountrySelectBinding? = DataBindingUtil.bind(itemView)

        fun bind(item: CountryItem) {
            binding?.item = item
        }
    }
}