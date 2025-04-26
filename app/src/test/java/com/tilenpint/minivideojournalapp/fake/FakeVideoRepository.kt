package com.tilenpint.minivideojournalapp.fake

import com.tilenpint.minivideojournalapp.Video
import com.tilenpint.minivideojournalapp.model.VideoRecording
import com.tilenpint.minivideojournalapp.repository.VideoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeVideoRepository : VideoRepository {
    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    override val videos: Flow<List<Video>> = _videos

    override suspend fun saveVideo(video: VideoRecording) {
        _videos.emit(
            _videos.value + Video(
                id = video.id,
                filePath = video.filePath,
                description = video.description,
                timestamp = video.timestamp,
                duration = video.duration,
                thumbnailFilePath = video.thumbnailPath
            )
        )
    }
}