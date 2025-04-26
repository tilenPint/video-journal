package com.tilenpint.minivideojournalapp.ui.screen.home.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.tilenpint.minivideojournalapp.Video
import com.tilenpint.minivideojournalapp.ui.screen.home.util.ui.HomeOverlay
import kotlinx.coroutines.delay

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun VideoPlayer(video: Video, modifier: Modifier) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var showThumbnail by remember { mutableStateOf(true) }

    var exoIsPlaying by remember { mutableStateOf(false) }

    var progress by remember { mutableFloatStateOf(0f) }

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

        HomeOverlay(
            video = video,
            progress = progress,
            mainAction = {
                if (exoIsPlaying) {
                    exoPlayer.pause()
                } else {
                    exoPlayer.play()
                }
            },
            exoIsPlaying = exoIsPlaying
        )
    }
}
