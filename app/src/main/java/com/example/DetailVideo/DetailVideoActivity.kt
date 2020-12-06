package com.example.DetailVideo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.WindowManager
import com.example.firstapp.R
import com.example.firstapp.base.BaseActivity
import com.example.firstapp.data.models.PlaylistItems
import com.example.firstapp.ui.detail_playlist.DetailPlaylistActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.activity_detailvideo.*

class DetailVideoActivity :
    BaseActivity<DetVideoViewModel>(R.layout.activity_detailvideo, DetVideoViewModel::class) {

    private var title: String? = null
    private var description: String? = null
    private val bottomSheetFragment = MyBottomSheetFragment()
    var video: String? = playlist?.contentDetails?.videoId
    val videoPath: String? = "https://www.youtube.com/watch?v=$video"


    private fun subscribeDetailPlaylist() {
        //  bottomSheetFragment.show(supportFragmentManager, "BottomSheetDialog")
        bottomSheetFragment.showNow(supportFragmentManager, "show")

    }


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


    override fun setupFetchRequests() {

    }

    private fun fetchDetailPlaylist() {
        this.viewModel.fetchPlaylistVideo((DetailPlaylistActivity.playlist?.id))
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun setupViews() {
        player_view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                videoPath.let { video?.let { it1 -> youTubePlayer.loadVideo(it1, 0f) } }
                getIntent2()
            }
        })}

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation ==
            ORIENTATION_LANDSCAPE
        ) {
            btnDownload.visibility = VISIBLE
            descriptionVideo?.visibility = VISIBLE
            relativeLayout2?.visibility = GONE
            titleVideo?.visibility = VISIBLE
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
            btnDownload.visibility = VISIBLE
            player_view?.layoutParams {
                btnDownload.visibility = VISIBLE
                width = ViewGroup.LayoutParams.MATCH_PARENT
                height = 1100
            } }

        if (newConfig.orientation == ORIENTATION_PORTRAIT) {
            btnDownload.visibility = VISIBLE
            relativeLayout2?.visibility = VISIBLE
            descriptionVideo?.visibility = VISIBLE
            titleVideo?.visibility = VISIBLE

            player_view?.layoutParams {
                width = ViewGroup.LayoutParams.MATCH_PARENT
                height = 600

            } } }

    fun View.layoutParams(run: ViewGroup.LayoutParams.() -> Unit) {
        val params = layoutParams
        run(params)
        layoutParams = params
    }
    override fun setupLiveData() {
        subscribeDetailPlaylist()
        fetchDetailPlaylist()

    }

    private fun getIntent2() {
        val intent: Intent = intent
        title = intent.getStringExtra("description")
        description = intent.getStringExtra("title")

        titleVideo?.text = title
        descriptionVideo?.text = description
    }

    override fun forSetOnClickListener() {

        relativeId.setOnClickListener(View.OnClickListener {
            bottomSheetFragment.showNow(supportFragmentManager, "show")
        })

    }}
    /* <activity
     android:name=".SingleTaskActivity"
     android:label="singleTask launchMode"
     android:launchMode="singleTask"> нужен*/





