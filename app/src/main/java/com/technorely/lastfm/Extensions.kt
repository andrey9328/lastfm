package com.technorely.lastfm

import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import com.google.gson.GsonBuilder
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

fun Spinner.setSpinnerListener(action: (position: Int) -> Unit) {

    var userSelect = false

    val listener = object : AdapterView.OnItemSelectedListener, View.OnTouchListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if (userSelect.not()) return
            userSelect = false
            action.invoke(position)
        }

        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            userSelect = true
            return false
        }

    }
    this.setOnTouchListener(listener)
    this.onItemSelectedListener = listener
}

fun createParser(type: Type?, typeAdapter: Any?): GsonConverterFactory {
    val gsonBuilder = GsonBuilder()
    gsonBuilder.registerTypeAdapter(type, typeAdapter)
    val gson = gsonBuilder.create()
    return GsonConverterFactory.create(gson)
}