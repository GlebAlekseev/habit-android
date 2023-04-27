package com.glebalekseevjk.habit.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.glebalekseevjk.habit.data.local.model.EventNotificationDbModel

@Dao
interface EventNotificationDao {
    @Query("SELECT * FROM EventNotificationDbModel WHERE id = :eventNotificationId")
    suspend fun get(eventNotificationId: Int): EventNotificationDbModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(eventNotificationDbModel: EventNotificationDbModel)

    @Query("DELETE FROM EventNotificationDbModel WHERE id = :eventNotificationId")
    suspend fun delete(eventNotificationId: Int)

    @Query("DELETE FROM EventNotificationDbModel WHERE habitId = :habitId")
    suspend fun deleteForHabit(habitId: Int)

    @Query("SELECT * FROM EventNotificationDbModel WHERE habitId = :habitId ORDER BY created_at ASC")
    suspend fun getForHabit(habitId: Int): List<EventNotificationDbModel>

    @Query("SELECT * FROM EventNotificationDbModel ORDER BY created_at ASC")
    suspend fun getAll(): List<EventNotificationDbModel>
}