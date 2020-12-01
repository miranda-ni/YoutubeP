package com.example.firstapp.ui.detail_playlist.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.R
import com.example.firstapp.loadImage
import com.example.firstapp.data.models.PlaylistItems
import com.example.firstapp.ui.detail_playlist.DetailPlaylistActivity

class DetailPlaylistAdapter(var context: Context,private var onItemClick: (PlaylistItems) -> Unit)
    : RecyclerView.Adapter<DetailPlaylistAdapter.ViewHolder>() {

    var list = mutableListOf<PlaylistItems>()
    lateinit var holder: ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false)
        return ViewHolder(view,context )
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        this.holder = holder
        val item = list[position]
        holder.onBind(item)
        holder.itemView.setOnClickListener {

            onItemClick(item)

        }
    }

    fun addItems(items: MutableList<PlaylistItems>) {
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(item: PlaylistItems) {
        list.add(item)
        notifyItemInserted(list.lastIndex)
    }

    class ViewHolder(itemView: View,var context: Context): RecyclerView.ViewHolder(itemView) {


        var image: ImageView = itemView.findViewById(R.id.image)
        var title: TextView = itemView.findViewById(R.id.title)
        var amount: TextView = itemView.findViewById(R.id.amount)

        fun onBind(item: PlaylistItems) {
            image.loadImage(item.snippet?.thumbnails?.medium?.url)
            title.text = item.snippet?.title
            amount.text = item.snippet?.description







        }
    }

}