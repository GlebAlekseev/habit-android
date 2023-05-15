package com.glebalekseevjk.habit.ui.common

import android.os.Parcelable
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.domain.entity.Habit
import com.glebalekseevjk.habit.domain.entity.Habit.Companion.TargetType
import com.glebalekseevjk.habit.domain.entity.Language
import com.glebalekseevjk.habit.ui.page.add_habits.AddHabitPage
import com.glebalekseevjk.habit.ui.page.edit_habits.EditHabitPage
import com.glebalekseevjk.habit.ui.page.habits.HabitsPage
import com.glebalekseevjk.habit.ui.page.history.HistoryPage
import com.glebalekseevjk.habit.ui.page.settings.SettingsPage
import com.glebalekseevjk.habit.ui.page.splash.SplashPage
import com.glebalekseevjk.habit.ui.page.templates_habits.TemplatesHabitsPage
import com.glebalekseevjk.habit.ui.page.today.TodayPage
import com.glebalekseevjk.habit.ui.theme.AppTheme
import com.glebalekseevjk.habit.ui.theme.MiddlePadding
import com.glebalekseevjk.habit.ui.theme.drawerShape
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
    data class ChooseIcon(val initValue: Int, val setIconId: (Int) -> Unit) :
        ModalBottomSheetState()

    data class ChooseColor(val initValue: Int, val setColorRGBA: (Int) -> Unit) :
        ModalBottomSheetState()

    data class ChooseEventDays(
        val initValue: Set<Habit.Companion.Day>,
        val setEventDays: (Set<Habit.Companion.Day>) -> Unit
    ) :
        ModalBottomSheetState()

    data class ChooseTimeStart(
        val initValue: LocalTime?,
        val minValue: LocalTime?,
        val maxValue: LocalTime?,
        val setTime: (LocalTime) -> Unit
    ) :
        ModalBottomSheetState()

    data class ChooseTimeEnd(
        val initValue: LocalTime?,
        val minValue: LocalTime?,
        val maxValue: LocalTime?,
        val setTime: (LocalTime) -> Unit
    ) :
        ModalBottomSheetState()

    data class ChooseTime(
        val initValue: LocalTime?,
        val minValue: LocalTime?,
        val maxValue: LocalTime?,
        val setTime: (LocalTime) -> Unit
    ) :
        ModalBottomSheetState()

    data class ChooseDate(val initValue: LocalDate?, val setDate: (LocalDate) -> Unit) :
        ModalBottomSheetState()

    data class ChooseTargetType(
        val initValue: TargetType,
        val setTargetType: (TargetType) -> Unit
    ) : ModalBottomSheetState()

    data class ChooseDuration(val initValue: LocalTime?, val setDuration: (LocalTime) -> Unit) :
        ModalBottomSheetState()

    data class ChooseRepeatCount(val initValue: Int?, val setRepeatCount: (Int) -> Unit) :
        ModalBottomSheetState()
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun AppScaffold(
    onThemeChange: (AppTheme.Theme) -> Unit,
    onLanguageChange: (Language) -> Unit,
    lang: String
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
        skipHalfExpanded = true
    )

    var modalBottomSheetType: ModalBottomSheetState by rememberSaveable {
        mutableStateOf(ModalBottomSheetState.Hide)
    }

    var isVisibleBottomBar by remember {
        mutableStateOf(false)
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            when (val action = modalBottomSheetType) {
                is ModalBottomSheetState.ChooseColor -> ChooseColorModalBottomSheet(
                    initValue = action.initValue,
                    callback = action.setColorRGBA,
                    onCancel = { scope.launch { bottomSheetState.hide() } }
                )
                is ModalBottomSheetState.ChooseDate -> ChooseDateModalBottomSheet(
                    action.initValue,
                    action.setDate,
                    onCancel = { scope.launch { bottomSheetState.hide() } }
                )
                is ModalBottomSheetState.ChooseEventDays -> ChooseEventDaysModalBottomSheet(
                    action.initValue,
                    action.setEventDays,
                    onCancel = { scope.launch { bottomSheetState.hide() } }
                )
                is ModalBottomSheetState.ChooseIcon -> ChooseIconModalBottomSheet(
                    action.initValue,
                    action.setIconId,
                    onCancel = { scope.launch { bottomSheetState.hide() } }
                )
                is ModalBottomSheetState.ChooseTime -> {
                    ChooseTimeModalBottomSheet(
                        action.initValue,
                        action.minValue,
                        action.maxValue,
                        action.setTime,
                        onCancel = { scope.launch { bottomSheetState.hide() } }
                    )
                }
                is ModalBottomSheetState.ChooseTimeStart -> {
                    ChooseTimeModalBottomSheet(
                        action.initValue,
                        action.minValue,
                        action.maxValue,
                        action.setTime,
                        onCancel = { scope.launch { bottomSheetState.hide() } }
                    )
                }
                is ModalBottomSheetState.ChooseTimeEnd -> {
                    ChooseTimeModalBottomSheet(
                        action.initValue,
                        action.minValue,
                        action.maxValue,
                        action.setTime,
                        onCancel = { scope.launch { bottomSheetState.hide() } }
                    )
                }
                is ModalBottomSheetState.ChooseDuration -> ChooseTimeModalBottomSheet(
                    action.initValue,
                    null,
                    null,
                    action.setDuration,
                    onCancel = { scope.launch { bottomSheetState.hide() } }
                )
                is ModalBottomSheetState.ChooseRepeatCount -> ChooseRepeatCountModalBottomSheet(
                    action.initValue,
                    action.setRepeatCount,
                    onCancel = { scope.launch { bottomSheetState.hide() } }
                )
                is ModalBottomSheetState.ChooseTargetType -> ChooseTargetTypeModalBottomSheet(
                    action.initValue,
                    action.setTargetType,
                    onCancel = { scope.launch { bottomSheetState.hide() } }
                )
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
                    RouteName.TODAY -> BottomNavigateBar(
                        navController = navController,
                        isVisibleBottomBar
                    )
                    RouteName.HABITS -> BottomNavigateBar(
                        navController = navController,
                        isVisibleBottomBar
                    )
                    RouteName.HISTORY -> BottomNavigateBar(
                        navController = navController,
                        isVisibleBottomBar
                    )
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
                        title = stringResource(id = R.string.settings),
                        lang = lang,
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
                BackHandler(enabled = bottomSheetState.isVisible) {
                    scope.launch {
                        bottomSheetState.hide()
                    }
                }
                AnimatedNavHost(
                    modifier = Modifier
                        .padding(it),
                    navController = navController,
                    startDestination = RouteName.SPLASH,
                    enterTransition = { defaultHTrackerEnterTransition(initialState, targetState) },
                    exitTransition = { defaultHTrackerExitTransition(initialState, targetState) },
                    popEnterTransition = { defaultHTrackerPopEnterTransition() },
                    popExitTransition = { defaultHTrackerPopExitTransition() },
                ) {
                    composable(route = RouteName.TODAY) {
                        TodayPage()
                        isVisibleBottomBar = true
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
                        isVisibleBottomBar = true
                    }
                    composable(route = RouteName.HISTORY) {
                        HistoryPage()
                        isVisibleBottomBar = true
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
                        isVisibleBottomBar = false
                    }
                    composable(
                        route = RouteName.EDIT_HABITS + "/{params}",
                        arguments = listOf(navArgument("params") { type = NavType.StringType })
                    ) {
                        val args =
                            it.arguments?.getString("params")?.fromJson<EditHabitsPageParams>()
                        args?.let { (habitId, templateId) ->
                            EditHabitPage(habitId, templateId,
                                onOpenChooseIconModalBottomSheet = { initValue, callback ->
                                    scope.launch {
                                        modalBottomSheetType =
                                            ModalBottomSheetState.ChooseIcon(initValue, callback)
                                        bottomSheetState.show()
                                    }
                                },
                                onOpenChooseIconColorModalBottomSheet = { initValue, callback ->
                                    scope.launch {
                                        modalBottomSheetType =
                                            ModalBottomSheetState.ChooseColor(initValue, callback)
                                        bottomSheetState.show()
                                    }
                                },
                                onOpenChooseEventDayModalBottomSheet = { initValue, callback ->
                                    scope.launch {
                                        modalBottomSheetType =
                                            ModalBottomSheetState.ChooseEventDays(
                                                initValue,
                                                callback
                                            )
                                        bottomSheetState.show()
                                    }
                                },
                                onOpenChooseTimeStartModalBottomSheet = { initValue, minValue, maxValue, callback ->
                                    scope.launch {
                                        modalBottomSheetType =
                                            ModalBottomSheetState.ChooseTimeStart(
                                                initValue,
                                                minValue,
                                                maxValue,
                                                callback
                                            )
                                        bottomSheetState.show()
                                    }
                                },
                                onOpenChooseTimeEndModalBottomSheet = { initValue, minValue, maxValue, callback ->
                                    scope.launch {
                                        modalBottomSheetType =
                                            ModalBottomSheetState.ChooseTimeEnd(
                                                initValue,
                                                minValue,
                                                maxValue,
                                                callback
                                            )
                                        bottomSheetState.show()
                                    }
                                },
                                onOpenChooseFinishDateModalBottomSheet = { initValue, callback ->
                                    scope.launch {
                                        modalBottomSheetType =
                                            ModalBottomSheetState.ChooseDate(initValue, callback)
                                        bottomSheetState.show()
                                    }
                                },
                                onOpenChooseTargetType = { initValue, callback ->
                                    scope.launch {
                                        modalBottomSheetType =
                                            ModalBottomSheetState.ChooseTargetType(
                                                initValue,
                                                callback
                                            )
                                        bottomSheetState.show()
                                    }
                                },
                                onOpenChooseRepeatCount = { initValue, callback ->
                                    scope.launch {
                                        modalBottomSheetType =
                                            ModalBottomSheetState.ChooseRepeatCount(
                                                initValue,
                                                callback
                                            )
                                        bottomSheetState.show()
                                    }
                                },
                                onOpenChooseDuration = { initValue, callback ->
                                    scope.launch {
                                        modalBottomSheetType = ModalBottomSheetState.ChooseDuration(
                                            initValue,
                                            callback
                                        )
                                        bottomSheetState.show()
                                    }
                                },
                                onSaveHabit = {
                                    // Save Habit
                                    navController.navigateTo(
                                        route = navController.currentBackStack.value[1].destination.route.toString(),
                                        navConfig = {
                                            it.popUpTo(navController.currentBackStack.value[1].destination.route.toString()) {
                                                inclusive = false
                                            }
                                            it.launchSingleTop = true
                                        }
                                    )
                                }
                            )
                        }
                        isVisibleBottomBar = false

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
                        isVisibleBottomBar = false
                    }
                    composable(route = RouteName.SETTINGS) {
                        SettingsPage(onThemeChange, onLanguageChange)
                        isVisibleBottomBar = false
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
                        isVisibleBottomBar = false
                    }
                }
            }
        )
    }
}

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultHTrackerEnterTransition(
    initial: NavBackStackEntry,
    target: NavBackStackEntry,
): EnterTransition {
    val initialNavGraph = initial.destination.hostNavGraph

    val targetNavGraph = target.destination.hostNavGraph
    if (initialNavGraph.id != targetNavGraph.id) {
        return fadeIn()
    }
    return fadeIn() + slideIntoContainer(AnimatedContentScope.SlideDirection.Up)
}

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultHTrackerExitTransition(
    initial: NavBackStackEntry,
    target: NavBackStackEntry,
): ExitTransition {
    if (initial.destination.route == RouteName.SPLASH) return ExitTransition.None

    val initialNavGraph = initial.destination.hostNavGraph
    val targetNavGraph = target.destination.hostNavGraph
    if (initialNavGraph.id != targetNavGraph.id) {
        return fadeOut()
    }
    return fadeOut() + slideOutOfContainer(AnimatedContentScope.SlideDirection.Up)
}

private val NavDestination.hostNavGraph: NavGraph
    get() = hierarchy.first { it is NavGraph } as NavGraph

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultHTrackerPopEnterTransition(): EnterTransition {
    return fadeIn() + slideIntoContainer(AnimatedContentScope.SlideDirection.Down)
}

@ExperimentalAnimationApi
private fun AnimatedContentScope<*>.defaultHTrackerPopExitTransition(): ExitTransition {
    return fadeOut() + slideOutOfContainer(AnimatedContentScope.SlideDirection.Down)
}


