package com.example.firstapp.ui.playlists

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstapp.R
import com.example.firstapp.base.BaseActivity
import com.example.firstapp.data.models.PlaylistItems

import com.example.firstapp.ui.detail_playlist.DetailPlaylistActivity
import com.example.firstapp.ui.playlists.adapter.MainAdapter
import kotlinx.android.synthetic.main.activity_main.*

class PlaylistsActivity :
    BaseActivity<PlaylistViewModel>(R.layout.activity_main, PlaylistViewModel::class) {

    private lateinit var adapter: MainAdapter

    // override val viewModel by inject<PlaylistViewModel>()
    private fun setupAdapter() {
        adapter = MainAdapter(this::onItemClick)
        recycler_view.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_view.adapter = adapter
    }

    private fun subscribeToPlaylists() {
        viewModel.playlists.observeForever {
            adapter.addItems(it)
        }
    }

    private fun onItemClick(item: PlaylistItems) {
        DetailPlaylistActivity.instanceActivity(this, item)
    }

    override fun setupViews() {
        setupAdapter()

    }

    override fun setupLiveData() {
        subscribeToPlaylists()
        viewModel.fetchPlaylists()
    }

    override fun setupFetchRequests() {

        languagesCh.setOnClickListener {
            initLanguage()
        }
    }
}