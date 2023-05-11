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
import java.time.LocalDate
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
            is HistoryViewAction.InitHistory -> initHistory()
        }
    }

    private fun initHistory() {
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
                                    val habitUIList = habitList.map { habit ->
                                        HabitUI(
                                            habit = habit,
                                            eventNotificationList = eventNotificationList.filter { it.habitId == habit.id }
                                        )
                                    }

                                    val dateWeekBefore = LocalDate.now().minusDays(7).atStartOfDay()
                                    val todayDate = LocalDate.now()
                                    val eventNotificationListSortedByDate = eventNotificationList.filter { it.date.isBefore(todayDate.plusDays(1).atStartOfDay()) } .sortedBy { it.date.toLocalDate().toEpochDay() }.reversed()
                                    val completedHabits = habitUIList.filter {
                                        it.eventNotificationList.filter { !it.isDone }.isEmpty()
                                    }

                                    val currentSeries = eventNotificationListSortedByDate.filter { !(!it.isDone && it.date.toLocalDate().isEqual(todayDate)) }.takeWhile { it.date.isAfter(
                                        dateWeekBefore) && it.isDone }.size
                                    val bestSeries = currentSeries
                                    val numberCompletedHabits = completedHabits.size // +
                                    val numberCompletedHabitsForLastWeek = completedHabits.filter {
                                        it.eventNotificationList.lastOrNull()
                                            ?.date
                                            ?.isAfter(dateWeekBefore)
                                            ?: false }.size
                                    val numberHabits = habitUIList.size
                                    val percentageOfCompleted = (numberCompletedHabits / numberHabits.toDouble() * 100).toInt()
                                    val eventsPerDate = eventNotificationListSortedByDate.groupBy { it.date.toLocalDate() }.values
                                    val numberPerfectDays = eventsPerDate.filter { it.all { it.isDone } }.size
                                    val numberPerfectDaysForLastWeek =eventsPerDate.filter { it.filter { it.date.isAfter(
                                        dateWeekBefore) }.all { it.isDone } }.size

                                    viewStates = HistoryViewState.Loaded(
                                        currentSeries,
                                        bestSeries,
                                        numberCompletedHabits,
                                        numberCompletedHabitsForLastWeek,
                                        percentageOfCompleted,
                                        numberHabits,
                                        numberPerfectDays,
                                        numberPerfectDaysForLastWeek,
                                        habitUIList,
                                        if (viewStates is HistoryViewState.Loaded) (viewStates as HistoryViewState.Loaded).lazyRowListState else LazyListState(),
                                        if (viewStates is HistoryViewState.Loaded) (viewStates as HistoryViewState.Loaded).lazyColumnListState else LazyListState(),
                                    )
                                }
                        }
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

sealed class HistoryViewState {
    object Init : HistoryViewState()
    object Loading : HistoryViewState()
    object Failure : HistoryViewState()
    data class Loaded(
        val currentSeries: Int,
        val bestSeries: Int,
        val numberCompletedHabits: Int,
        val numberCompletedHabitsForLastWeek: Int,
        val percentageOfCompleted : Int,
        val numberHabits: Int,
        val numberPerfectDays: Int,
        val numberPerfectDaysForLastWeek: Int,
        val habitList: List<HabitUI>,
        val lazyRowListState: LazyListState,
        val lazyColumnListState: LazyListState
    ) : HistoryViewState()
}

sealed class HistoryViewAction {
    object InitHistory : HistoryViewAction()
}