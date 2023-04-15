package com.glebalekseevjk.habit.ui.page.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.glebalekseevjk.habit.ui.theme.AppTheme
import com.glebalekseevjk.habit.ui.widgets.BottomNavBarView


@Composable
fun AppScaffold() {
    val navCtrl = rememberNavController()
    val navBackStackEntry by navCtrl.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        modifier = Modifier,
        bottomBar = {
            when (currentDestination?.route) {
                RouteName.HOME -> BottomNavBarView(navCtrl = navCtrl)
                RouteName.STATS -> BottomNavBarView(navCtrl = navCtrl)
                RouteName.NEW_QUEST -> BottomNavBarView(navCtrl = navCtrl)
            }
        },
        content = {
            NavHost(
                modifier = Modifier
                    .background(AppTheme.colors.colorPrimary)
                    .padding(it),
                navController = navCtrl,
                startDestination = RouteName.HOME
            ) {
                composable(route = RouteName.HOME) {
                    Text(text = "HOME")
                }
                composable(route = RouteName.STATS) {
                    Text(text = "STATS")
                }
                composable(route = RouteName.NEW_QUEST) {
                    Text(text = "NEW QUEST")
                }
            }
        }
    )
}