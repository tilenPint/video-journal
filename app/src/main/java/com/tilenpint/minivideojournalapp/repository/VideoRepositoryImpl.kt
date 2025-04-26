package com.tilenpint.minivideojournalapp.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.tilenpint.minivideojournalapp.Video
import com.tilenpint.minivideojournalapp.VideoQueries
import com.tilenpint.minivideojournalapp.model.VideoRecording
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class VideoRepositoryImpl(
    private val videoQueries: VideoQueries
) : VideoRepository {
    override val videos: Flow<List<Video>> = videoQueries.getVideos().asFlow()
        .mapToList(Dispatchers.IO)

    override suspend fun saveVideo(video: VideoRecording) {
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