package com.technorely.lastfm.data.coutries

import com.technorely.lastfm.R

class CountriesData {

    companion object {
        fun getCountries(): Array<CountryItem> {
            return arrayOf(
                CountryItem(
                    "UA", R.drawable.ic_flag_of_ukraine, "ukraine"),
                CountryItem("FR", R.drawable.ic_france, "france"),
                CountryItem("ES", R.drawable.ic_spain, "spain")
            )
        }
    }
}