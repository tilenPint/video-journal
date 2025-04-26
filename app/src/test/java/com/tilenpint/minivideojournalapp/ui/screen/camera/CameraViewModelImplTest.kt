package com.tilenpint.minivideojournalapp.ui.screen.camera

import app.cash.turbine.test
import com.tilenpint.minivideojournalapp.fake.FakeVideoRepository
import com.tilenpint.minivideojournalapp.model.VideoRecording
import com.tilenpint.minivideojournalapp.repository.VideoRepository
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CameraViewModelImplTest {

    private lateinit var viewModel: CameraViewModelImpl
    private val repository: VideoRepository = FakeVideoRepository()

    @Before
    fun setup() {
        viewModel = CameraViewModelImpl(repository)
    }

    @Test
    fun `emitDescription updates description`() = runTest {
        viewModel.emitVideo(testVideo)

        viewModel.emitDescription("new description")

        val state = viewModel.state.first()
        assertEquals("new description", state?.videoRecording?.description)
    }

    @Test
    fun `startRecording sets isRecording`() = runTest {
        viewModel.startRecording()

        val state = viewModel.state.first()
        assertTrue(state?.isRecording == true)
    }

    @Test
    fun `emitVideo sets videoRecording and shows bottom sheet`() = runTest {
        viewModel.emitVideo(testVideo)

        val state = viewModel.state.first()
        assertEquals(testVideo, state?.videoRecording)
        assertTrue(state?.showBottomSheet == true)
    }

    @Test
    fun `emitted back`() = runTest {
        viewModel.emitBackNavigation.test {
            viewModel.saveVideo()
            assertEquals(awaitItem(), Unit)
        }
    }
}

val testVideo = VideoRecording(
    id = "test",
    filePath = "some/path",
    timestamp = 10,
    duration = 10,
    thumbnailPath = "other/path"
)