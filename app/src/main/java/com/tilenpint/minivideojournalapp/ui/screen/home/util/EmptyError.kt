package com.tilenpint.minivideojournalapp.ui.screen.home.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tilenpint.minivideojournalapp.ui.theme.MiniVideoJournalAppTheme
import com.tilenpint.minivideojournalapp.util.FullScreenPreview

@Composable
fun EmptyError(
    action: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Error", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.size(8.dp))
            Text(text = "There are no videos", style = MaterialTheme.typography.bodyMedium)

            Spacer(Modifier.size(32.dp))

            Button(onClick = action) {
                Text(text = "Add Video")
            }
        }
    }
}

@Composable
@FullScreenPreview
private fun EmptyErrorPreview() {
    MiniVideoJournalAppTheme {
        EmptyError()
    }
}