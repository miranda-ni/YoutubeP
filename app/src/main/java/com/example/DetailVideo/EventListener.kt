package com.example.DetailVideo

import android.text.Editable
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Timeline
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.TrackSelectionArray

  abstract class EventListener(val onSucces:(Editable?)-> Unit):Player. EventListener{
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





}}