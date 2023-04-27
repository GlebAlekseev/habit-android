package com.glebalekseevjk.habit.data.local.model.converter

import androidx.room.TypeConverter
import com.glebalekseevjk.habit.domain.entity.Habit.Companion.RepeatType

object RepeatTypeConverter {
    @TypeConverter
    fun toRepeatType(value: String): RepeatType {
        return RepeatType.valueOf(value)
    }

    @TypeConverter
    fun fromRepeatType(repeatType: RepeatType): String {
        return repeatType.name
    }
}