package com.glebalekseevjk.habit.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.glebalekseevjk.habit.domain.entity.Language
import com.glebalekseevjk.habit.ui.page.settings.SettingsViewModel
import com.glebalekseevjk.habit.ui.theme.AppTheme
import com.glebalekseevjk.habit.utils.LocaleUtils

@Composable
fun MainEntry(
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    var theme by rememberSaveable {
        mutableStateOf(
            when (settingsViewModel.viewStates.isDarkTheme.also { println(">>>>>>>>>>>>>>>>>>>>> isDarkTheme=$it") }) {
                true -> AppTheme.Theme.Dark
                false -> AppTheme.Theme.Light
            }
        )
    }

    var lang by rememberSaveable {
        mutableStateOf(
            when (settingsViewModel.viewStates.language) {
                Language.RUSSIAN -> "ru"
                Language.ENGLISH -> "en"
            }
        )
    }
    LocaleUtils.setLocale(LocalContext.current, lang)
    AppTheme(theme) {
        AppScaffold(
            onThemeChange = { theme = it },
            onLanguageChange = { lang = when(it){
                Language.RUSSIAN -> "ru"
                Language.ENGLISH -> "en"
            } },
            lang = lang
        )
    }
}
