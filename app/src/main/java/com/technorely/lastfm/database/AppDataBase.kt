package com.technorely.lastfm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.technorely.lastfm.database.dao.ArtistDAO
import com.technorely.lastfm.database.dao.SongDAO
import com.technorely.lastfm.database.entity.ArtistEntity
import com.technorely.lastfm.database.entity.SongEntity
import org.koin.dsl.module

val dataBaseModule = module {
    single { provideDatabaseInstance(get()) }
    factory { get<AppDataBase>().artistDao() }
    factory { get<AppDataBase>().songDao() }
}

@Database(entities = [ArtistEntity::class, SongEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {
    abstract fun artistDao(): ArtistDAO

    abstract fun songDao(): SongDAO
}

fun provideDatabaseInstance(context: Context): AppDataBase? {
    return Room.databaseBuilder(context, AppDataBase::class.java, "lastfm.db").build()
}