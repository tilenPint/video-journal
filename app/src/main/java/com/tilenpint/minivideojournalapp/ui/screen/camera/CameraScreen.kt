package com.tilenpint.minivideojournalapp.ui.screen.camera

import androidx.activity.compose.LocalActivity
import androidx.camera.core.CameraSelector
import androidx.camera.video.Recording
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tilenpint.minivideojournalapp.model.VideoRecording
import com.tilenpint.minivideojournalapp.ui.screen.camera.util.recordVideo
import com.tilenpint.minivideojournalapp.ui.screen.camera.util.ui.BottomSheetContent
import com.tilenpint.minivideojournalapp.ui.screen.camera.util.ui.CameraPreviewContent
import com.tilenpint.minivideojournalapp.ui.screen.camera.util.ui.Overlay

@Composable
fun CameraScreen(
    viewModel: CameraViewModel,
    backNavigation: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle(null).value ?: return
    val activity = LocalActivity.current ?: return
    val context = LocalContext.current


    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE or CameraController.VIDEO_CAPTURE)
        }
    }

    val recording = remember {
        mutableStateOf<Recording?>(null)
    }

    DisposableEffect(Unit) {
        val window = activity.window
        val windowInsertController = WindowCompat.getInsetsController(window, window.decorView)

        windowInsertController.hide(WindowInsetsCompat.Type.statusBars())
        onDispose { windowInsertController.show(WindowInsetsCompat.Type.statusBars()) }
    }

    LaunchedEffect(Unit) {
        viewModel.emitBackNavigation.collect {
            backNavigation()
        }
    }

    CameraContent(
        state = state,
        controller = controller,
        recording = recording.value,
        updateRecording = { recording.value = it },
        emitVideo = viewModel::emitVideo,
        startRecording = viewModel::startRecording,
        emitDescription = viewModel::emitDescription,
        saveVideo = viewModel::saveVideo
    )
}

@Composable
private fun CameraContent(
    state: CameraUiState,
    controller: LifecycleCameraController,
    recording: Recording?,
    updateRecording: (Recording?) -> Unit,
    emitVideo: (VideoRecording) -> Unit,
    startRecording: () -> Unit,
    emitDescription: (String) -> Unit,
    saveVideo: () -> Unit
) {
    val context = LocalContext.current
    val activity = LocalActivity.current ?: return

    CameraPreviewContent(
        controller = controller,
        modifier = Modifier.fillMaxSize()
    )

    Overlay(
        isRecording = state.isRecording,
        rotateCamera = {
            controller.cameraSelector =
                if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                    CameraSelector.DEFAULT_FRONT_CAMERA
                } else {
                    CameraSelector.DEFAULT_BACK_CAMERA
                }
        }
    ) {
        recordVideo(
            recording = recording,
            updateRecording = updateRecording,
            applicationContext = context,
            controller = controller,
            filesDir = activity.filesDir,
            emitVideo = emitVideo,
            startRecording = startRecording
        )
    }

    BottomSheetContent(
        showBottomSheer = state.showBottomSheet,
        fieldValue = state.videoRecording?.description ?: "",
        fieldChanged = emitDescription,
        saveVideo = saveVideo
    )
}
