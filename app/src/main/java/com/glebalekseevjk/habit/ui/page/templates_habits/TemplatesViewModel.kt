package com.glebalekseevjk.habit.ui.page.templates_habits

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.glebalekseevjk.habit.domain.entity.Category
import com.glebalekseevjk.habit.domain.entity.TemplateHabit
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TemplatesViewModel @Inject constructor() : ViewModel() {
    var viewStates by mutableStateOf(
        TemplatesViewState(
            templatesList = listOf(),
            category = null
        )
    )
        private set

    fun dispatch(action: TemplatesViewAction) {
        when (action) {
            is TemplatesViewAction.SetCategory -> setCategory(action.categoryId)
        }
    }

    private fun setCategory(categoryId: Int) {
        viewStates = viewStates.copy(
            templatesList = TemplateHabit.templateHabitList.filter { it.categoryId == categoryId },
            category = Category.categoryList.find { it.id == categoryId }
        )
    }
}

data class TemplatesViewState(
    val templatesList: List<TemplateHabit>,
    val category: Category?
)

sealed class TemplatesViewAction {
    data class SetCategory(val categoryId: Int) : TemplatesViewAction()
}