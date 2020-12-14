package com.example.firstapp.ui.DetailVideo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.WindowManager
import com.example.firstapp.R
import com.example.firstapp.base.BaseActivity
import com.example.firstapp.data.models.PlaylistItems
import com.example.firstapp.ui.detail_playlist.DetailPlaylistActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.activity_detailvideo.*

class DetailVideoActivity :
    BaseActivity<DetVideoViewModel>(R.layout.activity_detailvideo, DetVideoViewModel::class) {
    private var title: String? = null
    private var description: String? = null
    private val bottomSheetFragment = YoutubeBottomSheetFragment()
    var video: String? = playlist?.contentDetails?.videoId
    val videoPath: String? = "https://www.youtube.com/watch?v=$video"

    private fun subscribeDetailPlaylist() {}

    companion object {
        var playlist: PlaylistItems? = null
        fun instanceActivity(
            activity: Activity?,
            playlist: PlaylistItems
        ) {
            val intent = Intent(activity, DetailVideoActivity::class.java)
            this.playlist = playlist

            intent.putExtra("description", playlist.snippet?.description.toString())
            intent.putExtra("title", playlist.snippet?.title)
            intent.putExtra("countVideo", playlist.contentDetails?.itemCount)
            intent.putExtra("image", playlist.snippet?.thumbnails?.medium?.url)
            activity?.startActivity(intent)
        }
    }

    override fun setupFetchRequests() {}

    private fun fetchDetailPlaylist() {
        this.viewModel.fetchPlaylistVideo((DetailPlaylistActivity.playlist?.id))
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun setupViews() {
        player_view.isFullScreen()
        player_view.nextFocusLeftId

        player_view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                videoPath.let { video?.let { it1 -> youTubePlayer.loadVideo(it1, 0f) } }
                getIntent2()
            }
        })
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation ==
            ORIENTATION_LANDSCAPE
        ) {
            orientationLandscape()
        }

        if (newConfig.orientation == ORIENTATION_PORTRAIT) {
            orientationPortrait()
        }
    }

    fun View.layoutParams(run: ViewGroup.LayoutParams.() -> Unit) {
        val params = layoutParams
        run(params)
        layoutParams = params
    }

    override fun setupLiveData() {
        subscribeDetailPlaylist()
        fetchDetailPlaylist()

    }

    private fun orientationLandscape() {
        visibleForLandscape()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        player_view?.layoutParams {
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = 1100
        }
    }

    private fun orientationPortrait() {
        visibleForPortrait()
        player_view?.layoutParams {
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = 600
        }
    }

    private fun visibleForLandscape() {
        btnDownload.visibility = VISIBLE
        descriptionVideo?.visibility = VISIBLE
        relativeLayout2?.visibility = GONE
        titleVideo?.visibility = VISIBLE
        btnDownload.visibility = VISIBLE
        btnDownload.visibility = VISIBLE
    }

    private fun visibleForPortrait() {
        btnDownload.visibility = VISIBLE
        relativeLayout2?.visibility = VISIBLE
        descriptionVideo?.visibility = VISIBLE
        titleVideo?.visibility = VISIBLE
    }

    private fun getIntent2() {
        val intent: Intent = intent
        title = intent.getStringExtra("description")
        description = intent.getStringExtra("title")
        titleVideo?.text = title
        descriptionVideo?.text = description
    }

    override fun forSetOnClickListener() {
        toolbar?.setOnClickListener {
            bottomSheetFragment.showNow(supportFragmentManager, "show")
        }
        relativeId?.setOnClickListener(View.OnClickListener {
            bottomSheetFragment.showNow(supportFragmentManager, "show")
        })
    }
}