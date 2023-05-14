package com.glebalekseevjk.habit.data.repository

import androidx.lifecycle.asFlow
import com.glebalekseevjk.habit.data.local.dao.EventNotificationDao
import com.glebalekseevjk.habit.data.local.model.EventNotificationDbModel
import com.glebalekseevjk.habit.domain.entity.EventNotification
import com.glebalekseevjk.habit.domain.entity.EventNotification.Companion.UNDEFINED_ID
import com.glebalekseevjk.habit.domain.entity.Habit
import com.glebalekseevjk.habit.domain.mapper.Mapper
import com.glebalekseevjk.habit.domain.repository.EventNotificationRepository
import com.glebalekseevjk.habit.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalTime
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

    override suspend fun toggleIsDoneEventNotification(
        eventNotification: EventNotification,
    ): Resource<Boolean> =
        withContext(Dispatchers.IO) {
            val isDone = !eventNotification.isDone
            eventNotificationDao.insert(
                mapper.mapItemToDbModel(eventNotification).copy(
                    isDone = isDone
                )
            )
            return@withContext Resource.Success(isDone)
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

    override suspend fun getEventNotificationList(): Resource<Flow<List<EventNotification>>> {
        val result = eventNotificationDao.getAllLiveData()
        val flow = result.asFlow().map { it.map { mapper.mapDbModelToItem(it) } }
        return Resource.Success(flow)
    }


    override suspend fun addEventNotificationsLaterThanDateInclusiveForHabit(
        habit: Habit,
        date: LocalDate
    ): Resource<List<EventNotification>> =
        withContext(Dispatchers.IO) {
            val isDateInclusive =
                eventNotificationDao.getForHabit(habit.id).filter { it.date.toLocalDate() == date }.isEmpty()
            val start = if (isDateInclusive) date else date.plusDays(1)
            val end = habit.deadline
                ?: return@withContext Resource.Failure(RuntimeException("deadline is null"))
            if (end.toEpochDay() < start.toEpochDay()) return@withContext Resource.Failure(
                RuntimeException("deadline < start date")
            )

            val eventNotificationList = mutableListOf<EventNotification>()
            val startIntervalHour = habit.startExecutionInterval?.hour ?: 9
            val startIntervalMinute = habit.startExecutionInterval?.minute ?: 0
            val endIntervalHour = habit.endExecutionInterval?.hour ?: 21
            val endIntervalMinute = habit.endExecutionInterval?.minute ?: 0

            if (LocalTime.of(startIntervalHour, startIntervalMinute).toSecondOfDay() > LocalTime.of(
                    endIntervalHour,
                    endIntervalMinute
                ).toSecondOfDay()
            )
                return@withContext Resource.Failure(RuntimeException("bad time interval: start > end"))

            val targetWeekOfDaysSet = habit.daysOfRepeat.map {
                when (it) {
                    Habit.Companion.Day.MONDAY -> 1
                    Habit.Companion.Day.TUESDAY -> 2
                    Habit.Companion.Day.WEDNESDAY -> 3
                    Habit.Companion.Day.THURSDAY -> 4
                    Habit.Companion.Day.FRIDAY -> 5
                    Habit.Companion.Day.SATURDAY -> 6
                    Habit.Companion.Day.SUNDAY -> 7
                }
            }
            val range = start.toEpochDay()..end.toEpochDay()
            var i = start.dayOfWeek.value - 1
            for (epochDay in range) {
                val currentDayOfWeek = i % 7 + 1
                if (currentDayOfWeek in targetWeekOfDaysSet || epochDay == range.last) {
                    val targetDate = LocalDate.ofEpochDay(epochDay)

                    val dateResult = targetDate
                        .atStartOfDay()
                        .plusHours((startIntervalHour..endIntervalHour).random().toLong())
                        .plusMinutes(
                            (startIntervalMinute..endIntervalMinute).random().toLong()
                        )
                    eventNotificationList.add(
                        EventNotification(
                            id = UNDEFINED_ID,
                            habitId = habit.id,
                            date = dateResult,
                            isDone = false
                        )
                    )
                }
                i++
            }

            eventNotificationDao.insertAll(*eventNotificationList.map {
                mapper.mapItemToDbModel(it)
            }.toTypedArray())

            return@withContext Resource.Success(eventNotificationDao.getAll().map { mapper.mapDbModelToItem(it) }.filter { it.habitId == habit.id })
        }

    override suspend fun removeEventNotificationsLaterThanDateInclusiveWhereIsDoneFalseForHabit(
        habitId: Int,
        date: LocalDate
    ): Resource<List<EventNotification>> =
        withContext(Dispatchers.IO) {
            val eventNotificationList = eventNotificationDao
                .getLaterThanDateInclusiveWhereIsDoneFalseForHabit(habitId, date.toEpochDay())
                .map { mapper.mapDbModelToItem(it) }
            eventNotificationDao
                .deleteLaterThanDateInclusiveWhereIsDoneFalseForHabit(habitId, date.toEpochDay())
            Resource.Success(eventNotificationList)
        }
}