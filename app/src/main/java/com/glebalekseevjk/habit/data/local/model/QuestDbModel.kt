package com.glebalekseevjk.habit.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QuestDbModel(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String
)