package com.tilenpint.minivideojournalapp.ui.screen.camera

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tilenpint.minivideojournalapp.model.VideoRecording
import com.tilenpint.minivideojournalapp.repository.VideoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class CameraViewModelImpl(
    private val videoRepository: VideoRepository
) : CameraViewModel, ViewModel() {
    private val _state = MutableStateFlow<CameraUiState?>(CameraUiState())
    override val state: Flow<CameraUiState?> = _state

    private val _emitBackNavigation = MutableSharedFlow<Unit>(replay = 0)
    override val emitBackNavigation: SharedFlow<Unit> = _emitBackNavigation

    override fun emitDescription(description: String) {
        _state.value = _state.value?.copy(
            videoRecording = _state.value?.videoRecording?.copy(description = description)
        )
    }

    override fun emitVideo(videoRecording: VideoRecording) {
        _state.value = _state.value?.copy(
            videoRecording = videoRecording,
            showBottomSheet = true
        )
    }

    override fun startRecording() {
        _state.value = _state.value?.copy(
            isRecording = true
        )
    }

    override fun saveVideo() {
        viewModelScope.launch {
            _state.value = _state.value?.copy(
                isRecording = false,
                loading = true,
                showBottomSheet = false
            )
            _state.value?.videoRecording?.let { videoRepository.saveVideo(it) } // TODO success/error

            _state.value = _state.value?.copy(loading = false)

            _emitBackNavigation.emit(Unit)
        }
    }
}