package com.glebalekseevjk.habit.domain.repository

import com.glebalekseevjk.habit.domain.entity.Habit
import com.glebalekseevjk.habit.utils.Resource
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    suspend fun addOrUpdateHabit(habit: Habit): Resource<Unit>
    suspend fun removeHabit(habitId: Int): Resource<Unit>
    suspend fun getHabit(habitId: Int): Resource<Habit>
    suspend fun getHabitList(): Resource<Flow<List<Habit>>>
}