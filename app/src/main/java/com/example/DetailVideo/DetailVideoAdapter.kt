package com.example.DetailVideo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.R
import com.example.firstapp.data.models.PlaylistItems
import com.example.firstapp.loadImage

class DetailVideoAdapter(private var onItemClick1: (PlaylistItems) -> Unit) :
    RecyclerView.Adapter<DetailVideoAdapter.ViewHolderVideo>() {

    var list = mutableListOf<PlaylistItems>()
    lateinit var holder: ViewHolderVideo
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderVideo {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.videolist, parent, false)
        return ViewHolderVideo(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolderVideo, position: Int) {
        this.holder = holder
        val item = list[position]
        holder.image.loadImage(item.snippet?.thumbnails?.medium?.url)
        holder.title.text = item.snippet?.title
        holder.drscription.text = item.snippet?.description

        holder.itemView.setOnClickListener {
            onItemClick1(item)
            holder.adapterPosition
        }
    }

    fun addItems(items: MutableList<PlaylistItems>) {
        list = items
        notifyDataSetChanged()
    }

    fun addItem(item: PlaylistItems) {
        list.add(item)
        notifyItemInserted(list.lastIndex)
    }

    class ViewHolderVideo(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.mainImage)
        var title: TextView = itemView.findViewById(R.id.titleVideo)
        var drscription: TextView = itemView.findViewById(R.id.descriptionVideo)
    }
}