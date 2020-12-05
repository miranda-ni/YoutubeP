package com.example.firstapp.data.models

import android.util.SparseArray
import at.huber.youtubeExtractor.YtFile

data class YoutubeVideo(
    var height: Int = 0,
    var audioFile: YtFile? = null,
    var videoFile: YtFile? = null,
    var isSelected: Boolean = false
) {

    private val ITAG_FOR_AUDIO = 140
    fun addFormatToList(array: MutableList<YoutubeVideo>, ytFile: YtFile, ytFiles: SparseArray<YtFile>): YoutubeVideo {
        if (ytFile.format.height != -1 || ytFile.format.height < 360) return YoutubeVideo()
        val height = ytFile.format.height
        if (height != -1) {
            for (frVideo in array) {
                if (frVideo.height == height && (frVideo.videoFile == null || frVideo.videoFile!!.format.fps == ytFile.format.fps)) {
                     return YoutubeVideo()
                }
            }
        }
        val frVideo = YoutubeVideo()
        frVideo.height = height
        if (ytFile.format.isDashContainer) {
            if (height > 0) {
                frVideo.videoFile = ytFile
                frVideo.audioFile = ytFiles.get(ITAG_FOR_AUDIO)
            } else {
                frVideo.audioFile = ytFile
            }
        } else {
            frVideo.videoFile = ytFile
        }
        return frVideo
    }
}
