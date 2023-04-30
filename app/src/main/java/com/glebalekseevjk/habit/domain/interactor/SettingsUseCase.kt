package com.glebalekseevjk.habit.domain.interactor

import com.glebalekseevjk.habit.domain.repository.SettingsRepository
import javax.inject.Inject

class SettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    var isDarkTheme
        get() = settingsRepository.isDarkTheme
        set(value) {
            settingsRepository.isDarkTheme = value
        }
    var language
        get() = settingsRepository.language
        set(value) {
            settingsRepository.language = value
        }
}