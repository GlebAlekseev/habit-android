package com.glebalekseevjk.habit.ui.page.today

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
class TodayViewModel @Inject constructor(
    private val habitUseCase: HabitUseCase,
    private val eventNotificationUseCase: EventNotificationUseCase
) : ViewModel() {
    var viewStates by mutableStateOf<TodayViewState>(
        TodayViewState.Init
    )
        private set

    fun dispatch(action: TodayViewAction) {
        when (action) {
            is TodayViewAction.InitToday -> initToday()
            is TodayViewAction.ToggleIsDoneEventNotification -> toggleIsDoneEventNotification(
                action.id
            )
        }
    }

    private fun initToday() {
        viewStates = TodayViewState.Loading

        viewModelScope.launch {
            when (val habits = habitUseCase.getHabitList()) {
                is Resource.Failure -> viewStates = TodayViewState.Failure
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
                                    val habitUIList = habitList.map { habit ->
                                        HabitUI(
                                            habit = habit,
                                            eventNotificationList = eventNotificationList.filter { it.habitId == habit.id }
                                        )
                                    }
                                    if (habitUIList.isEmpty()){
                                        viewStates = TodayViewState.Empty
                                    }else{
                                        viewStates = TodayViewState.Loaded(
                                            habitUIList,
                                            if (viewStates is TodayViewState.Loaded) (viewStates as TodayViewState.Loaded).lazyListState else LazyListState(),
                                        )
                                    }

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

sealed class TodayViewState {
    object Init : TodayViewState()
    object Loading : TodayViewState()
    object Failure : TodayViewState()
    object Empty : TodayViewState()
    data class Loaded(
        val habitList: List<HabitUI>,
        val lazyListState: LazyListState
    ) : TodayViewState()
}

sealed class TodayViewAction {
    object InitToday : TodayViewAction()
    data class ToggleIsDoneEventNotification(val id: Int) : TodayViewAction()
}