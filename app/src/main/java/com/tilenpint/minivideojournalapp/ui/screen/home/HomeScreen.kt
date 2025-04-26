package com.tilenpint.minivideojournalapp.ui.screen.home

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tilenpint.minivideojournalapp.R
import com.tilenpint.minivideojournalapp.Video
import com.tilenpint.minivideojournalapp.ui.screen.home.util.EmptyError
import com.tilenpint.minivideojournalapp.ui.screen.home.util.VideoPlayer
import com.tilenpint.minivideojournalapp.ui.theme.MiniVideoJournalAppTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModelImpl,
    navigateToCamera: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle(null).value ?: return
    HomeContent(
        state = state,
        navigateToCamera = navigateToCamera
    )
}

@Composable
private fun HomeContent(
    state: HomeUiState,
    navigateToCamera: () -> Unit
) {
    val listState = rememberLazyListState()
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToCamera) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add)
                )
            }
        }
    ) { padding ->

        if (state.isEmpty) {
            EmptyError(navigateToCamera)
        } else {
            LazyColumn(
                state = listState,
                flingBehavior = flingBehavior,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(state.videos) {
                    VideoPlayer(it, modifier = Modifier.fillParentMaxSize())
                }
            }
        }
    }
}

@Composable
@Preview
fun EmptyHomeScreenPreview() {
    MiniVideoJournalAppTheme {
        HomeContent(
            state = HomeUiState(),
            navigateToCamera = {}
        )
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    MiniVideoJournalAppTheme {
        HomeContent(
            state = HomeUiState(
                videos = listOf(
                    Video(
                        id = "1",
                        filePath = "",
                        timestamp = 1232313123,
                        duration = 0,
                        thumbnailFilePath = "",
                        description = "testDescription"
                    ),

                    Video(
                        id = "1",
                        filePath = "",
                        timestamp = 2222222222,
                        duration = 0,
                        thumbnailFilePath = "",
                        description = "testDescription 2"
                    )
                )
            ),
            navigateToCamera = {}
        )
    }
}