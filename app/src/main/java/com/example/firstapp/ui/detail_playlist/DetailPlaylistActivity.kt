package com.example.firstapp.ui.detail_playlist

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstapp.ui.DetailVideo.DetailVideoActivity
import com.example.firstapp.R
import com.example.firstapp.base.BaseActivity
import com.example.firstapp.data.models.PlaylistItems
import com.example.firstapp.showToast
import com.example.firstapp.ui.detail_playlist.adapter.DetailPlaylistAdapter
import kotlinx.android.synthetic.main.activity_detail_playlist.*

class DetailPlaylistActivity : BaseActivity<DetailPlaylistViewModel>(R.layout.activity_detail_playlist

    ,DetailPlaylistViewModel::class){
    private var title:String?=null
    private var description:String?=null
    private var videoCount:String?=null
    private lateinit var adapter: DetailPlaylistAdapter

    private fun setupAdapter() {
        adapter = DetailPlaylistAdapter(this,this::onItemClick)
        recycler_viewDetail.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_viewDetail.adapter = adapter
    }
    private fun onItemClick(item: PlaylistItems) {
        DetailVideoActivity.instanceActivity(this, item)
    adapter.holder
    }

    private fun subscribeDetailPlaylist() {
        viewModel.detailPlaylists.observeForever {
            adapter.addItems(it)
        }
    }

    private fun subscribeErrorMessage() {
        viewModel.errorMessage.observeForever {
            showToast(it)
        }
    }
    companion object {
        var playlist: PlaylistItems? = null
        fun instanceActivity(activity: Activity?, playlist: PlaylistItems) {
            val intent = Intent(activity, DetailPlaylistActivity::class.java)
            this.playlist = playlist

            intent.putExtra("description",playlist.snippet?.description?.length)
            intent.putExtra("title",playlist.snippet?.title)
            intent.putExtra("countVideo",playlist.contentDetails?.itemCount)
            activity?.startActivity(intent)
        } }
    @SuppressLint("SetTextI18n")
    private fun getIntent1(){


        val intent :Intent= intent
        title=intent.getStringExtra("description")
        description=intent.getStringExtra("title")
        videoCount=intent.getStringExtra("countVideo")

        titleFromOneActivity.text=title
        descriptionFromOneActivity.text=description
        video_seriesCount.text= videoCount+" video series"
    }
    override fun setupViews() {
        setupAdapter()

    }

    override fun setupLiveData() {
        subscribeDetailPlaylist()
        viewModel.fetchPlaylistVideo((playlist?.id))
        subscribeErrorMessage()
        getIntent1()
    }

    override fun setupFetchRequests() {

    }

    override fun forSetOnClickListener() {
    }
}