package com.tilenpint.minivideojournalapp.ui.screen.camera

import com.tilenpint.minivideojournalapp.model.VideoRecording
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface CameraViewModel {
    val state: Flow<CameraUiState?>
    val emitBackNavigation: SharedFlow<Unit>

    fun emitDescription(description: String)
    fun emitVideo(videoRecording: VideoRecording)
    fun startRecording()
    fun saveVideo()
}