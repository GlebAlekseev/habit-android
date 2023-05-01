package com.glebalekseevjk.habit.di.module

import android.content.Context
import androidx.room.Room
import com.glebalekseevjk.habit.data.local.AppDatabase
import com.glebalekseevjk.habit.data.local.dao.EventNotificationDao
import com.glebalekseevjk.habit.data.local.dao.HabitDao
import com.glebalekseevjk.habit.data.local.model.EventNotificationDbModel
import com.glebalekseevjk.habit.data.local.model.HabitDbModel
import com.glebalekseevjk.habit.data.mapper.EventNotificationMapperImpl
import com.glebalekseevjk.habit.data.mapper.HabitMapperImpl
import com.glebalekseevjk.habit.domain.entity.EventNotification
import com.glebalekseevjk.habit.domain.entity.Habit
import com.glebalekseevjk.habit.domain.mapper.Mapper
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
interface LocalStorageModule {
    @Binds
    fun provideMapperHabit(habitMapperImpl: HabitMapperImpl ): Mapper<Habit, HabitDbModel>

    @Binds
    fun provideMapperEventNotification(eventNotificationMapperImpl: EventNotificationMapperImpl): Mapper<EventNotification, EventNotificationDbModel>


    companion object {
        @Singleton
        @Provides
        fun provideAppDataBase(@ApplicationContext context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                AppDatabase.DATABASE_NAME
            ).build()
        }

        @Singleton
        @Provides
        fun provideHabitDao(appDatabase: AppDatabase): HabitDao = appDatabase.habitDao()

        @Singleton
        @Provides
        fun provideEventNotificationDao(appDatabase: AppDatabase): EventNotificationDao = appDatabase.eventNotificationDao()
    }
}