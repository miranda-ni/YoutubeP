package com.example.firstapp.RoomBd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.firstapp.data.models.Playlist

@Dao
interface PlaylistDao {

  @Query("SELECT * FROM playlist")
  suspend fun getPlaylist(): Playlist

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertPlaylist(items: Playlist)
}