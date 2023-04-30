package com.glebalekseevjk.habit.domain.repository

import com.glebalekseevjk.habit.domain.entity.Language

interface SettingsRepository {
    var isDarkTheme: Boolean
    var language: Language
}
