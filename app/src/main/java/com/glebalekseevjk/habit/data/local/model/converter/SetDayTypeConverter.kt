package com.glebalekseevjk.habit.data.local.model.converter

import androidx.room.TypeConverter
import com.glebalekseevjk.habit.domain.entity.Habit.Companion.Day

object SetDayTypeConverter {
    @TypeConverter
    fun toSetDay(value: String): Set<Day> {
        val result = mutableSetOf<Day>()
        value.forEach {
            when (it) {
                '1' -> result.add(Day.MONDAY)
                '2' -> result.add(Day.TUESDAY)
                '3' -> result.add(Day.WEDNESDAY)
                '4' -> result.add(Day.THURSDAY)
                '5' -> result.add(Day.FRIDAY)
                '6' -> result.add(Day.SATURDAY)
                '7' -> result.add(Day.SUNDAY)
            }
        }
        return result
    }

    @TypeConverter
    fun fromListDay(setDay: Set<Day>): String {
        val result = StringBuilder()
        setDay.forEach {
            when (it) {
                Day.MONDAY -> result.append("1")
                Day.TUESDAY -> result.append("2")
                Day.WEDNESDAY -> result.append("3")
                Day.THURSDAY -> result.append("4")
                Day.FRIDAY -> result.append("5")
                Day.SATURDAY -> result.append("6")
                Day.SUNDAY -> result.append("7")
            }

        }
        return result.toString()
    }
}
