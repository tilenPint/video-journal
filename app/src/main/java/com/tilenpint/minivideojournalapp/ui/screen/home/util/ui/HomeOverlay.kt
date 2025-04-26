package com.tilenpint.minivideojournalapp.ui.screen.home.util.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tilenpint.minivideojournalapp.Video
import com.tilenpint.minivideojournalapp.ui.theme.MiniVideoJournalAppTheme
import com.tilenpint.minivideojournalapp.util.FullScreenPreview


@Composable
fun BoxScope.HomeOverlay(
    video: Video,
    mainAction: () -> Unit,
    exoIsPlaying: Boolean,
    progress: Float
) {
    PlayOverlay(
        mainAction = mainAction,
        exoIsPlaying = exoIsPlaying
    )

    VideoData(
        modifier = Modifier.align(Alignment.BottomStart),
        video = video
    )

    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter),
        progress = { progress }
    )
}

@Composable
@FullScreenPreview
private fun HomeOverlayPreviewPlaying() {
    MiniVideoJournalAppTheme {
        Box {
            HomeOverlay(
                video = Video(
                    id = "1",
                    filePath = "",
                    timestamp = 1232313123,
                    duration = 0,
                    thumbnailFilePath = "",
                    description = "testDescription"
                ),
                progress = 0.5f,
                mainAction = {},
                exoIsPlaying = true
            )
        }
    }
}

@Composable
@FullScreenPreview
private fun HomeOverlayPreviewPaused() {
    MiniVideoJournalAppTheme {
        Box {
            HomeOverlay(
                video = Video(
                    id = "1",
                    filePath = "",
                    timestamp = 1232313123,
                    duration = 0,
                    thumbnailFilePath = "",
                    description = ""
                ),
                progress = 0.5f,
                mainAction = {},
                exoIsPlaying = false
            )
        }
    }
}
