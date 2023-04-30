package com.glebalekseevjk.habit.ui.common

import android.os.Parcelable
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.domain.entity.Habit
import com.glebalekseevjk.habit.ui.page.add_habits.AddHabitPage
import com.glebalekseevjk.habit.ui.page.edit_habits.EditHabitPage
import com.glebalekseevjk.habit.ui.page.habits.HabitsPage
import com.glebalekseevjk.habit.ui.page.history.HistoryPage
import com.glebalekseevjk.habit.ui.page.settings.SettingsPage
import com.glebalekseevjk.habit.ui.page.splash.SplashPage
import com.glebalekseevjk.habit.ui.page.templates_habits.TemplatesHabitsPage
import com.glebalekseevjk.habit.ui.theme.AppTheme
import com.glebalekseevjk.habit.ui.theme.MiddlePadding
import com.glebalekseevjk.habit.ui.theme.drawerShape
import com.glebalekseevjk.habit.ui.theme.typography
import com.glebalekseevjk.habit.ui.utils.fromJson
import com.glebalekseevjk.habit.ui.widgets.bottom_bar.BottomNavigateBar
import com.glebalekseevjk.habit.ui.widgets.modal_bottom_sheet.*
import com.glebalekseevjk.habit.ui.widgets.top_bar.MainTopBar
import com.glebalekseevjk.habit.ui.widgets.top_bar.StepTopBar
import com.glebalekseevjk.habit.ui.widgets.top_bar.WindowTopBar
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalTime


@Parcelize
sealed class ModalBottomSheetState : Parcelable {
    object Hide : ModalBottomSheetState()
    data class ChooseIcon(val iconId: (Int) -> Unit) : ModalBottomSheetState()
    data class ChooseColor(val colorRGBA: (Int) -> Unit) : ModalBottomSheetState()
    data class ChooseEventDays(val eventDays: (Set<Habit.Companion.Day>) -> Unit) :
        ModalBottomSheetState()

    data class ChooseTime(val time: (LocalTime) -> Unit) : ModalBottomSheetState()
    data class ChooseDate(val date: (LocalDate) -> Unit) : ModalBottomSheetState()
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun AppScaffold(
    onThemeChange: (AppTheme.Theme) -> Unit,
) {
    val navController = rememberAnimatedNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val systemUiCtrl = rememberSystemUiController()
    val systemBarColor = AppTheme.colors.colorPrimary
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )

    var modalBottomSheetType: ModalBottomSheetState by rememberSaveable {
        mutableStateOf(ModalBottomSheetState.Hide)
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            when (val value = modalBottomSheetType) {
                is ModalBottomSheetState.ChooseColor -> ChooseColorModalBottomSheet(value.colorRGBA)
                is ModalBottomSheetState.ChooseDate -> ChooseDateModalBottomSheet(value.date)
                is ModalBottomSheetState.ChooseEventDays -> ChooseEventDaysModalBottomSheet(value.eventDays)
                is ModalBottomSheetState.ChooseIcon -> ChooseIconModalBottomSheet(value.iconId)
                is ModalBottomSheetState.ChooseTime -> ChooseTimeModalBottomSheet(value.time)
                ModalBottomSheetState.Hide -> {}
            }
        },
        sheetShape = RoundedCornerShape(
            topStart = MiddlePadding,
            topEnd = MiddlePadding
        ),
        sheetBackgroundColor = AppTheme.colors.colorPrimary,
        sheetContentColor = AppTheme.colors.colorOnPrimary,
        scrimColor = AppTheme.colors.colorOnPrimary.copy(alpha = 0.4f)
    ) {

        Scaffold(
            modifier = Modifier,
            scaffoldState = scaffoldState,
            backgroundColor = AppTheme.colors.colorPrimary,
            bottomBar = {
                when (currentDestination?.route) {
                    RouteName.TODAY -> BottomNavigateBar(navController = navController)
                    RouteName.HABITS -> BottomNavigateBar(navController = navController)
                    RouteName.HISTORY -> BottomNavigateBar(navController = navController)
                }
            },
            drawerShape = drawerShape,
            topBar = {
                when (currentDestination?.route) {
                    RouteName.TODAY -> MainTopBar(
                        stringResource(id = R.string.today),
                        currentDestination.route!!,
                        onOpenDrawer = { scope.launch { scaffoldState.drawerState.open() } },
                        onNavigateToAddHabits = {
                            scope.launch {
                                navController.navigateTo(
                                    route = RouteName.ADD_HABITS
                                )
                            }
                        }
                    )
                    RouteName.HABITS -> MainTopBar(
                        stringResource(id = R.string.habits),
                        currentDestination.route!!,
                        onOpenDrawer = { scope.launch { scaffoldState.drawerState.open() } },
                        onNavigateToAddHabits = {
                            scope.launch {
                                navController.navigateTo(
                                    route = RouteName.ADD_HABITS
                                )
                            }
                        }
                    )
                    RouteName.HISTORY -> MainTopBar(
                        stringResource(id = R.string.history),
                        currentDestination.route!!,
                        onOpenDrawer = { scope.launch { scaffoldState.drawerState.open() } },
                        onNavigateToAddHabits = {
                            scope.launch {
                                navController.navigateTo(
                                    route = RouteName.ADD_HABITS
                                )
                            }
                        }
                    )
                    RouteName.SETTINGS -> StepTopBar(
                        title = stringResource(id = R.string.settings)
                    ) { scope.launch { navController.back() } }
                    RouteName.ADD_HABITS -> StepTopBar(
                        title = stringResource(id = R.string.add_habits)
                    ) { scope.launch { navController.back() } }
                    RouteName.TEMPLATES_HABITS + "/{templatesId}" -> {
                        WindowTopBar { scope.launch { navController.back() } }
                    }
                    RouteName.EDIT_HABITS + "/{params}" -> {
                        WindowTopBar { scope.launch { navController.back() } }
                    }
                }
            },
            drawerContentColor = AppTheme.colors.colorOnPrimary,
            drawerContent = {
                MainDrawer(
                    onNavigateToToday = {
                        navController.navigateTo(
                            route = RouteName.TODAY,
                            navConfig = {
                                it.popUpTo(navController.currentBackStack.value[1].destination.route!!) {
                                    inclusive = true
                                    saveState = true
                                }
                                it.restoreState = true
                                it.launchSingleTop = true
                            }
                        )
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    },
                    onNavigateToHabits = {
                        navController.navigateTo(
                            route = RouteName.HABITS,
                            navConfig = {
                                it.popUpTo(navController.currentBackStack.value[1].destination.route!!) {
                                    inclusive = true
                                    saveState = true
                                }
                                it.restoreState = true
                                it.launchSingleTop = true
                            }
                        )
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    },
                    onNavigateToHistory = {
                        navController.navigateTo(
                            route = RouteName.HISTORY,
                            navConfig = {
                                it.popUpTo(navController.currentBackStack.value[1].destination.route!!) {
                                    inclusive = true
                                    saveState = true
                                }
                                it.restoreState = true
                                it.launchSingleTop = true
                            }
                        )
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    },
                    onNavigateToSettings = {
                        navController.navigateTo(route = RouteName.SETTINGS)
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    },
                )
            },
            drawerGesturesEnabled = currentDestination?.route == RouteName.TODAY ||
                    currentDestination?.route == RouteName.HISTORY || currentDestination?.route == RouteName.HABITS,
            drawerBackgroundColor = AppTheme.colors.colorPrimary,
            drawerScrimColor = AppTheme.colors.colorOnPrimary.copy(alpha = 0.4f),
            content = { it ->
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
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "TODAY",
                                color = AppTheme.colors.colorOnPrimary,
                                style = typography.titleMedium
                            )
                        }
                    }
                    composable(route = RouteName.HABITS) {
                        HabitsPage(
                            onNavigateToAddHabits = {
                                navController.navigateTo(
                                    route = RouteName.ADD_HABITS,
                                )
                            },
                            onNavigateToEditHabits = {
                                navController.navigateTo(
                                    route = RouteName.EDIT_HABITS,
                                    args = EditHabitsPageParams(it, null)
                                )
                            }
                        )
                    }
                    composable(route = RouteName.HISTORY) {
                        HistoryPage()
                    }
                    composable(route = RouteName.ADD_HABITS) {
                        AddHabitPage(
                            onNavigateToTemplatesHabits = { categoryId ->
                                navController.navigateTo(
                                    route = RouteName.TEMPLATES_HABITS,
                                    args = categoryId
                                )
                            },
                            onNavigateToEditHabits = {
                                navController.navigateTo(
                                    route = RouteName.EDIT_HABITS,
                                    args = EditHabitsPageParams(
                                        null,
                                        when (it) {
                                            Habit.Companion.HabitType.REGULAR -> -1
                                            Habit.Companion.HabitType.HARMFUL -> -2
                                            Habit.Companion.HabitType.DISPOSABLE -> -3
                                        }
                                    )
                                )
                            }
                        )
                    }
                    composable(
                        route = RouteName.EDIT_HABITS + "/{params}",
                        arguments = listOf(navArgument("params") { type = NavType.StringType })
                    ) {
                        val args =
                            it.arguments?.getString("params")?.fromJson<EditHabitsPageParams>()
                        args?.let { (habitId, templateId) ->
                            EditHabitPage(habitId, templateId,
                                onOpenChooseIconModalBottomSheet = {
                                    scope.launch {
                                        modalBottomSheetType = ModalBottomSheetState.ChooseIcon(it)
                                        bottomSheetState.show()
                                    }
                                },
                                onOpenChooseIconColorModalBottomSheet = {
                                    scope.launch {
                                        modalBottomSheetType = ModalBottomSheetState.ChooseColor(it)
                                        bottomSheetState.show()
                                    }
                                },
                                onOpenChooseEventDayModalBottomSheet = {
                                    scope.launch {
                                        modalBottomSheetType =
                                            ModalBottomSheetState.ChooseEventDays(it)
                                        bottomSheetState.show()
                                    }
                                },
                                onOpenChooseTimeModalBottomSheet = {
                                    scope.launch {
                                        modalBottomSheetType = ModalBottomSheetState.ChooseTime(it)
                                        bottomSheetState.show()
                                    }
                                },
                                onOpenChooseFinishDateModalBottomSheet = {
                                    scope.launch {
                                        modalBottomSheetType = ModalBottomSheetState.ChooseDate(it)
                                        bottomSheetState.show()
                                    }
                                },
                                onSaveHabit = {
                                    navController.navigateTo(
                                        route = RouteName.ADD_HABITS,
                                        navConfig = {
                                            it.popUpTo(RouteName.ADD_HABITS) {
                                                inclusive = true
                                            }
                                            it.launchSingleTop = true
                                        }
                                    )
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                    composable(
                        route = RouteName.TEMPLATES_HABITS + "/{templatesId}",
                        arguments = listOf(navArgument("templatesId") { type = NavType.IntType })
                    ) {
                        val args = it.arguments?.getInt("templatesId")
                        args?.let { templatesId ->
                            TemplatesHabitsPage(
                                categoryId = templatesId,
                                onNavigateToEditHabits = { templateId ->
                                    navController.navigateTo(
                                        route = RouteName.EDIT_HABITS,
                                        args = EditHabitsPageParams(null, templateId)
                                    )
                                }
                            )
                        }
                    }
                    composable(route = RouteName.SETTINGS) {
                        SettingsPage(onThemeChange)
                    }
                    composable(route = RouteName.SPLASH) {
                        SplashPage {
                            navController.navigateTo(
                                route = RouteName.TODAY,
                                navConfig = {
                                    navController.popBackStack()
                                },
                                sideEffect = {
                                    systemUiCtrl.setStatusBarColor(systemBarColor)
                                    systemUiCtrl.setNavigationBarColor(systemBarColor)
                                    systemUiCtrl.setSystemBarsColor(systemBarColor)
                                })
                        }
                    }
                }
            }
        )
    }

}