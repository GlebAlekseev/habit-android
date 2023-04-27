package com.glebalekseevjk.habit.data.local.model.converter

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDate.ofEpochDay
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object LocalDateTimeTypeConverter {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    fun toLocalDateTime(value: String): LocalDateTime {
        return formatter.parse(value, LocalDateTime::from)
    }

    @TypeConverter
    fun fromLocalDateTime(time: LocalDateTime): String {
        return time.format(formatter)
    }
}