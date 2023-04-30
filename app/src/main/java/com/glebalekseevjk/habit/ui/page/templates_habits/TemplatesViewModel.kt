package com.glebalekseevjk.habit.ui.page.templates_habits

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glebalekseevjk.habit.domain.entity.Category
import com.glebalekseevjk.habit.domain.entity.TemplateHabit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TemplatesViewModel @Inject constructor() : ViewModel() {
    var viewStates by mutableStateOf<TemplatesViewState>(TemplatesViewState.Init)
        private set

    fun dispatch(action: TemplatesViewAction) {
        when (action) {
            is TemplatesViewAction.InitTemplates -> initTemplates(action.categoryId)
        }
    }

    private fun initTemplates(categoryId: Int) {
        viewStates = TemplatesViewState.Loading
        viewModelScope.launch {
            viewStates = TemplatesViewState.Loaded(
                templateList = TemplateHabit.templateHabitList.filter { it.categoryId == categoryId },
                category = Category.categoryList.find { it.id == categoryId }!!,
                lazyListState = LazyListState()
            )
        }
    }
}

sealed class TemplatesViewState {
    object Init : TemplatesViewState()
    object Loading : TemplatesViewState()
    object Failure : TemplatesViewState()
    data class Loaded(
        val templateList: List<TemplateHabit>,
        val category: Category,
        val lazyListState: LazyListState
    ) : TemplatesViewState()
}

sealed class TemplatesViewAction {
    data class InitTemplates(val categoryId: Int) : TemplatesViewAction()
}