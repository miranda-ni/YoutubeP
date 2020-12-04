package com.example.DetailVideo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.net.Uri
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.example.firstapp.R
import com.example.firstapp.base.BaseActivity
import com.example.firstapp.data.models.PlaylistItems
import com.example.firstapp.ui.detail_playlist.DetailPlaylistActivity
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import kotlinx.android.synthetic.main.activity_detailvideo.*
import kotlinx.android.synthetic.main.custom_controller.*
import retrofit2.http.Url

class DetailVideoActivity :
    BaseActivity<DetVideoViewModel>(R.layout.activity_detailvideo, DetVideoViewModel::class) {
    private lateinit var simpleExoPlayer: SimpleExoPlayer
    private var flag: Boolean = false
    val bottomSheetFragment = MyBottomSheetFragment()


    private fun subscribeDetailPlaylist() {

        btnDownload.setOnClickListener(View.OnClickListener {


            bottomSheetFragment.show(supportFragmentManager, "BottomSheetDialog")
        })

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


            val intent1 = Intent()
            var ur: String? = intent1.getStringExtra("image")

        }


    }


    private fun onItemClick1(item: PlaylistItems) {
        // DetailVideoActivity.instanceActivity(this, item)
    }

    override fun setupFetchRequests() {

    }

    private fun fetchDetailPlaylist() {
        this.viewModel.fetchPlaylistVideo((DetailPlaylistActivity.playlist?.id))
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun setupViews() {


        val link = "https://www.radiantmediaplayer.com/media/bbb-360p.mp4"
        val videoUri: Uri = Uri.parse(link)


        val loadControl: LoadControl = DefaultLoadControl()
        val bandwidthMeter: BandwidthMeter = DefaultBandwidthMeter()
        val trackSelector: TrackSelector = DefaultTrackSelector(
            AdaptiveTrackSelection.Factory(bandwidthMeter)
        )



        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
            this, trackSelector, loadControl
        )
        val factory: DefaultHttpDataSourceFactory = DefaultHttpDataSourceFactory("exoplayer_video")


        val extractorsFactory = DefaultExtractorsFactory()
        val mediaSource: MediaSource =
            ExtractorMediaSource(videoUri, factory, extractorsFactory, null, null)

        player_view.player = simpleExoPlayer
        player_view.keepScreenOn = true
        simpleExoPlayer.prepare(mediaSource)
        simpleExoPlayer.playWhenReady = true


        simpleExoPlayer.addListener(object : Player.EventListener {
            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {

            }

            override fun onSeekProcessed() {

            }


            override fun onTracksChanged(
                trackGroups: TrackGroupArray?,
                trackSelections: TrackSelectionArray?
            ) {

            }

            override fun onPlayerError(error: ExoPlaybackException?) {

            }

            override fun onLoadingChanged(isLoading: Boolean) {

            }

            override fun onPositionDiscontinuity(reason: Int) {

            }

            override fun onRepeatModeChanged(repeatMode: Int) {

            }

            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {

            }

            override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {

            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {

                if (playbackState == Player.STATE_BUFFERING) {
                    progress_bar.visibility = View.VISIBLE

                } else if (playbackState == Player.STATE_READY) {

                    progress_bar.visibility = GONE

                }
            }
        })

        bt_fullScreen.setOnClickListener(View.OnClickListener {
            if (flag) {


                val drawable = resources.getDrawable(R.drawable.ic_fullscreen)
                bt_fullScreen.setImageDrawable(drawable)
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                flag = false
            } else {
                val drawable = resources.getDrawable(R.drawable.ic_fullscreen_exit)
                bt_fullScreen.setImageDrawable(drawable)
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                flag = true
            }


        })
        //simpleExoPlayer.addListener(object : EventListener {}


    }

    override fun setupLiveData() {
        subscribeDetailPlaylist()
        fetchDetailPlaylist()
    }

    override fun onPause() {
        super.onPause()
        simpleExoPlayer.playWhenReady = false
        simpleExoPlayer.playbackState
    }

    override fun onRestart() {
        super.onRestart()
        simpleExoPlayer.playWhenReady = true
        simpleExoPlayer.playbackState

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation ==
            ORIENTATION_LANDSCAPE
        ) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
            player_view?.layoutParams {
                width = ViewGroup.LayoutParams.MATCH_PARENT
                height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
        }

        if (newConfig.orientation == ORIENTATION_PORTRAIT) {

            player_view?.layoutParams {
                width = ViewGroup.LayoutParams.MATCH_PARENT
                height = 600
            }
        }

    }

    fun View.layoutParams(run: ViewGroup.LayoutParams.() -> Unit) {
        val params = layoutParams
        run(params)
        layoutParams = params

    }

}