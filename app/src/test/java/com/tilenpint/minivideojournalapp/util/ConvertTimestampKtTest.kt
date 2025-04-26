package com.tilenpint.minivideojournalapp.util

import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import java.time.ZoneId
import java.time.ZonedDateTime

class TimestampExtensionTest {

    @Test
    fun `convertTimestampToDate should return correct formatted date`() {
        val zoneId = ZoneId.systemDefault()
        val dateTime = ZonedDateTime.of(2023, 12, 25, 15, 30, 0, 0, zoneId)
        val timestamp = dateTime.toInstant().toEpochMilli()

        val formattedDate = timestamp.convertTimestampToDate()

        assertEquals("25.12.2023 15:30", formattedDate)
    }
}