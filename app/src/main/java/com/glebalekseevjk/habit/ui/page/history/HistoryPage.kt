package com.glebalekseevjk.habit.ui.page.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.himanshoe.kalendar.ui.Kalendar
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.domain.entity.Habit
import com.glebalekseevjk.habit.ui.external.kalendar.common.data.KalendarEventCounter
import com.glebalekseevjk.habit.ui.theme.*
import com.glebalekseevjk.habit.ui.utils.pxToDp
import java.lang.Integer.min
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Stats(
    val title: String,
    val value: String,
    val subtitle: String,
    val subValue: String,
    val containerColor: Color,
)

@Composable
fun HistoryPage(
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val viewStates = viewModel.viewStates

    var currentDate by rememberSaveable {
        mutableStateOf(LocalDate.now().toEpochDay())
    }

    LaunchedEffect(Unit) {
        if (viewStates is HistoryViewState.Init) {
            viewModel.dispatch(HistoryViewAction.InitHistory)
        }
    }

    when (viewStates) {
        HistoryViewState.Failure -> {
            TODO("Ошибка")
        }
        is HistoryViewState.Loading, is HistoryViewState.Init -> {
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
        is HistoryViewState.Loaded -> {
            val stats = listOf(
                Stats(
                    stringResource(id = R.string.current_series),
                    viewStates.currentSeries.toString(),
                    stringResource(id = R.string.best_series),
                    viewStates.bestSeries.toString(),
                    containerColor = Color(0xFF7e8a97),
                ),
                Stats(
                    stringResource(id = R.string.number_completed_habits),
                    viewStates.numberCompletedHabits.toString(),
                    stringResource(id = R.string.number_completed_habits_for_last_week),
                    viewStates.numberCompletedHabitsForLastWeek.toString(),
                    containerColor = Color(0xFFf77a4e),
                ),
                Stats(
                    stringResource(id = R.string.percentage_of_completed),
                    "${viewStates.percentageOfCompleted}%",
                    stringResource(id = R.string.number_habits),
                    "${viewStates.numberCompletedHabits} / ${viewStates.numberHabits}",
                    containerColor = Color(0xFF4e5778),
                ),
                Stats(
                    stringResource(id = R.string.number_perfect_days),
                    viewStates.numberPerfectDays.toString(),
                    stringResource(id = R.string.number_perfect_days_for_last_week),
                    viewStates.numberPerfectDaysForLastWeek.toString(),
                    containerColor = Color(0xFF54a79c),
                ),
            )


            LazyColumn(
                state = viewStates.lazyColumnListState,
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(MiddlePadding)
            ) {
                item {
                    LazyRow(
                        state = viewStates.lazyRowListState,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(stats) {
                            Card(
                                modifier = Modifier
                                    .padding(ExtraSmallPadding)
                                    .height(IntrinsicSize.Max)
                                    ,
                                colors = CardDefaults.cardColors(
                                    containerColor = it.containerColor,
                                    contentColor = AppTheme.colors.colorPrimary
                                ),
                                shape = RoundedCornerShape(ExtraSmallPadding)
                            ) {
                                Column(modifier = Modifier.padding(SmallPadding)) {
                                    Text(
                                        text = it.title.toUpperCase(),
                                        style = typography.titleLarge.copy(fontSize = 21.sp)
                                    )
                                    Text(
                                        text = it.value,
                                        style = typography.titleLarge.copy(fontSize = 72.sp)
                                    )
                                    Text(
                                        text = it.subtitle + " " + it.subValue,
                                        style = typography.labelSmall.copy(fontSize = 11.sp)
                                    )
                                }
                            }
                        }
                    }
                }
                val allEvents =
                    viewStates.habitList.flatMap { it.eventNotificationList }.filter { it.isDone }
                val events = allEvents.groupBy { it.date.toLocalDate() }
                item {
                    Kalendar(
                        selectedDay = LocalDate.ofEpochDay(currentDate),
                        onCurrentDayClick = { date, _ ->
                            currentDate = date.toEpochDay()
                        },
                        kalendarEventCounterList = events.map {
                            KalendarEventCounter(
                                it.key,
                                it.value.size
                            )
                        },
                        modifier = Modifier
                            .padding(vertical = SmallPadding)
                            .padding(horizontal = ExtraSmallPadding)
                    )
                }

                val listEventNothingsForDate =
                    events[LocalDate.ofEpochDay(currentDate)] ?: emptyList()
                val grouped = listEventNothingsForDate.groupBy { it.habitId }
                val habitList =
                    grouped.map { map -> viewStates.habitList.find { it.habit.id == map.key }!! to map.value }

                items(habitList) { (habitUI, events) ->
                    val isDoneList =
                        habitUI.eventNotificationList.filter { it.isDone }
                    val countIsDoneBeforeToday = isDoneList.filter { it.date.isBefore(LocalDate.ofEpochDay(currentDate).atStartOfDay()) }.size
                    val all = habitUI.eventNotificationList.size
                    val percentOld =
                        (if (countIsDoneBeforeToday == 0) 0 else countIsDoneBeforeToday / all.toDouble() * 100).toInt()

                    var percentStep = if (all != 0) 100 / all else 0

                    if (isDoneList.size == all) percentStep = 100 - percentOld

                    val percentNow =
                        (if (countIsDoneBeforeToday == 0) 0 else countIsDoneBeforeToday / all.toDouble() * 100).toInt() + percentStep

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
                                        Habit.Companion.TargetType.REPEAT -> " — ${habitUI.habit.repeatCount} " + stringResource(
                                            id = R.string.times
                                        )
                                        Habit.Companion.TargetType.DURATION -> " — ${
                                            habitUI.habit.duration!!.format(
                                                DateTimeFormatter.ofPattern(stringResource(id = R.string.time_pattern))
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
                                    Row(
                                        modifier = Modifier,
                                        verticalAlignment = Alignment.CenterVertically

                                    ) {
                                        Icon(
                                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_progress_check),
                                            contentDescription = stringResource(id = R.string.progress),
                                            tint = green500,
                                            modifier = Modifier
                                                .size(ExtraSmallIconSize)
                                        )
                                        Text(
                                            text = "${percentOld}% → ${percentNow}%",
                                            color = green500,
                                            style = typography.labelSmall,
                                            modifier = Modifier.padding(
                                                start = ExtraSmallPadding / 2,
                                                end = SmallPadding
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
                                            text = when (habitUI.habit.habitType) {
                                                Habit.Companion.HabitType.REGULAR -> stringResource(
                                                    id = R.string.regular
                                                )
                                                Habit.Companion.HabitType.HARMFUL -> stringResource(
                                                    id = R.string.harmful
                                                )
                                                Habit.Companion.HabitType.DISPOSABLE -> stringResource(
                                                    id = R.string.disposable
                                                )
                                            },
                                            style = typography.labelSmall,
                                            modifier = Modifier.padding(
                                                horizontal = ExtraSmallPadding,
                                                vertical = ExtraSmallPadding / 2
                                            )
                                        )
                                    }
                                }
                            }



                            Column(
                                modifier = Modifier,
                                horizontalAlignment = Alignment.End,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = "+$percentStep%", color = green500, style = typography.titleLarge)
                            }


                        }
                    }

                }
            }


        }
    }
}