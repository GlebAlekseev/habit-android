package com.glebalekseevjk.habit.ui.page.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.glebalekseevjk.habit.ui.page.splash.SplashPage
import com.glebalekseevjk.habit.ui.theme.AppTheme

@Composable
fun MainEntry() {
    var isSplash by rememberSaveable { mutableStateOf(true) }
    AppTheme {
        if (isSplash) {
            SplashPage {
                isSplash = false
            }
        } else {
            AppScaffold()
        }
    }
}