package com.tilenpint.minivideojournalapp.model

data class VideoRecording(
    val id: String,
    val filePath: String,
    val timestamp: Long,
    val duration: Long,
    val thumbnailPath: String,
    val description: String? = null
)