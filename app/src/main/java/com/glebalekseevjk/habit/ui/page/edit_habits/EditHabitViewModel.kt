package com.glebalekseevjk.habit.ui.page.edit_habits


import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.glebalekseevjk.habit.domain.entity.Habit
import com.glebalekseevjk.habit.domain.entity.TemplateHabit
import com.glebalekseevjk.habit.domain.interactor.HabitUseCase
import com.glebalekseevjk.habit.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class EditHabitViewModel @Inject constructor(
    private val app: Application,
    private val habitUseCase: HabitUseCase
) : AndroidViewModel(app) {
    var viewStates by mutableStateOf<EditHabitViewState>(
        EditHabitViewState.Init
    )
        private set

    fun dispatch(action: EditHabitViewAction) {
        when (action) {
            is EditHabitViewAction.InitParams -> initParams(action.habitId, action.templateHabitId)
            is EditHabitViewAction.SetColorRGBA -> setColorRGBA(action.ColorRGBA)
            is EditHabitViewAction.SetDaysOfRepeat -> setDaysOfRepeat(action.daysOfRepeat)
            is EditHabitViewAction.SetDeadline -> setDeadline(action.deadline)
            is EditHabitViewAction.SetDuration -> setDuration(action.duration)
            is EditHabitViewAction.SetEndExecutionInterval -> setEndExecutionInterval(action.endExecutionInterval)
            is EditHabitViewAction.SetIconId -> setIconId(action.iconId)
            is EditHabitViewAction.SetRepeatCount -> setRepeatCount(action.repeatCount)
            is EditHabitViewAction.SetStartExecutionInterval -> setStartExecutionInterval(action.startExecutionInterval)
            is EditHabitViewAction.SetTargetType -> setTargetType(action.targetType)
            is EditHabitViewAction.SetTitle -> setTitle(action.title)
            EditHabitViewAction.SaveHabit -> saveHabit()
        }
    }

    private fun saveHabit() {
        (viewStates as? EditHabitViewState.Loaded)
            ?.let {
                viewModelScope.launch {
                    habitUseCase.addOrUpdateHabit(it.habit)
                }
            }
    }

    private fun initParams(habitId: Int?, templateHabitId: Int?) {
        viewStates = EditHabitViewState.Loading
        when {
            habitId != null -> {
                viewModelScope.launch {
                    viewStates = when (val habit = habitUseCase.getHabit(habitId)) {
                        is Resource.Failure -> EditHabitViewState.Failure
                        is Resource.Success -> EditHabitViewState.Loaded(habit.data)
                    }
                }
            }
            templateHabitId != null -> {
                viewModelScope.launch {
                    val templateHabit =
                        TemplateHabit.templateHabitList.find { it.id == templateHabitId }
                    viewStates = templateHabit?.let {
                        EditHabitViewState.Loaded(
                            Habit(
                                Habit.UNDEFINED_ID,
                                getString(it.titleStringId),
                                getString(it.descriptionStringId),
                                it.iconId,
                                it.colorRGBA,
                                it.repeatType,
                                it.daysOfRepeat,
                                it.startExecutionInterval,
                                it.endExecutionInterval,
                                it.deadline ?: LocalDate.now().plusMonths(1),
                                it.habitType,
                                it.targetType,
                                it.repeatCount,
                                it.duration,
                            )
                        )
                    } ?: EditHabitViewState.Failure
                }
            }
            else -> viewStates = EditHabitViewState.Failure
        }
    }

    private fun setColorRGBA(colorRGBA: Int) {
        val state = viewStates as EditHabitViewState.Loaded
        viewStates = state.copy(
            habit = state.habit.copy(
                colorRGBA = colorRGBA
            )
        )
    }

    private fun setDaysOfRepeat(daysOfRepeat: Set<Habit.Companion.Day>) {
        val state = viewStates as EditHabitViewState.Loaded
        viewStates = state.copy(
            habit = state.habit.copy(
                daysOfRepeat = daysOfRepeat
            )
        )
    }

    private fun setDeadline(deadline: LocalDate) {
        val state = viewStates as EditHabitViewState.Loaded
        viewStates = state.copy(
            habit = state.habit.copy(
                deadline = deadline
            )
        )
    }

    private fun setDuration(duration: LocalTime) {
        val state = viewStates as EditHabitViewState.Loaded
        viewStates = state.copy(
            habit = state.habit.copy(
                duration = duration
            )
        )
    }

    private fun setEndExecutionInterval(endExecutionInterval: LocalTime) {
        val state = viewStates as EditHabitViewState.Loaded
        viewStates = state.copy(
            habit = state.habit.copy(
                endExecutionInterval = endExecutionInterval
            )
        )
    }

    private fun setIconId(iconId: Int) {
        val state = viewStates as EditHabitViewState.Loaded
        viewStates = state.copy(
            habit = state.habit.copy(
                iconId = iconId
            )
        )
    }

    private fun setRepeatCount(repeatCount: Int) {
        val state = viewStates as EditHabitViewState.Loaded
        viewStates = state.copy(
            habit = state.habit.copy(
                repeatCount = repeatCount
            )
        )
    }

    private fun setStartExecutionInterval(startExecutionInterval: LocalTime) {
        val state = viewStates as EditHabitViewState.Loaded
        viewStates = state.copy(
            habit = state.habit.copy(
                startExecutionInterval = startExecutionInterval
            )
        )
    }

    private fun setTargetType(targetType: Habit.Companion.TargetType) {
        val state = viewStates as EditHabitViewState.Loaded
        viewStates = state.copy(
            habit = state.habit.copy(
                targetType = targetType
            )
        )
    }

    private fun setTitle(title: String) {
        val state = viewStates as EditHabitViewState.Loaded
        viewStates = state.copy(
            habit = state.habit.copy(
                title = title
            )
        )
    }

}

sealed class EditHabitViewState {
    object Init : EditHabitViewState()
    object Loading : EditHabitViewState()
    object Failure : EditHabitViewState()
    data class Loaded(val habit: Habit) : EditHabitViewState()
}

sealed class EditHabitViewAction {
    data class InitParams(val habitId: Int?, val templateHabitId: Int?) : EditHabitViewAction()
    data class SetTitle(val title: String) : EditHabitViewAction()
    data class SetIconId(val iconId: Int) : EditHabitViewAction()
    data class SetColorRGBA(val ColorRGBA: Int) : EditHabitViewAction()
    data class SetDaysOfRepeat(val daysOfRepeat: Set<Habit.Companion.Day>) : EditHabitViewAction()
    data class SetStartExecutionInterval(val startExecutionInterval: LocalTime) :
        EditHabitViewAction()

    data class SetEndExecutionInterval(val endExecutionInterval: LocalTime) : EditHabitViewAction()
    data class SetDeadline(val deadline: LocalDate) : EditHabitViewAction()
    data class SetTargetType(val targetType: Habit.Companion.TargetType) : EditHabitViewAction()
    data class SetRepeatCount(val repeatCount: Int) : EditHabitViewAction()
    data class SetDuration(val duration: LocalTime) : EditHabitViewAction()
    object SaveHabit : EditHabitViewAction()
}

private fun AndroidViewModel.getString(stringId: Int): String =
    this.getApplication<Application>().resources.getString(stringId)
