package com.glebalekseevjk.habit.ui.page.today

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.glebalekseevjk.habit.domain.entity.Habit
import com.glebalekseevjk.habit.ui.page.habits.HabitsViewState
import com.glebalekseevjk.habit.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodayPage(
    viewModel: TodayViewModel = hiltViewModel()
) {
    val viewStates = viewModel.viewStates

    LaunchedEffect(Unit) {
        if (viewStates is TodayViewState.Init) {
            viewModel.dispatch(TodayViewAction.InitToday)
        }
    }

    when (viewStates) {
        TodayViewState.Failure -> {
            TODO("Ошибка")
        }
        TodayViewState.Empty -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.25f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(text = "Пусто", color = AppTheme.colors.colorOnPrimary, style = typography.headlineLarge.copy(fontSize = 48.sp))
            }
        }
        is TodayViewState.Loading, is TodayViewState.Init -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.15f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                CircularProgressIndicator(
                    color = AppTheme.colors.colorOnPrimary,
                    strokeWidth = 2.dp
                )
            }
        }
        is TodayViewState.Loaded -> {
            LazyColumn(
                state = viewStates.lazyListState
            ) {

                // Выбрать ивенты на сегодня
                val eventsToday = viewStates.habitList.flatMap { it.eventNotificationList }
                    .filter { it.date.toLocalDate().isEqual(LocalDate.now()) }
                val grouped = eventsToday.groupBy { it.habitId }
                val itemsToday = grouped.map { map ->
                    Pair(
                        viewStates.habitList.filter { it.habit.id == map.key }.first(),
                        map.value.first()
                    )
                }

                items(itemsToday) { (habitUI, event) ->
                    val countIsDone =
                        habitUI.eventNotificationList.filter { it.isDone }.size
                    val all = habitUI.eventNotificationList.size
                    val percent =
                        (if (countIsDone == 0) 0 else countIsDone / all.toDouble() * 100).toInt()
                    Card(
                        modifier = Modifier.padding(MiddlePadding),
                        colors = CardDefaults.cardColors(
                            containerColor = transparent
                        ),

                        ) {

                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Card(
                                shape = RoundedCornerShape(SmallPadding),
                                colors = CardDefaults.cardColors(
                                    contentColor = Color(habitUI.habit.colorRGBA),
                                    containerColor = Color(habitUI.habit.colorRGBA).copy(alpha = 0.2f)
                                ),
                                modifier = Modifier
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = habitUI.habit.iconId),
                                    contentDescription = habitUI.habit.title,
                                    tint = Color(habitUI.habit.colorRGBA),
                                    modifier = Modifier
                                        .padding(MiddlePadding)
                                        .size(
                                            SmallIconSize
                                        )
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = MiddlePadding),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = habitUI.habit.title + when (habitUI.habit.targetType) {
                                        Habit.Companion.TargetType.OFF -> ""
                                        Habit.Companion.TargetType.REPEAT -> " — ${habitUI.habit.repeatCount} раз"
                                        Habit.Companion.TargetType.DURATION -> " — ${
                                            habitUI.habit.duration!!.format(
                                                DateTimeFormatter.ofPattern("h ч m мин")
                                            )
                                        }"
                                    },
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(bottom = ExtraSmallPadding),
                                    style = typography.titleMedium,
                                    color = AppTheme.colors.colorOnPrimary
                                )
                                Row(
                                    modifier = Modifier,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
//                                    Row(
//                                        modifier = Modifier,
//                                        verticalAlignment = Alignment.CenterVertically
//
//                                    ) {
//                                        Icon(
//                                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_progress_check),
//                                            contentDescription = "Прогресс",
//                                            tint = green500,
//                                            modifier = Modifier
//                                                .size(ExtraSmallIconSize)
//                                        )
//                                        Text(
//                                            text = "$percent%",
//                                            color = green500,
//                                            style = typography.labelSmall,
//                                            modifier = Modifier.padding(
//                                                start = ExtraSmallPadding / 2,
//                                                end = SmallPadding
//                                            )
//                                        )
//
//                                    }
                                    Card(
                                        shape = RoundedCornerShape(ExtraSmallPadding / 2),
                                        colors = when (habitUI.habit.habitType) {
                                            Habit.Companion.HabitType.REGULAR -> CardDefaults.cardColors(
                                                contentColor = green500,
                                                containerColor = green500.copy(alpha = 0.1f)
                                            )
                                            Habit.Companion.HabitType.HARMFUL -> CardDefaults.cardColors(
                                                contentColor = red500,
                                                containerColor = red500.copy(alpha = 0.1f)
                                            )
                                            Habit.Companion.HabitType.DISPOSABLE -> CardDefaults.cardColors(
                                                contentColor = blue500,
                                                containerColor = blue500.copy(alpha = 0.1f)
                                            )
                                        },
                                        modifier = Modifier.padding(end = SmallPadding)
                                    ) {
                                        Text(
                                            text = when (habitUI.habit.habitType) {
                                                Habit.Companion.HabitType.REGULAR -> "Регулярная"
                                                Habit.Companion.HabitType.HARMFUL -> "Вредная"
                                                Habit.Companion.HabitType.DISPOSABLE -> "Одноразовая"
                                            },
                                            style = typography.labelSmall,
                                            modifier = Modifier.padding(
                                                horizontal = ExtraSmallPadding,
                                                vertical = ExtraSmallPadding / 2
                                            )
                                        )
                                    }

                                    Card(
                                        shape = RoundedCornerShape(ExtraSmallPadding / 2),
                                        colors = when (habitUI.habit.habitType) {
                                            Habit.Companion.HabitType.REGULAR -> CardDefaults.cardColors(
                                                contentColor = green500,
                                                containerColor = green500.copy(alpha = 0.1f)
                                            )
                                            Habit.Companion.HabitType.HARMFUL -> CardDefaults.cardColors(
                                                contentColor = red500,
                                                containerColor = red500.copy(alpha = 0.1f)
                                            )
                                            Habit.Companion.HabitType.DISPOSABLE -> CardDefaults.cardColors(
                                                contentColor = blue500,
                                                containerColor = blue500.copy(alpha = 0.1f)
                                            )
                                        },
                                        modifier = Modifier.padding(end = SmallPadding)
                                    ) {
                                        Text(
                                            text = "${habitUI.habit.startExecutionInterval.toString()} - ${habitUI.habit.endExecutionInterval.toString()}",
                                            style = typography.labelSmall,
                                            modifier = Modifier.padding(
                                                horizontal = ExtraSmallPadding,
                                                vertical = ExtraSmallPadding / 2
                                            )
                                        )
                                    }

                                    Card(
                                        shape = RoundedCornerShape(ExtraSmallPadding / 2),
                                        colors = when (habitUI.habit.habitType) {
                                            Habit.Companion.HabitType.REGULAR -> CardDefaults.cardColors(
                                                contentColor = green500,
                                                containerColor = green500.copy(alpha = 0.1f)
                                            )
                                            Habit.Companion.HabitType.HARMFUL -> CardDefaults.cardColors(
                                                contentColor = red500,
                                                containerColor = red500.copy(alpha = 0.1f)
                                            )
                                            Habit.Companion.HabitType.DISPOSABLE -> CardDefaults.cardColors(
                                                contentColor = blue500,
                                                containerColor = blue500.copy(alpha = 0.1f)
                                            )
                                        },
                                    ) {
                                        Text(
                                            text = habitUI.habit.deadline!!.format(
                                                DateTimeFormatter.ofPattern("d MMMM")
                                            ),
                                            style = typography.labelSmall,
                                            modifier = Modifier.padding(
                                                horizontal = ExtraSmallPadding,
                                                vertical = ExtraSmallPadding / 2
                                            )
                                        )
                                    }
                                }
                            }


                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "$percent%",
                                    style = typography.labelSmall,
                                    modifier = Modifier.align(Alignment.BottomCenter),
                                    color = green500
                                )
                                Card(
                                    onClick = {
                                        viewModel.dispatch(
                                            TodayViewAction.ToggleIsDoneEventNotification(
                                                event.id
                                            )
                                        )
                                    },
                                    colors = CardDefaults.cardColors(
                                        contentColor = if (event.isDone) green500 else AppTheme.colors.colorOnPrimary,
                                        containerColor = if (event.isDone) green500.copy(alpha = 0.2f) else AppTheme.colors.colorOnPrimary.copy(
                                            alpha = 0.2f
                                        )
                                    ),
                                    shape = RoundedCornerShape(ExtraSmallPadding),
                                    modifier = Modifier.padding(vertical = SmallPadding)

                                ) {
                                    Box(
                                        modifier = Modifier,
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            tint = if (!event.isDone) transparent else green500,
                                            imageVector = Icons.Default.Done,
                                            contentDescription = "isDone",
                                            modifier = Modifier
                                                .padding(ExtraSmallPadding)
                                                .size(SmallIconSize)
                                        )
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}