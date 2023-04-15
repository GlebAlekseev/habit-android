package com.glebalekseevjk.habit.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColorPalette = AppColors(
    colorPrimary = gray100,
    colorOnPrimary = blue900,
    colorSecondary = blue200,
    colorOnSecondary = blue700,
    colorAccent = blue500,
    divider = gray300,
    icon = gray100,
    warn = orange500,
    success = green500,
    error = red500,
)

private val DarkColorPalette = AppColors(
    colorPrimary = blue900,
    colorOnPrimary = gray100,
    colorSecondary = blue700,
    colorOnSecondary = blue200,
    colorAccent = blue500,
    divider = gray300,
    icon = gray500,
    warn = orange500,
    success = green500,
    error = red500,
)

var LocalAppColors = compositionLocalOf {
    LightColorPalette
}

@Stable
object AppTheme {
    val colors: AppColors
        @Composable
        get() = LocalAppColors.current

    enum class Theme {
        Light, Dark
    }
}

@Stable
class AppColors(
    colorPrimary: Color,
    colorOnPrimary: Color,
    colorSecondary: Color,
    colorOnSecondary: Color,
    colorAccent: Color,
    divider: Color,
    icon: Color,
    warn: Color,
    success: Color,
    error: Color
) {
    var colorPrimary: Color by mutableStateOf(colorPrimary)
        internal set
    var colorOnPrimary: Color by mutableStateOf(colorOnPrimary)
        private set
    var colorSecondary: Color by mutableStateOf(colorSecondary)
        private set
    var colorOnSecondary: Color by mutableStateOf(colorOnSecondary)
        private set
    var colorAccent: Color by mutableStateOf(colorAccent)
        private set
    var divider: Color by mutableStateOf(divider)
        private set
    var icon: Color by mutableStateOf(icon)
        private set
    var warn: Color by mutableStateOf(warn)
        private set
    var success: Color by mutableStateOf(success)
        private set
    var error: Color by mutableStateOf(error)
        private set

}

@Composable
fun AppTheme(
    theme: AppTheme.Theme =
        if (isSystemInDarkTheme()) AppTheme.Theme.Dark else AppTheme.Theme.Light,
    content: @Composable () -> Unit
) {
    val targetColors = when (theme) {
        AppTheme.Theme.Light -> LightColorPalette
        AppTheme.Theme.Dark -> DarkColorPalette
    }

    val colorPrimary = animateColorAsState(targetColors.colorPrimary, TweenSpec(400))
    val colorOnPrimary = animateColorAsState(targetColors.colorOnPrimary, TweenSpec(400))
    val colorSecondary = animateColorAsState(targetColors.colorSecondary, TweenSpec(400))
    val colorOnSecondary = animateColorAsState(targetColors.colorOnSecondary, TweenSpec(400))
    val colorAccent = animateColorAsState(targetColors.colorAccent, TweenSpec(400))
    val divider = animateColorAsState(targetColors.divider, TweenSpec(400))
    val icon = animateColorAsState(targetColors.icon, TweenSpec(400))
    val warn = animateColorAsState(targetColors.warn, TweenSpec(400))
    val success = animateColorAsState(targetColors.success, TweenSpec(400))
    val error = animateColorAsState(targetColors.error, TweenSpec(400))

    val appColors = AppColors(
        colorPrimary = colorPrimary.value,
        colorOnPrimary = colorOnPrimary.value,
        colorSecondary = colorSecondary.value,
        colorOnSecondary = colorOnSecondary.value,
        colorAccent = colorAccent.value,
        divider = divider.value,
        icon = icon.value,
        warn = warn.value,
        success = success.value,
        error = error.value,
    )

    val systemUiCtrl = rememberSystemUiController()
    systemUiCtrl.setStatusBarColor(appColors.colorPrimary)
    systemUiCtrl.setNavigationBarColor(appColors.colorPrimary)
    systemUiCtrl.setSystemBarsColor(appColors.colorPrimary)

    ProvideWindowInsets {
        CompositionLocalProvider(LocalAppColors provides appColors, content = content)
    }
}