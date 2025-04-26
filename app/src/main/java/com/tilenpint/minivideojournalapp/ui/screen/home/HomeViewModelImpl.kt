package com.tilenpint.minivideojournalapp.ui.screen.home

import androidx.lifecycle.ViewModel
import com.tilenpint.minivideojournalapp.repository.VideoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HomeViewModelImpl(repository: VideoRepository): ViewModel(), HomeViewModel {
    override val state: Flow<HomeUiState?> = repository.videos.map {
        HomeUiState(videos = it)
    }
}