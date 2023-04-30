package com.glebalekseevjk.habit.ui.page.settings

import android.app.Application
import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.glebalekseevjk.habit.domain.entity.Habit
import com.glebalekseevjk.habit.domain.entity.Language
import com.glebalekseevjk.habit.domain.interactor.SettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val app: Application,
    private val settingsUseCase: SettingsUseCase,
) : AndroidViewModel(app) {
    var viewStates by mutableStateOf(
        SettingsViewState(
            language = settingsUseCase.language,
            isDarkTheme = settingsUseCase.isDarkTheme
        )
    )
        private set

    fun dispatch(action: SettingsViewAction) {
        when (action) {
            is SettingsViewAction.SetTheme -> setTheme(action.isDarkTheme)
            is SettingsViewAction.SetLanguage -> setLanguage(action.language)
        }
    }

    private fun setTheme(isDarkTheme: Boolean) {
        settingsUseCase.isDarkTheme = isDarkTheme
        viewStates = viewStates.copy(
            isDarkTheme = isDarkTheme
        )
    }

    private fun setLanguage(language: Language) {
        settingsUseCase.language = language
        viewStates = viewStates.copy(
            language = language
        )
    }
}

data class SettingsViewState(
    val language: Language,
    val isDarkTheme: Boolean,
)

sealed class SettingsViewAction {
    data class SetTheme(val isDarkTheme: Boolean) : SettingsViewAction()
    data class SetLanguage(val language: Language) : SettingsViewAction()
}