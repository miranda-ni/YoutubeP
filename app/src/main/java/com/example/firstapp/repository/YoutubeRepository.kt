package com.example.firstapp.repository

import androidx.lifecycle.liveData
import com.example.RoomBd.PlaylistDao
import com.example.firstapp.data.network.Resource
import com.example.firstapp.data.network.YoutubeApi
import kotlinx.coroutines.Dispatchers

open class BaseRepository {

    suspend fun <T> baseRequest(dto: T) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = dto))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message =  e.message ?: "Error"))
        }
    }

    fun <T> baseRequestWithDB(dto: T, fetchData: T) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        emit(Resource.fetchFromDB(fetchData))
        try {
            emit(Resource.success(data = dto))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message =  e.message ?: "Error"))
        }
    }
}






class YoutubeRepository(private var api: YoutubeApi,var dao: PlaylistDao): BaseRepository() {

    val channel = "UCw3vK8lNe5SZzL--rMgq-CQ"
    val key = "AIzaSyCO__x6rsVAwStDJukt7yHGVP7mDHY7u38"
    val part = "snippet,contentDetails"



    fun fetchPlaylists() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        emit(Resource.loading(dao.getPlaylist()))

        try {

            //emit(Resource.success(data = api.fetchPlaylists(part, key, channel)))
            val request = api.fetchPlaylists(part, key, channel)
            dao.insertPlaylist(request)
            emit(Resource.success(data = request))
            emit(Resource.loading(dao.getPlaylist()))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message =  e.message ?: "Error"))
        }
    }

    fun fetchDetailPlaylists(playlistId: String?, pageToken: String?) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        // emit(Resource.fetchFromDB(dao.getPlaylist()))
        try {
            emit(Resource.success(data = api.fetchDetailPlaylist(part, key, playlistId, pageToken)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message =  e.message ?: "Error"))
        }
    }
}
