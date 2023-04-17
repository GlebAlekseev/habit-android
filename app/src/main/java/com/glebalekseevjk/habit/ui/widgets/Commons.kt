package com.glebalekseevjk.habit.ui.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.glebalekseevjk.habit.ui.page.common.BottomNavRoute
import com.glebalekseevjk.habit.ui.page.common.RouteName
import com.glebalekseevjk.habit.ui.theme.AppTheme
import com.glebalekseevjk.habit.ui.theme.typography

@Composable
fun BottomNavBarView(navController: NavHostController) {
    val bottomNavList = listOf(
        BottomNavRoute.Today,
        BottomNavRoute.Habits,
        BottomNavRoute.History,
    )
    BottomNavigation(
        Modifier
            .height(100.dp),
        backgroundColor = AppTheme.colors.colorSecondary,
        elevation = 5.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        bottomNavList.forEach { screen ->
            BottomNavigationItem(
                modifier = Modifier
                    .padding(20.dp),
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = screen.iconId),
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = stringResource(screen.stringId),
                        style = typography.iconHint,
                        modifier = Modifier.padding(top = 25.dp)
                    )
                },
                selected = currentDestination?.hierarchy?.any { it.route == screen.routeName } == true,
                onClick = {
                    if (currentDestination?.route != screen.routeName) {
                        println("\t\tcurrentDestination?.route=${currentDestination?.route} target=${screen.routeName}")
                        navController.navigate(screen.routeName) {
                            println("\tpopUpTo=${navController.graph.findStartDestination()}")
                            popUpTo(navController.graph.findNode(currentDestination?.route)!!.id) {
                                inclusive = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                selectedContentColor = AppTheme.colors.selectedContent,
                unselectedContentColor = AppTheme.colors.unselectedContent,
            )
        }
    }
}

@Composable
fun TopBarView(title: String, route: String, onOpenDrawer: ()->Unit) {
    TopAppBar(
        backgroundColor = AppTheme.colors.colorPrimary,
        elevation = 5.dp
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .align(Alignment.CenterVertically)
                .padding(horizontal = 24.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(Modifier.align(Alignment.CenterVertically)) {
                IconButton(
                    onClick = onOpenDrawer,
                    modifier = Modifier.align(Alignment.CenterVertically).padding(end = 10.dp)
                ) {
                    Icon(Icons.Filled.Menu, "Меню", tint = AppTheme.colors.colorOnPrimary, modifier = Modifier.size(36.dp))
                }

                Text(
                    text = title.toUpperCase(),
                    style = typography.titleTopBar,
                    color = AppTheme.colors.colorOnPrimary,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                if (route == RouteName.TODAY) {
                    Text(
                        text = "18 апр.",
                        style = typography.subtitleTopBar,
                        color = AppTheme.colors.subtitle,
                        modifier = Modifier.align(Alignment.CenterVertically).padding(start = 20.dp)
                    )
                }
            }

            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(Icons.Filled.Add, "Добавить привычку", tint = AppTheme.colors.colorOnPrimary, modifier = Modifier.size(36.dp))
            }
        }
    }

}