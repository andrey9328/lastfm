package com.technorely.lastfm.data.shared

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import org.koin.dsl.module

val sharedModule = module {
    single { SharedSettings(get()) }
    single { provideSharedPreference(get()) }
}

class SharedSettings (private val shared:  SharedPreferences) {

    fun writeThemeType(type: EThemeType) {
        shared.edit().putInt(THEME_TYPE, type.ordinal).apply()
    }

    fun isOfflineMode(): Boolean {
        return shared.getBoolean(IS_OFFLINE_MODE, false)
    }

    fun writeOfflineMode(isEnabled: Boolean) {
        shared.edit().putBoolean(IS_OFFLINE_MODE, isEnabled).apply()
    }

    fun getThemeType(): EThemeType {
        return EThemeType.values()[shared.getInt(THEME_TYPE, 0)]
    }

    companion object {
        const val THEME_TYPE: String = "THEME_TYPE"
        const val IS_OFFLINE_MODE: String = "IS_OFFLINE_MODE"
    }
}

fun provideSharedPreference(context: Context): SharedPreferences {
    return PreferenceManager.getDefaultSharedPreferences(context)
}