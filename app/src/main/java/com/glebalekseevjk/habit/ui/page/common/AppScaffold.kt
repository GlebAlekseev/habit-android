package com.glebalekseevjk.habit.ui.page.common

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.ui.page.splash.SplashPage
import com.glebalekseevjk.habit.ui.theme.AppTheme
import com.glebalekseevjk.habit.ui.theme.typography
import com.glebalekseevjk.habit.ui.widgets.BottomNavBarView
import com.glebalekseevjk.habit.ui.widgets.TopBarView
import kotlinx.coroutines.launch


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppScaffold() {
    val navController = rememberAnimatedNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val systemUiCtrl = rememberSystemUiController()
    val systemBarColor = AppTheme.colors.colorPrimary
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier,
        scaffoldState = scaffoldState,
        backgroundColor = AppTheme.colors.colorPrimary,
        bottomBar = {
            when (currentDestination?.route) {
                RouteName.TODAY -> BottomNavBarView(navController = navController)
                RouteName.HABITS -> BottomNavBarView(navController = navController)
                RouteName.HISTORY -> BottomNavBarView(navController = navController)
                RouteName.SPLASH -> {}
            }
        },
        drawerShape = object : Shape {
            var leftSpaceWidth: Float? = null
            override fun createOutline(
                size: Size,
                layoutDirection: LayoutDirection,
                density: Density
            ): Outline {
                leftSpaceWidth = size.width * 1 / 3
                return Outline.Rectangle(
                    Rect(
                        left = 0f,
                        top = 0f,
                        right = size.width * 2 / 3,
                        bottom = size.height
                    )
                )
            }
        },
        topBar = {
            when (currentDestination?.route) {
                RouteName.TODAY -> TopBarView(
                    stringResource(id = R.string.today),
                    currentDestination.route!!
                ) { scope.launch { scaffoldState.drawerState.open() } }
                RouteName.HABITS -> TopBarView(
                    stringResource(id = R.string.habits),
                    currentDestination.route!!
                ) { scope.launch { scaffoldState.drawerState.open() } }
                RouteName.HISTORY -> TopBarView(
                    stringResource(id = R.string.history),
                    currentDestination.route!!
                ) { scope.launch { scaffoldState.drawerState.open() } }
                RouteName.SPLASH -> {}
            }
        },
        drawerContentColor = AppTheme.colors.colorOnPrimary,
        drawerContent = {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(start = 36.dp)
            ) {
                Text(
                    text = "HTracker",
                    style = typography.brand.copy(fontSize = 48.sp),
                    modifier = Modifier.padding(top = 12.dp)
                )
                Text(text = "V1.0", modifier = Modifier.padding(vertical = 6.dp))
                Text(
                    text = "Вторник",
                    style = typography.iconHint.copy(fontSize = 16.sp),
                    modifier = Modifier.padding(vertical = 0.dp)
                )
                Text(text = "18 апреля 2023 г.", modifier = Modifier.padding(vertical = 6.dp))

                Divider()

                val iconLabelModified = Modifier.padding(vertical = 8.dp)
                val labelModified = Modifier.padding(start = 12.dp)

                Row(iconLabelModified, verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_today),
                        contentDescription = "Сегодня",
                        tint = AppTheme.colors.colorOnPrimary,
                        modifier = Modifier.size(36.dp),
                    )
                    Text(modifier = labelModified, text = "Сегодня", style = typography.iconHint)
                }
                Row(iconLabelModified, verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_habits),
                        contentDescription = "Привычки",
                        tint = AppTheme.colors.colorOnPrimary,
                        modifier = Modifier.size(36.dp),
                    )
                    Text(modifier = labelModified, text = "Привычки", style = typography.iconHint)
                }
                Row(iconLabelModified, verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_history),
                        contentDescription = "История",
                        tint = AppTheme.colors.colorOnPrimary,
                        modifier = Modifier.size(36.dp),
                    )
                    Text(modifier = labelModified, text = "История", style = typography.iconHint)
                }

                Divider()

                Row(iconLabelModified, verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Настройки",
                        tint = AppTheme.colors.colorOnPrimary,
                        modifier = Modifier.size(36.dp),
                    )
                    Text(modifier = labelModified, text = "Настройки", style = typography.iconHint)
                }
            }


        },
        drawerGesturesEnabled = currentDestination?.route != RouteName.SPLASH,
        drawerBackgroundColor = AppTheme.colors.colorPrimary,
        drawerScrimColor = AppTheme.colors.colorOnPrimary,
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

                }
                composable(route = RouteName.HABITS) {
                    Text(text = "HABITS")

                }
                composable(route = RouteName.HISTORY) {
                    Text(text = "HISTORY")

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