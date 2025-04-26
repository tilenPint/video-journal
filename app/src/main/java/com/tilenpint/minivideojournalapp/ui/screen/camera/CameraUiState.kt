package com.tilenpint.minivideojournalapp.ui.screen.camera

import com.tilenpint.minivideojournalapp.model.VideoRecording

data class CameraUiState(
    val isRecording: Boolean = false,
    val loading: Boolean = false,
    val videoRecording: VideoRecording? = null,
    val showBottomSheet: Boolean = false
)