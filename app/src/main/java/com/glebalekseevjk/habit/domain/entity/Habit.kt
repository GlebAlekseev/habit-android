package com.glebalekseevjk.habit.domain.entity

import java.time.LocalDate
import java.time.LocalTime

data class Habit(
    val id: Int = UNDEFINED_ID,
    val title: String,
    val description: String,
    val iconId: Int,
    val colorRGBA: Int,
    val repeatType: RepeatType,
    val daysOfRepeat: Set<Day>,
    val startExecutionInterval: LocalTime?,
    val endExecutionInterval: LocalTime?,
    val deadline: LocalDate?,
    val habitType: HabitType,
    val targetType: TargetType = TargetType.OFF,
    val repeatCount: Int?,
    val duration: LocalTime?,
) {
    companion object {
        const val UNDEFINED_ID = 0

        enum class RepeatType {
            SPECIFIC_DAYS,
            X_DAYS_PER_WEEK,
            X_DAYS_PER_MONTH,
            X_DAYS_PER_YEAR,
        }

        enum class Day {
            MONDAY,
            TUESDAY,
            WEDNESDAY,
            THURSDAY,
            FRIDAY,
            SATURDAY,
            SUNDAY,
        }

        enum class HabitType {
            REGULAR,
            HARMFUL,
            DISPOSABLE
        }

        enum class TargetType {
            OFF,
            REPEAT,
            DURATION
        }
    }
}