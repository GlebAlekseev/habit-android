package com.glebalekseevjk.habit.ui.page.history

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glebalekseevjk.habit.domain.entity.EventNotification
import com.glebalekseevjk.habit.domain.entity.Habit
import com.glebalekseevjk.habit.domain.interactor.EventNotificationUseCase
import com.glebalekseevjk.habit.domain.interactor.HabitUseCase
import com.glebalekseevjk.habit.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val habitUseCase: HabitUseCase,
    private val eventNotificationUseCase: EventNotificationUseCase
) : ViewModel() {
    var viewStates by mutableStateOf<HistoryViewState>(
        HistoryViewState.Init
    )
        private set

    fun dispatch(action: HistoryViewAction) {
        when (action) {
            is HistoryViewAction.InitHistory -> initHabits()
            is HistoryViewAction.ToggleIsDoneEventNotification -> toggleIsDoneEventNotification(
                action.id
            )
        }
    }

    private fun initHabits() {
        viewStates = HistoryViewState.Loading

        viewModelScope.launch {
            when (val habits = habitUseCase.getHabitList()) {
                is Resource.Failure -> viewStates = HistoryViewState.Failure
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
                                    viewStates
                                    viewStates = HistoryViewState.Loaded(
                                        habitList.map { habit ->
                                            HabitUI(
                                                habit = habit,
                                                eventNotificationList = eventNotificationList.filter { it.habitId == habit.id }
                                            )
                                        },
                                        if (viewStates is HistoryViewState.Loaded) (viewStates as HistoryViewState.Loaded).lazyListState else LazyListState()

                                    )
                                }
                        }
                    }

                }
            }
        }
    }

    private fun toggleIsDoneEventNotification(id: Int) {
        viewModelScope.launch {
            eventNotificationUseCase.toggleIsDoneEventNotification(id)
        }
    }
}

data class HabitUI(
    val habit: Habit,
    val eventNotificationList: List<EventNotification>
)

sealed class HistoryViewState {
    object Init : HistoryViewState()
    object Loading : HistoryViewState()
    object Failure : HistoryViewState()
    data class Loaded(
        val habitList: List<HabitUI>,
        val lazyListState: LazyListState
    ) : HistoryViewState()
}

sealed class HistoryViewAction {
    object InitHistory : HistoryViewAction()
    data class ToggleIsDoneEventNotification(val id: Int) : HistoryViewAction()
}