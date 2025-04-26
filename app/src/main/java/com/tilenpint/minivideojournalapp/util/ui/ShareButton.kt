package com.tilenpint.minivideojournalapp.util.ui

import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.FileProvider
import com.tilenpint.minivideojournalapp.R
import com.tilenpint.minivideojournalapp.ui.theme.MiniVideoJournalAppTheme
import com.tilenpint.minivideojournalapp.util.LightModeScreen
import java.io.File

@Composable
fun ShareButton(
    videoPath: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    FilledIconButton(
        modifier = modifier,
        onClick = {
            val videoFile = File(videoPath)
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                videoFile
            )

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "video/*"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            context.startActivity(
                Intent.createChooser(shareIntent, context.getString(R.string.share_video_via))
            )
        }
    ) {
        Icon(
            modifier = Modifier,
            imageVector = Icons.Default.Share,
            contentDescription = stringResource(R.string.share)
        )
    }
}

@Composable
@LightModeScreen
private fun ShareButtonPreview() {
    MiniVideoJournalAppTheme {
        ShareButton(videoPath = "")
    }
}