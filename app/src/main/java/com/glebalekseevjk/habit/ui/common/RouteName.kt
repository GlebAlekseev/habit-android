package com.glebalekseevjk.habit.ui.common

import android.net.Uri
import android.os.Parcelable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.glebalekseevjk.habit.ui.utils.toJson
import kotlinx.parcelize.Parcelize

object RouteName {
    const val TODAY = "today"
    const val HABITS = "habits"
    const val HISTORY = "history"
    const val SPLASH = "splash"
    const val SETTINGS = "settings"
    const val ADD_HABITS = "add_habits"
    const val EDIT_HABITS = "edit_habits"
    const val TEMPLATES_HABITS = "templates_habits"
}

internal fun NavHostController.navigateTo(
    route: String,
    isClearBackStack: Boolean = false,
    args: Any? = null,
    sideEffect: () -> Unit = {}
) {
    if (currentDestination?.route != route) {
        val argument = when (args) {
            is Parcelable -> String.format("/%s", Uri.encode(args.toJson()))
            is String -> String.format("/%s", args)
            is Int -> String.format("/%s", args)
            is Float -> String.format("/%s", args)
            is Double -> String.format("/%s", args)
            is Boolean -> String.format("/%s", args)
            is Long -> String.format("/%s", args)
            else -> ""
        }

        this.navigate("$route$argument") {
            if (isClearBackStack) {
                popUpTo(this@navigateTo.graph.findNode(currentDestination?.route)!!.id) {
                    inclusive = true
                }
            } else {
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

@Parcelize
internal data class EditHabitsPageParams(
    val habitId: Int? = null,
    val templateId: Int? = null,
) : Parcelable