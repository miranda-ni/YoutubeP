package com.example.firstapp.data.network

import com.example.firstapp.data.models.Playlist
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeApi {

    @GET("youtube/v3/playlists")
    suspend fun fetchPlaylists(
        @Query("part") part: String,
        @Query("key") key: String,
        @Query("channelId") channelId: String
    ): Playlist


    @GET("youtube/v3/playlistItems")
    suspend fun fetchDetailPlaylist(
        @Query("part") part: String,
        @Query("key") key: String,
        @Query("playlistId") playlistId: String?,
        @Query("pageToken") pageToken: String?,
        @Query("videoId")videoId:String?
    ): Playlist

    @GET("youtube/v3/playlistItems")
    suspend fun GetVideo(
        @Query("part") part: String,
        @Query("key") key: String,
        @Query("playlistId") playlistId: String?,
        @Query("pageToken") pageToken: String?,
        @Query("videoId")videoId:String?
    ): Playlist




}
