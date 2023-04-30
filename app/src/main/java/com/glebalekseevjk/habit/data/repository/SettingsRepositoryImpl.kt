package com.glebalekseevjk.habit.data.repository

import com.glebalekseevjk.habit.data.preferences.SharedPreferencesSettingsStorage
import com.glebalekseevjk.habit.domain.entity.Language
import com.glebalekseevjk.habit.domain.repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val sharedPreferencesSettingsStorage: SharedPreferencesSettingsStorage
) : SettingsRepository {
    override var isDarkTheme: Boolean
        get() = sharedPreferencesSettingsStorage.getIsDarkTheme()
        set(value) = sharedPreferencesSettingsStorage.setIsDarkTheme(value)
    override var language: Language
        get() = sharedPreferencesSettingsStorage.getLanguage()
        set(value) = sharedPreferencesSettingsStorage.setLanguage(value)
}