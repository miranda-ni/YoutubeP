package com.example.firstapp.converter



import androidx.room.TypeConverter
import com.example.firstapp.data.models.PlaylistItems
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object PlaylistItemsConverter {

    private val gson = Gson()
    private val type: Type = object : TypeToken<MutableList<PlaylistItems>>() {}.type

    @TypeConverter
    @JvmStatic
    fun daysOfWeekToString(playlistItems: MutableList<PlaylistItems>?): String? =
        gson.toJson(playlistItems, type)

    @TypeConverter
    @JvmStatic
    fun stringToDaysOfWeek(playlistItems: String?): MutableList<PlaylistItems>? =
        gson.fromJson(playlistItems, type)

}