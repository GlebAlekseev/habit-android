package com.glebalekseevjk.habit.domain.repository

import com.glebalekseevjk.habit.domain.entity.EventNotification
import com.glebalekseevjk.habit.domain.entity.Habit
import com.glebalekseevjk.habit.utils.Resource

interface EventNotificationRepository {
    suspend fun addEventNotification(eventNotification: EventNotification): Resource<Unit>
    suspend fun doneEventNotification(eventNotificationId: Int): Resource<Unit>
    suspend fun cancelEventNotification(eventNotificationId: Int): Resource<Unit>
    suspend fun removeEventNotification(eventNotificationId: Int): Resource<Unit>
    suspend fun removeEventNotificationForHabit(habitId: Int): Resource<Unit>
    suspend fun getEventNotification(eventNotificationId: Int): Resource<EventNotification>
    suspend fun getEventNotificationListForHabit(habitId: Int): Resource<List<EventNotification>>
    suspend fun getEventNotificationList(): Resource<List<EventNotification>>
}