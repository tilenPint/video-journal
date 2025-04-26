package com.tilenpint.minivideojournalapp.ui.screen.home.util.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.tilenpint.minivideojournalapp.Video
import com.tilenpint.minivideojournalapp.ui.theme.MiniVideoJournalAppTheme
import com.tilenpint.minivideojournalapp.util.LightModeScreen
import com.tilenpint.minivideojournalapp.util.convertTimestampToDate

@Composable
fun VideoData(
    modifier: Modifier = Modifier,
    video: Video
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(topEnd = 64.dp))
            .background(MaterialTheme.colorScheme.surface.copy(0.3f))
            .padding(32.dp),
    ) {
        if (!video.description.isNullOrBlank()) {
            Text(video.description)
        }
        Text(video.timestamp.convertTimestampToDate())
    }
}

@Composable
@LightModeScreen
private fun VideoDataWithDescPreview() {
    MiniVideoJournalAppTheme {
        VideoData(
            video = Video(
                id = "1",
                filePath = "test",
                timestamp = 123456789,
                duration = 123456789,
                thumbnailFilePath = "test",
                description = "test"
            )
        )
    }
}

@Composable
@LightModeScreen
private fun VideoDataNoDescPreview() {
    MiniVideoJournalAppTheme {
        VideoData(
            video = Video(
                id = "1",
                filePath = "test",
                timestamp = 123456789,
                duration = 123456789,
                thumbnailFilePath = "test",
                description = ""
            )
        )
    }
}