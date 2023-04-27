package com.glebalekseevjk.habit.data.repository

import com.glebalekseevjk.habit.data.local.dao.EventNotificationDao
import com.glebalekseevjk.habit.data.local.model.EventNotificationDbModel
import com.glebalekseevjk.habit.domain.entity.EventNotification
import com.glebalekseevjk.habit.domain.mapper.Mapper
import com.glebalekseevjk.habit.domain.repository.EventNotificationRepository
import com.glebalekseevjk.habit.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EventNotificationRepositoryImpl @Inject constructor(
    private val eventNotificationDao: EventNotificationDao,
    private val mapper: Mapper<EventNotification, EventNotificationDbModel>
) : EventNotificationRepository {
    override suspend fun addEventNotification(eventNotification: EventNotification): Resource<Unit> =
        withContext(Dispatchers.IO) {
            val item = mapper.mapItemToDbModel(eventNotification)
            eventNotificationDao.insert(item)
            Resource.Success(Unit)
        }

    override suspend fun doneEventNotification(eventNotificationId: Int): Resource<Unit> =
        withContext(Dispatchers.IO) {
            val result = eventNotificationDao.get(eventNotificationId)
            if (result == null) {
                Resource.Failure(NoSuchElementException())
            } else {
                eventNotificationDao.insert(
                    result.copy(
                        isDone = true
                    )
                )
                Resource.Success(Unit)
            }
        }

    override suspend fun cancelEventNotification(eventNotificationId: Int): Resource<Unit> =
        withContext(Dispatchers.IO) {
            val result = eventNotificationDao.get(eventNotificationId)
            if (result == null) {
                Resource.Failure(NoSuchElementException())
            } else {
                eventNotificationDao.insert(
                    result.copy(
                        isDone = false
                    )
                )
                Resource.Success(Unit)
            }
        }

    override suspend fun removeEventNotification(eventNotificationId: Int): Resource<Unit> =
        withContext(Dispatchers.IO) {
            try {
                eventNotificationDao.delete(eventNotificationId)
                Resource.Success(Unit)
            } catch (e: Exception) {
                Resource.Failure(NoSuchElementException())
            }
        }

    override suspend fun removeEventNotificationForHabit(habitId: Int): Resource<Unit> =
        withContext(Dispatchers.IO) {
            try {
                eventNotificationDao.deleteForHabit(habitId)
                Resource.Success(Unit)
            } catch (e: Exception) {
                Resource.Failure(NoSuchElementException())
            }
        }

    override suspend fun getEventNotification(eventNotificationId: Int): Resource<EventNotification> =
        withContext(Dispatchers.IO) {
            val result = eventNotificationDao.get(eventNotificationId)
            if (result == null) {
                Resource.Failure(NoSuchElementException())
            } else {
                Resource.Success(mapper.mapDbModelToItem(result))
            }
        }

    override suspend fun getEventNotificationListForHabit(habitId: Int): Resource<List<EventNotification>> =
        withContext(Dispatchers.IO) {
            val result = eventNotificationDao.getForHabit(habitId)
            Resource.Success(result.map { mapper.mapDbModelToItem(it) })
        }

    override suspend fun getEventNotificationList(): Resource<List<EventNotification>> =
        withContext(Dispatchers.IO) {
            val result = eventNotificationDao.getAll()
            Resource.Success(result.map { mapper.mapDbModelToItem(it) })
        }
}