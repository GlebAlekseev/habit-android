package com.glebalekseevjk.habit.data.mapper

import com.glebalekseevjk.habit.data.local.model.EventNotificationDbModel
import com.glebalekseevjk.habit.domain.entity.EventNotification
import com.glebalekseevjk.habit.domain.mapper.Mapper
import javax.inject.Inject

class EventNotificationMapperImpl @Inject constructor() :
    Mapper<EventNotification, EventNotificationDbModel> {
    override fun mapItemToDbModel(item: EventNotification): EventNotificationDbModel =
        EventNotificationDbModel(
            item.id,
            item.habitId,
            item.date,
            item.isDone,
        )

    override fun mapDbModelToItem(dbModel: EventNotificationDbModel): EventNotification =
        EventNotification(
            dbModel.id,
            dbModel.habitId,
            dbModel.date,
            dbModel.isDone,
        )
}