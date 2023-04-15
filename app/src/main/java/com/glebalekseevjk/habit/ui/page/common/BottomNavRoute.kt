package com.glebalekseevjk.habit.ui.page.common

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import com.glebalekseevjk.habit.R

sealed class BottomNavRoute(
    var routeName: String,
    @StringRes var stringId: Int,
    var icon: ImageVector
) {
    object Home : BottomNavRoute(RouteName.HOME, R.string.home, Icons.Default.Home)
    object Stats : BottomNavRoute(RouteName.STATS, R.string.stats, Icons.Default.Menu)
    object NewQuest :
        BottomNavRoute(RouteName.NEW_QUEST, R.string.new_quest, Icons.Default.Favorite)
}
