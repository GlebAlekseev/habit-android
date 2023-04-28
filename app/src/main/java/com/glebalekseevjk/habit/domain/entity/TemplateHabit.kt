package com.glebalekseevjk.habit.domain.entity

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.domain.entity.Habit.Companion.Day
import com.glebalekseevjk.habit.domain.entity.Habit.Companion.Day.*
import com.glebalekseevjk.habit.domain.entity.Habit.Companion.HabitType
import com.glebalekseevjk.habit.domain.entity.Habit.Companion.HabitType.DISPOSABLE
import com.glebalekseevjk.habit.domain.entity.Habit.Companion.HabitType.REGULAR
import com.glebalekseevjk.habit.domain.entity.Habit.Companion.RepeatType
import com.glebalekseevjk.habit.domain.entity.Habit.Companion.RepeatType.SPECIFIC_DAYS
import java.time.LocalDate
import java.time.LocalTime


data class TemplateHabit(
    val id: Int,
    val categoryId: Int,
    val titleStringId: Int,
    val descriptionStringId: Int,
    val iconId: Int,
    val colorRGBA: Int,
    val repeatType: RepeatType,
    val daysOfRepeat: Set<Day>,
    val startExecutionInterval: LocalTime?,
    val endExecutionInterval: LocalTime?,
    val deadline: LocalDate?,
    val habitType: HabitType,
    val targetType: Habit.Companion.TargetType = Habit.Companion.TargetType.OFF,
    val repeatCount: Int?,
    val duration: LocalTime?,
) {
    companion object {
        val templateHabitList = listOf(
            TemplateHabit(
                id = 1,
                categoryId = 1,
                titleStringId = R.string.categories_trend_title_1,
                descriptionStringId = R.string.categories_trend_description_1,
                iconId = R.drawable.ic_categories_trend_1,
                colorRGBA = Color(0xFF0f3057).toArgb(),
                repeatType = SPECIFIC_DAYS,
                daysOfRepeat = setOf(
                    MONDAY,
                    TUESDAY,
                    WEDNESDAY,
                    THURSDAY,
                    FRIDAY,
                    SATURDAY,
                    SUNDAY
                ),
                startExecutionInterval = LocalTime.of(9, 0),
                endExecutionInterval = LocalTime.of(21, 0),
                deadline = null,
                habitType = REGULAR,
                targetType = Habit.Companion.TargetType.DURATION,
                repeatCount = null,
                duration = LocalTime.of(0, 30)
            ),
            TemplateHabit(
                id = 2,
                categoryId = 1,
                titleStringId = R.string.categories_trend_title_2,
                descriptionStringId = R.string.categories_trend_description_2,
                iconId = R.drawable.ic_categories_trend_2,
                colorRGBA = Color(0xFF93b5c6).toArgb(),
                repeatType = SPECIFIC_DAYS,
                daysOfRepeat = setOf(
                    MONDAY,
                    TUESDAY,
                    WEDNESDAY,
                    THURSDAY,
                    FRIDAY,
                    SATURDAY,
                    SUNDAY
                ),
                startExecutionInterval = LocalTime.of(9, 0),
                endExecutionInterval = LocalTime.of(21, 0),
                deadline = null,
                habitType = REGULAR,
                targetType = Habit.Companion.TargetType.OFF,
                repeatCount = null,
                duration = null
            ),
            TemplateHabit(
                id = 3,
                categoryId = 1,
                titleStringId = R.string.categories_trend_title_3,
                descriptionStringId = R.string.categories_trend_description_3,
                iconId = R.drawable.ic_categories_trend_3,
                colorRGBA = Color(0xFF95be6c).toArgb(),
                repeatType = SPECIFIC_DAYS,
                daysOfRepeat = setOf(
                    MONDAY,
                    TUESDAY,
                    WEDNESDAY,
                    THURSDAY,
                    FRIDAY,
                    SATURDAY,
                    SUNDAY
                ),
                startExecutionInterval = LocalTime.of(9, 0),
                endExecutionInterval = LocalTime.of(21, 0),
                deadline = null,
                habitType = REGULAR,
                targetType = Habit.Companion.TargetType.OFF,
                repeatCount = null,
                duration = null
            ),
            TemplateHabit(
                id = 4,
                categoryId = 1,
                titleStringId = R.string.categories_trend_title_4,
                descriptionStringId = R.string.categories_trend_description_4,
                iconId = R.drawable.ic_categories_trend_4,
                colorRGBA = Color(0xFF00adb5).toArgb(),
                repeatType = SPECIFIC_DAYS,
                daysOfRepeat = setOf(
                    SATURDAY,
                    SUNDAY
                ),
                startExecutionInterval = LocalTime.of(9, 0),
                endExecutionInterval = LocalTime.of(21, 0),
                deadline = null,
                habitType = REGULAR,
                targetType = Habit.Companion.TargetType.OFF,
                repeatCount = null,
                duration = null
            ),
            TemplateHabit(
                id = 5,
                categoryId = 1,
                titleStringId = R.string.categories_trend_title_5,
                descriptionStringId = R.string.categories_trend_description_5,
                iconId = R.drawable.ic_categories_trend_5,
                colorRGBA = Color(0xFFf0a500).toArgb(),
                repeatType = SPECIFIC_DAYS,
                daysOfRepeat = setOf(
                    MONDAY,
                    TUESDAY,
                    WEDNESDAY,
                    THURSDAY,
                    FRIDAY,
                    SATURDAY,
                    SUNDAY
                ),
                startExecutionInterval = LocalTime.of(9, 0),
                endExecutionInterval = LocalTime.of(21, 0),
                deadline = null,
                habitType = REGULAR,
                targetType = Habit.Companion.TargetType.OFF,
                repeatCount = null,
                duration = null
            ),
            TemplateHabit(
                id = 6,
                categoryId = 1,
                titleStringId = R.string.categories_trend_title_6,
                descriptionStringId = R.string.categories_trend_description_6,
                iconId = R.drawable.ic_categories_trend_6,
                colorRGBA = Color(0xFF204051).toArgb(),
                repeatType = SPECIFIC_DAYS,
                daysOfRepeat = setOf(),
                startExecutionInterval = LocalTime.of(9, 0),
                endExecutionInterval = LocalTime.of(21, 0),
                deadline = LocalDate.now().plusMonths(2),
                habitType = DISPOSABLE,
                targetType = Habit.Companion.TargetType.OFF,
                repeatCount = null,
                duration = null
            ),
        )
    }
}