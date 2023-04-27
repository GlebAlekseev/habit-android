package com.glebalekseevjk.habit.data.repository

import com.glebalekseevjk.habit.data.local.dao.HabitDao
import com.glebalekseevjk.habit.data.local.model.HabitDbModel
import com.glebalekseevjk.habit.domain.entity.Habit
import com.glebalekseevjk.habit.domain.mapper.Mapper
import com.glebalekseevjk.habit.domain.repository.HabitRepository
import com.glebalekseevjk.habit.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HabitRepositoryImpl @Inject constructor(
    private val habitDao: HabitDao,
    private val mapper: Mapper<Habit, HabitDbModel>
) : HabitRepository {
    override suspend fun addOrUpdateHabit(habit: Habit): Resource<Unit> =
        withContext(Dispatchers.IO) {
            val item = mapper.mapItemToDbModel(habit)
            habitDao.insert(item)
            Resource.Success(Unit)
        }

    override suspend fun removeHabit(habitId: Int): Resource<Unit> =
        withContext(Dispatchers.IO) {
            try {
                habitDao.delete(habitId)
                Resource.Success(Unit)
            } catch (e: Exception) {
                Resource.Failure(NoSuchElementException())
            }
        }

    override suspend fun getHabit(habitId: Int): Resource<Habit> = withContext(Dispatchers.IO) {
        val result = habitDao.get(habitId)
        if (result == null) {
            Resource.Failure(NoSuchElementException())
        } else {
            Resource.Success(mapper.mapDbModelToItem(result))
        }
    }

    override suspend fun getHabitList(): Resource<List<Habit>> = withContext(Dispatchers.IO) {
        val result = habitDao.getAll()
        Resource.Success(result.map { mapper.mapDbModelToItem(it) })
    }
}