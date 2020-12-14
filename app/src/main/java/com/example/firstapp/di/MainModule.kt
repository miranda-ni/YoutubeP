package com.example.firstapp.di
import com.example.firstapp.ui.DetailVideo.DetVideoViewModel
import com.example.firstapp.RoomBd.DatabaseClient
import com.example.firstapp.data.network.provideYoutubeApi
import com.example.firstapp.repository.YoutubeRepository
import com.example.firstapp.ui.detail_playlist.DetailPlaylistViewModel
import com.example.firstapp.ui.playlists.PlaylistViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var viewModelModule = module {
    viewModel { PlaylistViewModel(get()) }
    viewModel { DetailPlaylistViewModel(get()) }
    viewModel { DetVideoViewModel(get()) }
}

var networkModule = module {
    single { provideYoutubeApi() }
}

var repositoryModule = module {
    factory { YoutubeRepository(get(), get()) }
}

var dbModule = module {
    single { DatabaseClient().provideDatabase(androidContext()) }
    single { DatabaseClient().providePlaylistDao(get()) }
}