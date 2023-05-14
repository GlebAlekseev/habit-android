package com.glebalekseevjk.habit.data.repository

import com.glebalekseevjk.habit.broadcastreceiver.NotificationReceiver
import com.glebalekseevjk.habit.data.scheduler.AlarmScheduler
import com.glebalekseevjk.habit.domain.entity.EventNotification
import com.glebalekseevjk.habit.domain.repository.EventNotificationSchedulerRepository
import com.glebalekseevjk.habit.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EventNotificationSchedulerRepositoryImpl @Inject constructor(
    private val alarmScheduler: AlarmScheduler
) : EventNotificationSchedulerRepository {
    override suspend fun scheduleNotificationEvent(eventNotification: EventNotification): Resource<Unit> =
        withContext(Dispatchers.IO) {
            alarmScheduler.schedule(
                NotificationReceiver::class.java,
                eventNotification.id,
                eventNotification.date
            )
            Resource.Success(Unit)
        }

    override suspend fun scheduleNotificationEvent(eventNotifications: List<EventNotification>): Resource<Unit> {
        eventNotifications.forEach {
            scheduleNotificationEvent(it)
        }
        return Resource.Success(Unit)
    }

    override suspend fun cancelNotificationEvent(eventNotification: EventNotification): Resource<Unit> =
        withContext(Dispatchers.IO) {
            alarmScheduler.cancel(NotificationReceiver::class.java, eventNotification.id)
            Resource.Success(Unit)
        }

    override suspend fun cancelNotificationEvent(eventNotifications: List<EventNotification>): Resource<Unit> {
        eventNotifications.forEach {
            cancelNotificationEvent(it)
        }
        return Resource.Success(Unit)
    }
}