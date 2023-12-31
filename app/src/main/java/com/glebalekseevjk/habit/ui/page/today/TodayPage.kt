package com.glebalekseevjk.habit.ui.page.today

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.domain.entity.Habit
import com.glebalekseevjk.habit.ui.page.habits.HabitsViewState
import com.glebalekseevjk.habit.ui.theme.*
import com.glebalekseevjk.habit.ui.widgets.AutoResizeText
import com.glebalekseevjk.habit.ui.widgets.CustomFlowRow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
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
                Text(text = stringResource(id = R.string.empty_word), color = AppTheme.colors.colorOnPrimary, style = typography.headlineLarge.copy(fontSize = 48.sp))
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

                items(viewStates.habitList) { (habitUI, event) ->
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
//                                .height(SmallIconSize + MiddlePadding*2),
                            verticalAlignment = Alignment.Top,
                        ) {
                            Card(
                                shape = RoundedCornerShape(SmallPadding),
                                colors = CardDefaults.cardColors(
                                    contentColor = Color(habitUI.habit.colorRGBA),
                                    containerColor = Color(habitUI.habit.colorRGBA).copy(alpha = 0.2f).compositeOver(Color.White)
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
                                CustomFlowRow(
                                    modifier = Modifier,
                                    horizontalGap = ExtraSmallPadding,
                                    verticalGap = ExtraSmallPadding
                                ) {
                                    Card(
                                        shape = RoundedCornerShape(ExtraSmallPadding / 2),
                                        colors = when (habitUI.habit.habitType) {
                                            Habit.Companion.HabitType.REGULAR -> CardDefaults.cardColors(
                                                contentColor = AppTheme.colors.colorRegularTag,
                                                containerColor = AppTheme.colors.colorRegularTag.copy(alpha = 0.1f)
                                            )
                                            Habit.Companion.HabitType.HARMFUL -> CardDefaults.cardColors(
                                                contentColor = AppTheme.colors.colorHarmfulTag,
                                                containerColor = AppTheme.colors.colorHarmfulTag.copy(alpha = 0.1f)
                                            )
                                            Habit.Companion.HabitType.DISPOSABLE -> CardDefaults.cardColors(
                                                contentColor = AppTheme.colors.colorDisposableTag,
                                                containerColor = AppTheme.colors.colorDisposableTag.copy(alpha = 0.1f)
                                            )
                                        },
                                        modifier = Modifier
//                                            .padding(end = SmallPadding)
//                                            .padding(bottom = ExtraSmallPadding)
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

                                    Card(
                                        shape = RoundedCornerShape(ExtraSmallPadding / 2),
                                        colors = when (habitUI.habit.habitType) {
                                            Habit.Companion.HabitType.REGULAR -> CardDefaults.cardColors(
                                                contentColor = AppTheme.colors.colorRegularTag,
                                                containerColor = AppTheme.colors.colorRegularTag.copy(alpha = 0.1f)
                                            )
                                            Habit.Companion.HabitType.HARMFUL -> CardDefaults.cardColors(
                                                contentColor = AppTheme.colors.colorHarmfulTag,
                                                containerColor = AppTheme.colors.colorHarmfulTag.copy(alpha = 0.1f)
                                            )
                                            Habit.Companion.HabitType.DISPOSABLE -> CardDefaults.cardColors(
                                                contentColor = AppTheme.colors.colorDisposableTag,
                                                containerColor = AppTheme.colors.colorDisposableTag.copy(alpha = 0.1f)
                                            )
                                        },
                                        modifier = Modifier
//                                            .padding(end = SmallPadding)
//                                            .padding(bottom = ExtraSmallPadding)
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
                                                contentColor = AppTheme.colors.colorRegularTag,
                                                containerColor = AppTheme.colors.colorRegularTag.copy(alpha = 0.1f)
                                            )
                                            Habit.Companion.HabitType.HARMFUL -> CardDefaults.cardColors(
                                                contentColor = AppTheme.colors.colorHarmfulTag,
                                                containerColor = AppTheme.colors.colorHarmfulTag.copy(alpha = 0.1f)
                                            )
                                            Habit.Companion.HabitType.DISPOSABLE -> CardDefaults.cardColors(
                                                contentColor = AppTheme.colors.colorDisposableTag,
                                                containerColor = AppTheme.colors.colorDisposableTag.copy(alpha = 0.1f)
                                            )
                                        },
                                        modifier = Modifier
//                                            .padding(bottom = ExtraSmallPadding)
                                    ) {
                                        Text(
                                            text = habitUI.habit.deadline!!.format(
                                                DateTimeFormatter.ofPattern(stringResource(id = R.string.date_3_pattern))
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


                            Box(contentAlignment = Alignment.Center, modifier = Modifier
                                .padding(bottom = ExtraSmallPadding)
                                .height(SmallIconSize + MiddlePadding*2)) {
                                Text(
                                    text = "$percent%",
                                    style = typography.labelSmall,
                                    modifier = Modifier
                                        .offset(0.dp, ExtraSmallPadding)
                                        .align(Alignment.BottomCenter),
                                    color = AppTheme.colors.colorProgress
                                )
                                Card(
                                    onClick = {
                                        viewModel.dispatch(
                                            TodayViewAction.ToggleIsDoneEventNotification(
                                                event
                                            )
                                        )
                                    },
                                    colors = CardDefaults.cardColors(
                                        contentColor = if (event.isDone) AppTheme.colors.colorProgress else AppTheme.colors.colorOnPrimary,
                                        containerColor = if (event.isDone) AppTheme.colors.colorProgress.copy(alpha = 0.2f).compositeOver(Color.White) else AppTheme.colors.colorOnPrimary.copy(
                                            alpha = 0.2f
                                        ).compositeOver(Color.White)
                                    ),
                                    shape = RoundedCornerShape(ExtraSmallPadding),
                                    modifier = Modifier

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
                                                .padding(ExtraSmallPadding/2)
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