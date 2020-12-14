package com.example.firstapp.ui.DetailVideo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstapp.R
import com.example.firstapp.data.models.PlaylistItems
import com.example.firstapp.ui.detail_playlist.adapter.DetailPlaylistAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_fragment.*

class YoutubeBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var adapter: DetailPlaylistAdapter
    private lateinit var viewModel: DetVideoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(DetVideoViewModel::class.java)

        setupAdapter()

    }

    private fun setupAdapter() {
        adapter = DetailPlaylistAdapter(requireContext(), this::onItemClick)
        recycler_view_sheet.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler_view_sheet.adapter = adapter

        viewModel.detailPlaylists.observeForever {
            adapter.addItems(it)
        }
    }

    private fun onItemClick(item: PlaylistItems) {
        DetailVideoActivity.instanceActivity(requireActivity(), item)
    }
}