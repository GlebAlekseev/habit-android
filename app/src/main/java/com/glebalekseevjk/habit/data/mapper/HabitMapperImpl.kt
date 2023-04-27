package com.glebalekseevjk.habit.data.mapper

import com.glebalekseevjk.habit.data.local.model.HabitDbModel
import com.glebalekseevjk.habit.domain.entity.Habit
import com.glebalekseevjk.habit.domain.mapper.Mapper
import javax.inject.Inject

class HabitMapperImpl @Inject constructor() : Mapper<Habit, HabitDbModel> {
    override fun mapItemToDbModel(item: Habit): HabitDbModel = HabitDbModel(
        item.id,
        item.title,
        item.description,
        item.iconId,
        item.colorRGBA,
        item.repeatType,
        item.daysOfRepeat,
        item.startExecutionInterval,
        item.endExecutionInterval,
        item.deadline,
        item.habitType,
        item.targetType,
        item.repeatCount,
        item.duration,
    )

    override fun mapDbModelToItem(dbModel: HabitDbModel): Habit = Habit(
        dbModel.id,
        dbModel.title,
        dbModel.description,
        dbModel.iconId,
        dbModel.colorRGBA,
        dbModel.repeatType,
        dbModel.daysOfRepeat,
        dbModel.startExecutionInterval,
        dbModel.endExecutionInterval,
        dbModel.deadline,
        dbModel.habitType,
        dbModel.targetType,
        dbModel.repeatCount,
        dbModel.duration,
    )
}