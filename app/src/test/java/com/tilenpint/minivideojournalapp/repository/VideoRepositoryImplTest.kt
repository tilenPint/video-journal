package com.tilenpint.minivideojournalapp.repository

import com.tilenpint.minivideojournalapp.VideoQueries
import com.tilenpint.minivideojournalapp.model.VideoRecording
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class VideoRepositoryImplTest {

    private val videoQueries = mockk<VideoQueries>(relaxed = true)
    private lateinit var repository: VideoRepositoryImpl

    @Before
    fun setup() {
        repository = VideoRepositoryImpl(videoQueries)
    }

    @Test
    fun `saveVideo should call insertVideo with correct params`() = runTest {
        val video = VideoRecording(
            id = "123",
            filePath = "/path/to/video.mp4",
            timestamp = 1234567890L,
            duration = 30_000L,
            description = "Test video",
            thumbnailPath = "/path/to/thumbnail.jpg"
        )

        repository.saveVideo(video)

        verify {
            videoQueries.insertVideo(
                id = video.id,
                filePath = video.filePath,
                timestamp = video.timestamp,
                duration = video.duration,
                description = video.description,
                thumbnailFilePath = video.thumbnailPath
            )
        }
    }
}