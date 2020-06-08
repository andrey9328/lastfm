package com.technorely.lastfm.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.technorely.lastfm.R
import com.technorely.lastfm.data.coutries.CountriesData
import com.technorely.lastfm.setSpinnerListener
import com.technorely.lastfm.ui.adapters.CountryAdapter
import com.technorely.lastfm.ui.dialogs.SettingsDialog
import com.technorely.lastfm.ui.fragments.artis.ArtistListFragment
import kotlinx.android.synthetic.main.activity_content.*
import kotlinx.android.synthetic.main.view_popular_selector.*

class ContentActivity : AppCompatActivity(R.layout.activity_content) {

    private lateinit var navFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(appToolbar)
        initNavigationGraph()
        supportActionBar?.title = null
        initListeners()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return false
    }

    fun hideCountrySelector(title: String?) {
        countrySectorSpinner.visibility = View.GONE
        arrow.visibility = View.GONE
        toolbar_title.text = title
    }

    fun showCountrySelector() {
        countrySectorSpinner.visibility = View.VISIBLE
        arrow.visibility = View.VISIBLE
        toolbar_title.text = getString(R.string.popular_artists)
    }

    private fun initNavigationGraph() {
        navFragment = supportFragmentManager.findFragmentById(R.id.navigationMainFragment) as NavHostFragment
        navController = navFragment.navController
        val appBarConfiguration = AppBarConfiguration.Builder(setOf(R.id.artistListFragment)).build()
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }

    private fun initListeners() {
        val adapter = CountryAdapter(this, CountriesData.getCountries())
        if (countrySectorSpinner.adapter == null)
            countrySectorSpinner.adapter = adapter

        countrySectorSpinner.setSpinnerListener {
            val item = (countrySectorSpinner.adapter as CountryAdapter).array[it]
            navController.setGraph(
                R.navigation.main_graph,
                bundleOf(ArtistListFragment.COUNTRY_REQUEST to item.requestName)
            )
        }
    }
}