package com.glebalekseevjk.habit.di.module

import com.glebalekseevjk.habit.data.repository.EventNotificationRepositoryImpl
import com.glebalekseevjk.habit.data.repository.EventNotificationSchedulerRepositoryImpl
import com.glebalekseevjk.habit.data.repository.HabitRepositoryImpl
import com.glebalekseevjk.habit.data.repository.SettingsRepositoryImpl
import com.glebalekseevjk.habit.domain.repository.EventNotificationRepository
import com.glebalekseevjk.habit.domain.repository.EventNotificationSchedulerRepository
import com.glebalekseevjk.habit.domain.repository.HabitRepository
import com.glebalekseevjk.habit.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {
    @Binds
    fun bindHabitRepository(habitRepositoryImpl: HabitRepositoryImpl): HabitRepository

    @Binds
    fun bindEventNotificationRepository(eventNotificationRepositoryImpl: EventNotificationRepositoryImpl): EventNotificationRepository

    @Binds
    fun bindEventNotificationSchedulerRepository(eventNotificationSchedulerRepositoryImpl: EventNotificationSchedulerRepositoryImpl): EventNotificationSchedulerRepository

    @Binds
    fun bindSettingsRepository(settingsRepositoryImpl: SettingsRepositoryImpl): SettingsRepository
}