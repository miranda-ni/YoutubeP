package com.example.firstapp.ui.playlists.adapter

import android.annotation.SuppressLint
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

class MainAdapter(private var onItemClick: (PlaylistItems) -> Unit)
    : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    var list = mutableListOf<PlaylistItems>()
    lateinit var holder: ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_main, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        this.holder = holder
        val item = list[position]
        holder.image.loadImage(item.snippet?.thumbnails?.medium?.url)
        holder.title.text = item.snippet?.title
        holder.videoSeries.text=item.contentDetails?.itemCount+" video series"

        Log.v("descrip", "descrip"+item.snippet?.description)
        holder.itemView.setOnClickListener {
            onItemClick(item)
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

    fun itemRemove(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
        Log.v("DELETED_POSITION_AT", "$position")
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.whiteImage)
        var title: TextView = itemView.findViewById(R.id.title)
        var videoSeries: TextView = itemView.findViewById(R.id.series)


    }

}