package com.example.firstapp.RoomBd

import android.content.Context
import androidx.room.Room

class DatabaseClient {

    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    fun providePlaylistDao(db: AppDatabase): PlaylistDao? = db.playlistDao()
}