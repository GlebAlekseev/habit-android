package com.glebalekseevjk.habit.data.local.model

import androidx.room.*
import com.glebalekseevjk.habit.data.local.model.converter.*
import com.glebalekseevjk.habit.domain.entity.Habit.Companion.Day
import com.glebalekseevjk.habit.domain.entity.Habit.Companion.HabitType
import com.glebalekseevjk.habit.domain.entity.Habit.Companion.RepeatType
import com.glebalekseevjk.habit.domain.entity.Habit.Companion.TargetType
import org.jetbrains.annotations.NotNull
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Entity
data class HabitDbModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "icon_id") val iconId: Int,
    @ColumnInfo(name = "color_rgba") val colorRGBA: Int,
    @ColumnInfo(name = "repeat_type") val repeatType: RepeatType,
    @ColumnInfo(name = "days_of_repeat") val daysOfRepeat: Set<Day>,
    @ColumnInfo(name = "start_execution_interval") val startExecutionInterval: LocalTime?,
    @ColumnInfo(name = "end_execution_interval") val endExecutionInterval: LocalTime?,
    @ColumnInfo(name = "deadline") val deadline: LocalDate?,
    @ColumnInfo(name = "habit_type") val habitType: HabitType,
    @ColumnInfo(name = "target_type") val targetType: TargetType,
    @ColumnInfo(name = "repeat_count") val repeatCount: Int?,
    @ColumnInfo(name = "duration") val duration: LocalTime?,
    @ColumnInfo(name = "created_at") val createdAt: LocalDateTime,
)
