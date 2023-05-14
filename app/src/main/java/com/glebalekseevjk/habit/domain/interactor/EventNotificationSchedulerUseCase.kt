package com.glebalekseevjk.habit.domain.interactor

import com.glebalekseevjk.habit.domain.entity.EventNotification
import com.glebalekseevjk.habit.domain.repository.EventNotificationSchedulerRepository
import javax.inject.Inject

class EventNotificationSchedulerUseCase @Inject constructor(
    private val eventNotificationSchedulerRepository: EventNotificationSchedulerRepository
) {
    suspend fun scheduleNotificationEvent(eventNotification: EventNotification) =
        eventNotificationSchedulerRepository.scheduleNotificationEvent(eventNotification)

    suspend fun scheduleNotificationEvent(eventNotifications: List<EventNotification>) =
        eventNotificationSchedulerRepository.scheduleNotificationEvent(eventNotifications)

    suspend fun cancelNotificationEvent(eventNotification: EventNotification) =
        eventNotificationSchedulerRepository.cancelNotificationEvent(eventNotification)

    suspend fun cancelNotificationEvent(eventNotifications: List<EventNotification>) =
        eventNotificationSchedulerRepository.cancelNotificationEvent(eventNotifications)

}