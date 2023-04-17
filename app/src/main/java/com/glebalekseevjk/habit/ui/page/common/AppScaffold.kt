package com.glebalekseevjk.habit.ui.page.common

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.glebalekseevjk.habit.ui.page.splash.SplashPage
import com.glebalekseevjk.habit.ui.theme.AppTheme
import com.glebalekseevjk.habit.ui.widgets.BottomNavBarView


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppScaffold() {
    val navController = rememberAnimatedNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val systemUiCtrl = rememberSystemUiController()
    val systemBarColor = AppTheme.colors.colorPrimary
    Scaffold(
        modifier = Modifier,
        backgroundColor = AppTheme.colors.colorPrimary,
        bottomBar = {
            when (currentDestination?.route) {
                RouteName.TODAY -> BottomNavBarView(navController = navController)
                RouteName.HABITS -> BottomNavBarView(navController = navController)
                RouteName.HISTORY -> BottomNavBarView(navController = navController)
                RouteName.SPLASH -> {}
            }
        },
        content = {
            AnimatedNavHost(
                modifier = Modifier
                    .padding(it),
                navController = navController,
                startDestination = RouteName.SPLASH,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popEnterTransition = { EnterTransition.None },
                popExitTransition = { ExitTransition.None },
            ) {
                composable(route = RouteName.TODAY) {
                    Text(text = "TODAY")
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.Black)
                    )
                }
                composable(route = RouteName.HABITS) {
                    Text(text = "HABITS")
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.Cyan)
                    )
                }
                composable(route = RouteName.HISTORY) {
                    Text(text = "HISTORY")
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.Red)
                    )
                }
                composable(route = RouteName.SPLASH) {
                    SplashPage {
                        if (currentDestination?.route != RouteName.TODAY) {
                            navController.navigate(RouteName.TODAY) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                            systemUiCtrl.setStatusBarColor(systemBarColor)
                            systemUiCtrl.setNavigationBarColor(systemBarColor)
                            systemUiCtrl.setSystemBarsColor(systemBarColor)
                        }
                    }
                }
            }
        }
    )
}