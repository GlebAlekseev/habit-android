package com.glebalekseevjk.habit.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.glebalekseevjk.habit.data.local.model.converter.LocalDateTimeTypeConverter
import java.time.LocalDateTime

@Entity
data class EventNotificationDbModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "habit_id") val habitId: Int,
    @ColumnInfo(name = "date") val date: LocalDateTime,
    @ColumnInfo(name = "is_done") val isDone: Boolean
)