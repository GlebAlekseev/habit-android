package com.glebalekseevjk.habit.ui.page.add_habits

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.glebalekseevjk.habit.domain.entity.Category
import com.glebalekseevjk.habit.domain.entity.Habit.Companion.HabitType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddHabitViewModel @Inject constructor() : ViewModel() {
    var viewStates by mutableStateOf(
        AddHabitViewState(
            habitType = HabitType.REGULAR,
            categoryList = Category.categoryList
        )
    )
        private set

    fun dispatch(action: AddHabitViewAction) {
        when (action) {
            is AddHabitViewAction.SetHabitType -> setHabitType(action.habitType)
        }
    }

    private fun setHabitType(habitType: HabitType) {
        viewStates = viewStates.copy(habitType = habitType)
    }
}

data class AddHabitViewState(
    val habitType: HabitType,
    val categoryList: List<Category>
)

sealed class AddHabitViewAction {
    data class SetHabitType(val habitType: HabitType) : AddHabitViewAction()
}