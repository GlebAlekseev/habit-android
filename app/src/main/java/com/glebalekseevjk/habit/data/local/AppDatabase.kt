package com.glebalekseevjk.habit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.glebalekseevjk.habit.data.local.dao.EventNotificationDao
import com.glebalekseevjk.habit.data.local.dao.HabitDao
import com.glebalekseevjk.habit.data.local.model.EventNotificationDbModel
import com.glebalekseevjk.habit.data.local.model.HabitDbModel
import com.glebalekseevjk.habit.data.local.model.converter.*

@Database(entities = [HabitDbModel::class, EventNotificationDbModel::class], version = 1)
@TypeConverters(
    HabitTypeConverter::class,
    LocalDateTimeTypeConverter::class,
    LocalDateTypeConverter::class,
    LocalTimeTypeConverter::class,
    RepeatTypeConverter::class,
    SetDayTypeConverter::class,
    TargetTypeConverter::class,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
    abstract fun eventNotificationDao(): EventNotificationDao

    companion object {
        const val DATABASE_NAME = "habit-database"
    }
}