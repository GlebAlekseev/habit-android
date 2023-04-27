package com.glebalekseevjk.habit.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.glebalekseevjk.habit.data.local.model.converter.*
import com.glebalekseevjk.habit.domain.entity.Habit.Companion.Day
import com.glebalekseevjk.habit.domain.entity.Habit.Companion.HabitType
import com.glebalekseevjk.habit.domain.entity.Habit.Companion.RepeatType
import com.glebalekseevjk.habit.domain.entity.Habit.Companion.TargetType
import java.time.LocalDate
import java.time.LocalTime

@Entity
data class HabitDbModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "icon_id") val iconId: Int,
    @ColumnInfo(name = "color_rgba") val colorRGBA: Int,
    @ColumnInfo(name = "repeat_type") @TypeConverters(RepeatTypeConverter::class) val repeatType: RepeatType,
    @ColumnInfo(name = "days_of_repeat") @TypeConverters(SetDayTypeConverter::class) val daysOfRepeat: Set<Day>,
    @ColumnInfo(name = "start_execution_interval") @TypeConverters(LocalTimeTypeConverter::class) val startExecutionInterval: LocalTime?,
    @ColumnInfo(name = "end_execution_interval") @TypeConverters(LocalTimeTypeConverter::class) val endExecutionInterval: LocalTime?,
    @ColumnInfo(name = "deadline") @TypeConverters(LocalDateTypeConverter::class) val deadline: LocalDate?,
    @ColumnInfo(name = "habit_type") @TypeConverters(HabitTypeConverter::class) val habitType: HabitType,
    @ColumnInfo(name = "target_type") @TypeConverters(TargetTypeConverter::class) val targetType: TargetType,
    @ColumnInfo(name = "repeat_count") val repeatCount: Int?,
    @ColumnInfo(name = "duration") @TypeConverters(LocalTimeTypeConverter::class) val duration: LocalTime?,
)
