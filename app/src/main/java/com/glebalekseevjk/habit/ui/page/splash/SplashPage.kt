package com.glebalekseevjk.habit.ui.page.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.ui.theme.AppTheme
import com.glebalekseevjk.habit.ui.theme.BaseRotate
import com.glebalekseevjk.habit.ui.theme.typography
import kotlinx.coroutines.delay

@Composable
fun SplashPage(onNextPage: () -> Unit) {
    val systemBarColor = AppTheme.colors.colorOnPrimary
    val systemUiCtrl = rememberSystemUiController()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.colorSecondary),
        contentAlignment = Alignment.Center
    ) {
        LaunchedEffect(Unit) {
            systemUiCtrl.setStatusBarColor(systemBarColor)
            systemUiCtrl.setNavigationBarColor(systemBarColor)
            systemUiCtrl.setSystemBarsColor(systemBarColor)
            delay(1600)
            onNextPage.invoke()
        }

        Box(
            Modifier
                .height(256.dp)
                .rotate(BaseRotate)
        ) {
            Box(
                modifier = Modifier
                    .offset(LocalConfiguration.current.screenWidthDp.dp / 2, 0.dp)
                    .fillMaxSize()
                    .background(AppTheme.colors.colorPrimary)
            )
            Box(
                modifier = Modifier
                    .offset(-LocalConfiguration.current.screenWidthDp.dp / 2, 0.dp)
                    .fillMaxSize()
                    .background(AppTheme.colors.colorPrimary)
            )
        }

        Text(
            text = stringResource(id = R.string.app_name),
            style = typography.displayLarge,
            color = AppTheme.colors.colorOnPrimary
        )
    }
}