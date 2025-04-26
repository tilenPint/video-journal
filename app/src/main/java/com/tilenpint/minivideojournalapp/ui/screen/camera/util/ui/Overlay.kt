package com.tilenpint.minivideojournalapp.ui.screen.camera.util.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tilenpint.minivideojournalapp.R
import com.tilenpint.minivideojournalapp.ui.theme.MiniVideoJournalAppTheme

@Composable
fun Overlay(
    isRecording: Boolean,
    rotateCamera: () -> Unit,
    recordVideo: () -> Unit
) {
    Box(
        Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        IconButton(
            modifier = Modifier
                .padding(32.dp)
                .align(Alignment.TopStart),
            onClick = rotateCamera
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_rotate_camera),
                contentDescription = stringResource(R.string.rotate_camera)
            )
        }

        IconButton(
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.BottomCenter)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.2f))
                .run {
                    if (isRecording) {
                        border(2.dp, Color.Red, CircleShape)
                    } else {
                        this
                    }
                },
            onClick = recordVideo
        ) {
            if (!isRecording) {
                Icon(
                    Icons.Default.PlayArrow,
                    contentDescription = stringResource(R.string.start_recording)
                )
            }
        }
    }
}

@Composable
@Preview
fun PlayingOverlay() {
    MiniVideoJournalAppTheme {
        Overlay(isRecording = true, rotateCamera = {}, recordVideo = {})
    }
}

@Composable
@Preview
fun PausedOverlay() {
    MiniVideoJournalAppTheme {
        Overlay(isRecording = false, rotateCamera = {}, recordVideo = {})
    }
}