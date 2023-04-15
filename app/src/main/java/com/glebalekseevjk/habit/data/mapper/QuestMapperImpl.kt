package com.glebalekseevjk.habit.data.mapper

import com.glebalekseevjk.habit.data.local.model.QuestDbModel
import com.glebalekseevjk.habit.domain.entity.Quest
import com.glebalekseevjk.habit.domain.mapper.Mapper
import javax.inject.Inject

class QuestMapperImpl @Inject constructor(): Mapper<Quest, QuestDbModel> {
    override fun mapItemToDbModel(item: Quest): QuestDbModel {
        TODO("Not yet implemented")
    }

    override fun mapDbModelToItem(dbModel: QuestDbModel): Quest {
        TODO("Not yet implemented")
    }
}