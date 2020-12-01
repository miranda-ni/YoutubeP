package com.example.DetailVideo

import android.app.Activity
import android.content.Intent
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.firstapp.R
import com.example.firstapp.base.BaseActivity
import com.example.firstapp.data.models.PlaylistItems
import com.example.firstapp.ui.detail_playlist.DetailPlaylistActivity
import kotlinx.android.synthetic.main.activity_detailvideo.*

class DetailVideoActivity :
    BaseActivity<DetVideoViewModel>(R.layout.activity_detailvideo, DetVideoViewModel::class) {
    private lateinit var adapter: DetailVideoAdapter
    private fun setupAdapter() {
        adapter = DetailVideoAdapter(this::onItemClick1)
        video_recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        video_recyclerView.adapter = adapter
        video_recyclerView.setOnTouchListener(OnTouchListener { view: View?, motionEvent: MotionEvent? -> true })
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(video_recyclerView)

    }

    private fun subscribeDetailPlaylist() {
        viewModel.detailPlaylists.observeForever {
            adapter.addItems(it)
        }
    }


    companion object {
        var playlist: PlaylistItems? = null
        fun instanceActivity(
            activity: Activity?,
            playlist: PlaylistItems,
            adapterPosition: Int
        ) {
            val intent = Intent(activity, DetailVideoActivity::class.java)
            this.playlist = playlist

            intent.putExtra("description", playlist.snippet?.description?.length)
            intent.putExtra("title", playlist.snippet?.title)
            intent.putExtra("countVideo", playlist.contentDetails?.itemCount)
            intent.putExtra("image", playlist.snippet?.thumbnails?.medium?.url)
            activity?.startActivity(intent)
        }
    }
    private fun onItemClick1(item: PlaylistItems) {
        // DetailVideoActivity.instanceActivity(this, item)
    }

    override fun setupFetchRequests() {
    backBtn.setOnClickListener(View.OnClickListener {
        if (adapter.holder.adapterPosition!=0) {
            video_recyclerView.layoutManager?.scrollToPosition(adapter.holder.adapterPosition - 1)
        }})

        nextBtn.setOnClickListener(View.OnClickListener {

            video_recyclerView.layoutManager?.scrollToPosition(adapter.holder.position+1)
        })
    }

    private fun fetchDetailPlaylist() {
        this.viewModel.fetchPlaylistVideo((DetailPlaylistActivity.playlist?.id))
    }

    override fun setupViews() {
        setupAdapter()
    }

    override fun setupLiveData() {
        subscribeDetailPlaylist()
        fetchDetailPlaylist()
    }
}