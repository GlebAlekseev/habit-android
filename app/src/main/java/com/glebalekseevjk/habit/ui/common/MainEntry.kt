package com.glebalekseevjk.habit.ui.common

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.glebalekseevjk.habit.ui.theme.AppTheme

@Composable
fun MainEntry() {
    var theme by rememberSaveable {
        mutableStateOf(AppTheme.Theme.Light)
    }
    AppTheme(theme) {
        AppScaffold { theme = it }
    }
}