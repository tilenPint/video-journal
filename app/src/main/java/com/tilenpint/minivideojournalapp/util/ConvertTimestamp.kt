package com.tilenpint.minivideojournalapp.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


fun Long.convertTimestampToDate(): String {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
        .withZone(ZoneId.systemDefault())

    return formatter.format(Instant.ofEpochMilli(this))
}