package com.example.firstapp.ui.detail_playlist

import androidx.lifecycle.MutableLiveData
import com.example.firstapp.base.BaseViewModel
import com.example.firstapp.data.models.Playlist
import com.example.firstapp.data.models.PlaylistItems
import com.example.firstapp.data.network.Status
import com.example.firstapp.repository.YoutubeRepository


class DetailPlaylistViewModel(var repository: YoutubeRepository) : BaseViewModel() {
var videoId:String?=null
    var detailPlaylists = MutableLiveData<MutableList<PlaylistItems>>()
    var detail: MutableList<PlaylistItems>? = mutableListOf()
    var playlistId: String? = null
    fun fetchPlaylistVideo(playlistId: String?, nextPageToken: String? = null,videoId: String?=null) {
        this.playlistId = playlistId
        repository.fetchDetailPlaylists(playlistId, nextPageToken,videoId).observeForever {
            when (it.status) {
                Status.SUCCESS -> getAllVideo(it?.data)
                Status.ERROR -> errorMessage.value = it.message.toString()
            }
        }
    }

    private fun getAllVideo(data: Playlist?) {
        data?.items?.let { detail?.addAll(it) }
        if (!data?.nextPageToken.isNullOrEmpty()) fetchPlaylistVideo(playlistId, data?.nextPageToken,videoId)
        else detailPlaylists.value = detail
    }
}