package com.glebalekseevjk.habit.domain.entity

data class Quest(
    val title: String,
    val description: String,
    val id: Int = GENERATE_ID,
    ) {
    companion object {
        const val GENERATE_ID = -1
        val MOCK = Quest("mock title", "mock description")
    }
}