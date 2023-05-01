package com.glebalekseevjk.habit.ui.page.habits

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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitsViewModel @Inject constructor(
    private val habitUseCase: HabitUseCase,
    private val eventNotificationUseCase: EventNotificationUseCase
) : ViewModel() {
    var viewStates by mutableStateOf<HabitsViewState>(
        HabitsViewState.Init
    )
        private set

    fun dispatch(action: HabitsViewAction) {
        when (action) {
            is HabitsViewAction.InitHabits -> initHabits()
        }
    }

    private fun initHabits() {
        viewStates = HabitsViewState.Loading
        viewModelScope.launch {
            when (val habits = habitUseCase.getHabitList()) {
                is Resource.Failure -> viewStates = HabitsViewState.Failure
                is Resource.Success -> {
                    habits.data.collect {
                        viewStates = HabitsViewState.Loaded(
                            it.map {
                                HabitUI(
                                    habit = it,
                                    eventNotificationList = when (val eventNotificationList =
                                        eventNotificationUseCase.getEventNotificationListForHabit(it.id)) {
                                        is Resource.Failure -> listOf()
                                        is Resource.Success -> eventNotificationList.data
                                    }
                                )
                            },
                            LazyListState()
                        )
                    }
                }
            }
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
    data class Loaded(
        val habitList: List<HabitUI>,
        val lazyListState: LazyListState
    ) : HabitsViewState()
}

sealed class HabitsViewAction {
    object InitHabits : HabitsViewAction()
}