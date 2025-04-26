package com.tilenpint.minivideojournalapp.ui.screen.home

import com.tilenpint.minivideojournalapp.Video

data class HomeUiState(
    val videos: List<Video> = emptyList()
) {
    val isEmpty: Boolean = videos.isEmpty()
}