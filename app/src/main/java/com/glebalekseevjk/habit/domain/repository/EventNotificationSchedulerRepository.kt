package com.glebalekseevjk.habit.domain.repository

import com.glebalekseevjk.habit.domain.entity.EventNotification
import com.glebalekseevjk.habit.domain.entity.Habit
import com.glebalekseevjk.habit.utils.Resource
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface EventNotificationSchedulerRepository {
    suspend fun scheduleNotificationEvent(eventNotification: EventNotification): Resource<Unit>
    suspend fun scheduleNotificationEvent(eventNotifications: List<EventNotification>): Resource<Unit>
    suspend fun cancelNotificationEvent(eventNotification: EventNotification): Resource<Unit>
    suspend fun cancelNotificationEvent(eventNotifications: List<EventNotification>): Resource<Unit>
}