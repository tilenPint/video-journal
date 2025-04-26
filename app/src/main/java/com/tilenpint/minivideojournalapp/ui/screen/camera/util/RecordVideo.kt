package com.tilenpint.minivideojournalapp.ui.screen.camera.util

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.Recording
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.video.AudioConfig
import androidx.core.content.ContextCompat
import com.tilenpint.minivideojournalapp.model.VideoRecording
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@OptIn(ExperimentalUuidApi::class)
fun recordVideo(
    recording: Recording?,
    updateRecording: (Recording?) -> Unit,
    applicationContext: Context,
    controller: LifecycleCameraController,
    filesDir: File,
    emitVideo: (VideoRecording) -> Unit,
    startRecording: () -> Unit
) {
    var recordingCopy = recording
    if (recordingCopy != null) {
        recordingCopy.stop()
        recordingCopy = null
        return
    }


    val baseOutputFile = File(filesDir, Uuid.random().toHexString())

    val videOutputFile = File("${baseOutputFile.path}.mp4")
    val thumbnailOutputFile = File("${baseOutputFile.path}.png")

    val videoRecording = VideoRecording(
        id = videOutputFile.nameWithoutExtension,
        filePath = videOutputFile.path,
        timestamp = 0,
        duration = 0,
        thumbnailPath = thumbnailOutputFile.path
    )

    recordingCopy = controller.startRecording(
        FileOutputOptions.Builder(videOutputFile).build(),
        AudioConfig.AUDIO_DISABLED,
        ContextCompat.getMainExecutor(applicationContext)
    ) { event ->
        when (event) {
            is VideoRecordEvent.Finalize -> {
                if (event.hasError()) {
                    recordingCopy?.close()
                    recordingCopy = null

                    Toast.makeText(
                        applicationContext,
                        "Video capture failed",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    emitVideo(
                        videoRecording.copy(
                            duration = TimeUnit.MILLISECONDS.convert(
                                event.recordingStats.recordedDurationNanos,
                                TimeUnit.NANOSECONDS
                            ),
                            timestamp = System.currentTimeMillis()
                        )
                    )
                }
            }

            is VideoRecordEvent.Start -> {
                takePhoto(context = applicationContext, controller = controller, {
                    thumbnailOutputFile.outputStream().use { out ->
                        it.compress(Bitmap.CompressFormat.PNG, 85, out)
                        out.flush()
                    }
                })
                startRecording()
            }
        }
    }

    updateRecording(recordingCopy)
}
