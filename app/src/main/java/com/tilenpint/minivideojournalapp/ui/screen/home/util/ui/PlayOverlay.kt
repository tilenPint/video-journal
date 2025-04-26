package com.tilenpint.minivideojournalapp.ui.screen.home.util.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tilenpint.minivideojournalapp.R

@Composable
fun PlayOverlay(exoIsPlaying: Boolean, mainAction: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                if (exoIsPlaying) {
                    Color.Transparent
                } else {
                    Color.Black.copy(0.4f)
                }
            )
            .clickable(onClick = mainAction),
        contentAlignment = Alignment.Center
    ) {
        if (exoIsPlaying) return
        Icon(
            modifier = Modifier.size(128.dp),
            imageVector = Icons.Default.PlayArrow,
            contentDescription = stringResource(R.string.play)
        )
    }
}

@Composable
@Preview
private fun PlayOverlayPreview() {
    PlayOverlay(exoIsPlaying = true, mainAction = {})
}

@Composable
@Preview
private fun PlayOverlayPreview2() {
    PlayOverlay(exoIsPlaying = false, mainAction = {})
}