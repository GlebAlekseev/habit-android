package com.glebalekseevjk.habit.ui.common

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

object RouteName {
    const val TODAY = "today"
    const val HABITS = "habits"
    const val HISTORY = "history"
    const val SPLASH = "splash"
    const val SETTINGS = "settings"
    const val ADD_HABITS = "add_habits"
}

internal fun NavHostController.navigateTo(route: String, isClearBackStack: Boolean = false, sideEffect: () -> Unit = {}){
    if (currentDestination?.route != route) {
        this.navigate(route) {
            if (isClearBackStack){
                popUpTo(this@navigateTo.graph.findNode(currentDestination?.route)!!.id) {
                    inclusive = true
                }
            }else {
                popUpTo(this@navigateTo.graph.findStartDestination().id) {
                    inclusive = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }
        sideEffect()
    }
}


internal fun NavHostController.back() {
    this.popBackStack()
}