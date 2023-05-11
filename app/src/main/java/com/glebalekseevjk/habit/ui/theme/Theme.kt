package com.glebalekseevjk.habit.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController


//private val LightColorPalette = AppColors(
//    colorPrimary = gray200,
//    colorOnPrimary = brown500,
//    colorSecondary = brown500_10,
//    colorDivider = brown500_20,
//    colorRegularTag = green500,
//    colorHarmfulTag = red500,
//    colorDisposableTag = blue500,
//    colorProgress = green500,
//    colorIsDone = green500_10,
//    colorIsBad = red500_10,
//    colorAdditional1 = gray500,
//    colorAdditional2 = orange500,
//    colorAdditional3 = gray800,
//    colorAdditional4 = green800,
//    colorUnselected = gray200_40,
//    colorCursor = brown300_40
//)

private val LightColorPalette = AppColors(
    colorPrimary = gray200,
    colorOnPrimary = brown500,
    colorSecondary = brown500_10,
    colorDivider = brown500_20,
    colorRegularTag = green500,
    colorHarmfulTag = red500,
    colorDisposableTag = blue500,
    colorProgress = green500,
    colorIsDone = green500_10,
    colorIsBad = red500_10,
    colorAdditional1 = gray500,
    colorAdditional2 = orange500,
    colorAdditional3 = gray800,
    colorAdditional4 = green800,
    colorUnselected = gray200_40,
    colorCursor = brown300_40,
    colorDeadline = Color.Black,
)

private val DarkColorPalette = AppColors(
    colorPrimary = Color(0xFF3b2e5a),
    colorOnPrimary = Color(0xFFfff5b7),
    colorSecondary = Color(0x33707991),
    colorDivider = Color(0x99fff5b7),
    colorRegularTag = green500,
    colorHarmfulTag = Color(0xFFff0404),
    colorDisposableTag = Color(0xFFcce8f4),
    colorProgress = green500,
    colorIsDone = green500_60,
    colorIsBad = red500_60,
    colorAdditional1 = gray500,
    colorAdditional2 = orange500,
    colorAdditional3 = Color(0xFF5b50fa),
    colorAdditional4 = green800,
    colorUnselected = Color(0x663b2e5a),
    colorCursor = brown300_40,
    colorDeadline = Color.White
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
    colorDivider: Color,
    colorRegularTag: Color,
    colorHarmfulTag: Color,
    colorDisposableTag: Color,
    colorProgress: Color,
    colorIsDone: Color,
    colorIsBad: Color,
    colorAdditional1: Color,
    colorAdditional2: Color,
    colorAdditional3: Color,
    colorAdditional4: Color,
    colorUnselected: Color,
    colorCursor: Color,
    colorDeadline: Color,
) {
    var colorPrimary: Color by mutableStateOf(colorPrimary)
        internal set
    var colorOnPrimary: Color by mutableStateOf(colorOnPrimary)
        private set
    var colorSecondary: Color by mutableStateOf(colorSecondary)
        private set
    var colorDivider: Color by mutableStateOf(colorDivider)
        private set
    var colorRegularTag: Color by mutableStateOf(colorRegularTag)
        private set
    var colorHarmfulTag: Color by mutableStateOf(colorHarmfulTag)
        private set
    var colorDisposableTag: Color by mutableStateOf(colorDisposableTag)
        private set
    var colorProgress: Color by mutableStateOf(colorProgress)
        private set
    var colorIsDone: Color by mutableStateOf(colorIsDone)
        private set
    var colorIsBad: Color by mutableStateOf(colorIsBad)
        private set
    var colorAdditional1: Color by mutableStateOf(colorAdditional1)
        private set
    var colorAdditional2: Color by mutableStateOf(colorAdditional2)
        private set
    var colorAdditional3: Color by mutableStateOf(colorAdditional3)
        private set
    var colorAdditional4: Color by mutableStateOf(colorAdditional4)
        private set
    var colorUnselected: Color by mutableStateOf(colorUnselected)
        private set
    var colorCursor: Color by mutableStateOf(colorCursor)
        private set
    var colorDeadline: Color by mutableStateOf(colorDeadline)
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
    val colorDivider = animateColorAsState(targetColors.colorDivider, TweenSpec(400))
    val colorRegularTag = animateColorAsState(targetColors.colorRegularTag, TweenSpec(400))
    val colorHarmfulTag = animateColorAsState(targetColors.colorHarmfulTag, TweenSpec(400))
    val colorDisposableTag = animateColorAsState(targetColors.colorDisposableTag, TweenSpec(400))
    val colorProgress = animateColorAsState(targetColors.colorProgress, TweenSpec(400))
    val colorIsDone = animateColorAsState(targetColors.colorIsDone, TweenSpec(400))
    val colorIsBad = animateColorAsState(targetColors.colorIsBad, TweenSpec(400))
    val colorAdditional1 = animateColorAsState(targetColors.colorAdditional1, TweenSpec(400))
    val colorAdditional2 = animateColorAsState(targetColors.colorAdditional2, TweenSpec(400))
    val colorAdditional3 = animateColorAsState(targetColors.colorAdditional3, TweenSpec(400))
    val colorAdditional4 = animateColorAsState(targetColors.colorAdditional4, TweenSpec(400))
    val colorUnselected = animateColorAsState(targetColors.colorUnselected, TweenSpec(400))
    val colorCursor = animateColorAsState(targetColors.colorCursor, TweenSpec(400))
    val colorDeadline = animateColorAsState(targetColors.colorDeadline, TweenSpec(400))

    val appColors = AppColors(
        colorPrimary = colorPrimary.value,
        colorOnPrimary = colorOnPrimary.value,
        colorSecondary = colorSecondary.value,
        colorDivider = colorDivider.value,
        colorRegularTag = colorRegularTag.value,
        colorHarmfulTag = colorHarmfulTag.value,
        colorDisposableTag = colorDisposableTag.value,
        colorProgress = colorProgress.value,
        colorIsDone = colorIsDone.value,
        colorIsBad = colorIsBad.value,
        colorAdditional1 = colorAdditional1.value,
        colorAdditional2 = colorAdditional2.value,
        colorAdditional3 = colorAdditional3.value,
        colorAdditional4 = colorAdditional4.value,
        colorUnselected = colorUnselected.value,
        colorCursor = colorCursor.value,
        colorDeadline = colorDeadline.value
    )

    val systemUiCtrl = rememberSystemUiController()
    systemUiCtrl.setStatusBarColor(appColors.colorPrimary)
    systemUiCtrl.setNavigationBarColor(appColors.colorPrimary)
    systemUiCtrl.setSystemBarsColor(appColors.colorPrimary)

    ProvideWindowInsets {
        CompositionLocalProvider(LocalAppColors provides appColors, content = content)
    }
}