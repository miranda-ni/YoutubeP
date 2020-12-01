package com.example.RoomBd

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.firstapp.converter.PlaylistItemsConverter
import com.example.firstapp.data.models.Playlist

@Database(entities = [Playlist::class], version = 2)
@TypeConverters(PlaylistItemsConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playlistDao(): PlaylistDao

}
