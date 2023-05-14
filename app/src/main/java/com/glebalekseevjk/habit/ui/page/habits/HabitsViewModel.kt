package com.glebalekseevjk.habit.ui.page.habits

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glebalekseevjk.habit.domain.entity.EventNotification
import com.glebalekseevjk.habit.domain.entity.Habit
import com.glebalekseevjk.habit.domain.interactor.EventNotificationSchedulerUseCase
import com.glebalekseevjk.habit.domain.interactor.EventNotificationUseCase
import com.glebalekseevjk.habit.domain.interactor.HabitUseCase
import com.glebalekseevjk.habit.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitsViewModel @Inject constructor(
    private val habitUseCase: HabitUseCase,
    private val eventNotificationUseCase: EventNotificationUseCase,
    private val eventNotificationSchedulerUseCase: EventNotificationSchedulerUseCase
) : ViewModel() {
    var viewStates by mutableStateOf<HabitsViewState>(
        HabitsViewState.Init
    )
        private set

    fun dispatch(action: HabitsViewAction) {
        when (action) {
            is HabitsViewAction.InitHabits -> initHabits()
            is HabitsViewAction.ToggleIsDoneEventNotification -> toggleIsDoneEventNotification(
                action.eventNotification
            )
        }
    }

    private fun initHabits() {
        viewStates = HabitsViewState.Loading

        viewModelScope.launch {
            when (val habits = habitUseCase.getHabitList()) {
                is Resource.Failure -> viewStates = HabitsViewState.Failure
                is Resource.Success -> {
                    val eventNotifications = eventNotificationUseCase.getEventNotificationList()
                    when (eventNotifications) {
                        is Resource.Failure -> {}
                        is Resource.Success -> {
                            eventNotifications.data
                                .combine(habits.data) { a, b -> Pair(a, b) }
                                .collect {
                                    val habitList = it.second
                                    val eventNotificationList = it.first

                                    if (habitList.isEmpty()) {
                                        viewStates = HabitsViewState.Empty
                                    } else {
                                        viewStates = HabitsViewState.Loaded(
                                            habitList.map { habit ->
                                                HabitUI(
                                                    habit = habit,
                                                    eventNotificationList = eventNotificationList.filter { it.habitId == habit.id }
                                                )
                                            },
                                            if (viewStates is HabitsViewState.Loaded) (viewStates as HabitsViewState.Loaded).lazyListState else LazyListState()

                                        )
                                    }
                                }
                        }
                    }

                }
            }
        }
    }

    private fun toggleIsDoneEventNotification(eventNotification: EventNotification) {
        viewModelScope.launch {
            eventNotificationUseCase.toggleIsDoneEventNotification(
                eventNotification = eventNotification,
                onInitNotification = {
                    CoroutineScope(Dispatchers.IO).launch {
                        eventNotificationSchedulerUseCase.scheduleNotificationEvent(it)
                    }
                },
                onCancelNotification = {
                    CoroutineScope(Dispatchers.IO).launch {
                        eventNotificationSchedulerUseCase.cancelNotificationEvent(it)
                    }
                }
            )
        }
    }
}

data class HabitUI(
    val habit: Habit,
    val eventNotificationList: List<EventNotification>
)

sealed class HabitsViewState {
    object Init : HabitsViewState()
    object Loading : HabitsViewState()
    object Failure : HabitsViewState()
    object Empty : HabitsViewState()
    data class Loaded(
        val habitList: List<HabitUI>,
        val lazyListState: LazyListState
    ) : HabitsViewState()
}

sealed class HabitsViewAction {
    object InitHabits : HabitsViewAction()
    data class ToggleIsDoneEventNotification(val eventNotification: EventNotification) : HabitsViewAction()
}