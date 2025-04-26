package com.tilenpint.minivideojournalapp.repository

import com.tilenpint.minivideojournalapp.Video
import com.tilenpint.minivideojournalapp.model.VideoRecording
import kotlinx.coroutines.flow.Flow


interface VideoRepository {
    val videos: Flow<List<Video>>
    suspend fun saveVideo(video: VideoRecording)
}