package com.glebalekseevjk.habit.utils

sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Failure<T>(val throwable: Throwable) : Resource<T>()
}