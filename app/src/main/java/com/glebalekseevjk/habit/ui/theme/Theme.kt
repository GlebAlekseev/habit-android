package com.glebalekseevjk.habit.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColorPalette = AppColors(
    colorPrimary = gray200,
    colorOnPrimary = brown500,
    colorSecondary = brown500,
    colorOnSecondary = gray200,
    colorAccent = raspberry500_40,
    divider = brown500_20,
    selectedContent = gray200,
    unselectedContent = gray200_40,
    subtitle = brown500_60,
)

private val DarkColorPalette = AppColors(
    colorPrimary = brown500,
    colorOnPrimary = gray200,
    colorSecondary = brown500,
    colorOnSecondary = gray200,
    colorAccent = raspberry500_40,
    divider = brown500_20,
    selectedContent = gray200,
    unselectedContent = gray200_40,
    subtitle = brown500_60,
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
    selectedContent: Color,
    unselectedContent: Color,
    subtitle: Color,
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
    var selectedContent: Color by mutableStateOf(selectedContent)
        private set
    var unselectedContent: Color by mutableStateOf(unselectedContent)
        private set
    var subtitle: Color by mutableStateOf(subtitle)
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
    val selectedContent = animateColorAsState(targetColors.selectedContent, TweenSpec(400))
    val unselectedContent = animateColorAsState(targetColors.unselectedContent, TweenSpec(400))
    val subtitle = animateColorAsState(targetColors.subtitle, TweenSpec(400))

    val appColors = AppColors(
        colorPrimary = colorPrimary.value,
        colorOnPrimary = colorOnPrimary.value,
        colorSecondary = colorSecondary.value,
        colorOnSecondary = colorOnSecondary.value,
        colorAccent = colorAccent.value,
        divider = divider.value,
        selectedContent = selectedContent.value,
        unselectedContent = unselectedContent.value,
        subtitle = subtitle.value,
    )

    val systemUiCtrl = rememberSystemUiController()
    systemUiCtrl.setStatusBarColor(appColors.colorPrimary)
    systemUiCtrl.setNavigationBarColor(appColors.colorPrimary)
    systemUiCtrl.setSystemBarsColor(appColors.colorPrimary)

    ProvideWindowInsets {
        CompositionLocalProvider(LocalAppColors provides appColors, content = content)
    }
}