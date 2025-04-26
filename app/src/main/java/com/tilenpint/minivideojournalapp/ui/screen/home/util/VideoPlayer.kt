package com.tilenpint.minivideojournalapp.ui.screen.home.util

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.tilenpint.minivideojournalapp.R
import com.tilenpint.minivideojournalapp.Video
import com.tilenpint.minivideojournalapp.ui.theme.MiniVideoJournalAppTheme
import com.tilenpint.minivideojournalapp.util.convertTimestampToDate
import kotlinx.coroutines.delay

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun VideoPlayer(video: Video, modifier: Modifier) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var showThumbnail by remember { mutableStateOf(true) }

    var exoIsPlaying by remember { mutableStateOf(false) }

    val exoPlayer = remember(context) {
        ExoPlayer.Builder(context).build().apply {
            repeatMode = Player.REPEAT_MODE_ONE
            setMediaItem(MediaItem.fromUri(video.filePath))

            prepare()
            addListener(object : Player.Listener {
                override fun onRenderedFirstFrame() {
                    super.onRenderedFirstFrame()
                    showThumbnail = false
                }

                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)
                    exoIsPlaying = isPlaying
                }
            })
        }
    }

    var progress by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(exoPlayer) {
        while (true) {
            progress = exoPlayer.contentPosition / exoPlayer.duration.toFloat()
            delay(100)
        }
    }

    DisposableEffect(key1 = lifecycleOwner) {
        val lifeCycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_STOP -> {
                    exoPlayer.pause()
                }

                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(lifeCycleObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifeCycleObserver)
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        AndroidView(
            factory = {
                PlayerView(it).apply {
                    useController = false
                    player = exoPlayer
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
                }
            },
            modifier = Modifier,
            onRelease = {
                showThumbnail = true
                exoPlayer.release()
            }
        )

        if (showThumbnail) {
            GlideImage(
                modifier = Modifier.fillMaxSize(),
                model = video.thumbnailFilePath,
                contentDescription = "desc",
                contentScale = ContentScale.Crop
            )
        }

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
                .clickable {
                    if (exoIsPlaying) {
                        exoPlayer.pause()
                    } else {
                        exoPlayer.play()
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            if (exoIsPlaying) return@Box
            Icon(
                modifier = Modifier.size(128.dp),
                imageVector = Icons.Default.PlayArrow,
                contentDescription = stringResource(R.string.play)
            )
        }


        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(topEnd = 64.dp))
                .background(Color.Black.copy(0.3f))
                .align(Alignment.BottomStart)
                .padding(32.dp),
        ) {
            video.description?.let { description -> Text(description) }
            Text(video.timestamp.convertTimestampToDate())
        }

        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            progress = { progress }
        )
    }
}
