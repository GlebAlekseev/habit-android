package com.glebalekseevjk.habit.domain.entity

import java.time.LocalDateTime


data class EventNotification(
    val id: Int = UNDEFINED_ID,
    val habitId: Int,
    val date: LocalDateTime,
    val isDone: Boolean
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}
