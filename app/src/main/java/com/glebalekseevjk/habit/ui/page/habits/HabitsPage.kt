package com.glebalekseevjk.habit.ui.page.habits

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.domain.entity.Habit
import com.glebalekseevjk.habit.domain.entity.Habit.Companion.HabitType.*
import com.glebalekseevjk.habit.ui.theme.*
import com.glebalekseevjk.habit.ui.utils.pxToDp
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.max


@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class, ExperimentalLayoutApi::class
)
@Composable
fun HabitsPage(
    onNavigateToAddHabits: () -> Unit,
    onNavigateToEditHabits: (Int) -> Unit,
    viewModel: HabitsViewModel = hiltViewModel()
) {
    val viewStates = viewModel.viewStates

    LaunchedEffect(Unit) {
        if (viewStates is HabitsViewState.Init) {
            viewModel.dispatch(HabitsViewAction.InitHabits)
        }
    }

    when (viewStates) {
        HabitsViewState.Failure -> {
            TODO("Ошибка")
        }
        HabitsViewState.Empty -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.25f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = stringResource(id = R.string.empty_word),
                    color = AppTheme.colors.colorOnPrimary,
                    style = typography.headlineLarge.copy(fontSize = 48.sp)
                )
            }
        }
        is HabitsViewState.Loading, is HabitsViewState.Init -> {
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
        is HabitsViewState.Loaded -> {
            LazyColumn(
                state = viewStates.lazyListState,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(MiddlePadding)
            ) {
                items(viewStates.habitList) { habitUI ->
                    var elementHeight by remember {
                        mutableStateOf(0)
                    }

                    Card(
                        modifier = Modifier
                            .padding(vertical = ExtraSmallPadding)
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = AppTheme.colors.colorSecondary,
                            contentColor = AppTheme.colors.colorOnPrimary
                        ),
                        shape = RoundedCornerShape(ExtraSmallPadding),
                        elevation = CardDefaults.cardElevation(
                        ),
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = MiddlePadding)
                                .padding(top = MiddlePadding)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {


                            Row(
                                modifier = Modifier,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Row(
                                    modifier = Modifier.weight(1f),
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                when (habitUI.habit.habitType) {
                                                    REGULAR -> green500
                                                    HARMFUL -> red500
                                                    DISPOSABLE -> blue500
                                                }
                                            )
                                            .width(ExtraSmallPadding / 2)
                                            .height(elementHeight.pxToDp())
                                    ) {}
                                    Column(modifier = Modifier
                                        .padding(horizontal = MiddlePadding)
                                        .onSizeChanged {
                                            if (elementHeight != it.height) {
                                                elementHeight = max(it.height, elementHeight)
                                            }
                                        }
                                    ) {
                                        Text(
                                            text = habitUI.habit.title + when (habitUI.habit.targetType) {
                                                Habit.Companion.TargetType.OFF -> ""
                                                Habit.Companion.TargetType.REPEAT -> " — ${habitUI.habit.repeatCount} " + stringResource(
                                                    id = R.string.times
                                                )
                                                Habit.Companion.TargetType.DURATION -> " — ${
                                                    habitUI.habit.duration!!.format(
                                                        DateTimeFormatter.ofPattern(
                                                            stringResource(
                                                                id = R.string.time_pattern
                                                            )
                                                        )
                                                    )
                                                }"
                                            },
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier,
                                            style = typography.titleMedium
                                        )
                                        FlowRow(
                                            modifier = Modifier.fillMaxWidth(),
                                        ) {
                                            Card(
                                                shape = RoundedCornerShape(ExtraSmallPadding / 2),
                                                colors = when (habitUI.habit.habitType) {
                                                    REGULAR -> CardDefaults.cardColors(
                                                        contentColor = AppTheme.colors.colorRegularTag,
                                                        containerColor = AppTheme.colors.colorRegularTag.copy(alpha = 0.1f)
                                                    )
                                                    HARMFUL -> CardDefaults.cardColors(
                                                        contentColor = AppTheme.colors.colorHarmfulTag,
                                                        containerColor = AppTheme.colors.colorHarmfulTag.copy(alpha = 0.1f)
                                                    )
                                                    DISPOSABLE -> CardDefaults.cardColors(
                                                        contentColor = AppTheme.colors.colorDisposableTag,
                                                        containerColor = AppTheme.colors.colorDisposableTag.copy(alpha = 0.1f)
                                                    )
                                                },
                                                modifier = Modifier
                                                    .padding(end = SmallPadding)
                                                    .padding(top = ExtraSmallPadding)
                                            ) {
                                                Text(
                                                    text = when (habitUI.habit.habitType) {
                                                        REGULAR -> stringResource(id = R.string.regular)
                                                        HARMFUL -> stringResource(id = R.string.harmful)
                                                        DISPOSABLE -> stringResource(id = R.string.disposable)
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
                                                    REGULAR -> CardDefaults.cardColors(
                                                        contentColor = AppTheme.colors.colorRegularTag,
                                                        containerColor = AppTheme.colors.colorRegularTag.copy(alpha = 0.1f)
                                                    )
                                                    HARMFUL -> CardDefaults.cardColors(
                                                        contentColor = AppTheme.colors.colorHarmfulTag,
                                                        containerColor = AppTheme.colors.colorHarmfulTag.copy(alpha = 0.1f)
                                                    )
                                                    DISPOSABLE -> CardDefaults.cardColors(
                                                        contentColor = AppTheme.colors.colorDisposableTag,
                                                        containerColor = AppTheme.colors.colorDisposableTag.copy(alpha = 0.1f)
                                                    )
                                                },
                                                modifier = Modifier
                                                    .padding(end = SmallPadding)
                                                    .padding(top = ExtraSmallPadding)
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
                                                    REGULAR -> CardDefaults.cardColors(
                                                        contentColor = AppTheme.colors.colorRegularTag,
                                                        containerColor = AppTheme.colors.colorRegularTag.copy(alpha = 0.1f)
                                                    )
                                                    HARMFUL -> CardDefaults.cardColors(
                                                        contentColor = AppTheme.colors.colorHarmfulTag,
                                                        containerColor = AppTheme.colors.colorHarmfulTag.copy(alpha = 0.1f)
                                                    )
                                                    DISPOSABLE -> CardDefaults.cardColors(
                                                        contentColor = AppTheme.colors.colorDisposableTag,
                                                        containerColor = AppTheme.colors.colorDisposableTag.copy(alpha = 0.1f)
                                                    )
                                                },
                                                modifier = Modifier
                                                    .padding(top = ExtraSmallPadding)
                                            ) {
                                                Text(
                                                    text = habitUI.habit.deadline!!.format(
                                                        DateTimeFormatter.ofPattern(
                                                            stringResource(
                                                                id = R.string.date_3_pattern
                                                            )
                                                        )
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
                                }
                                Box(
                                    modifier = Modifier
                                        .height(elementHeight.pxToDp()),
                                    contentAlignment = Alignment.Center) {
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
                                                .padding(ExtraSmallPadding)
                                                .size(
                                                    MiddleIconSize
                                                )
                                        )
                                    }
                                }


                            }


                            var widthDays by remember {
                                mutableStateOf(0)
                            }
                            Row(
                                modifier = Modifier
                                    .onSizeChanged {
                                        widthDays = it.width
                                    }
                                    .padding(ExtraSmallPadding)
                                    .padding(top = MiddlePadding)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                val currentDate = LocalDate.now()
                                val list = listOf(
                                    currentDate.minusDays(3),
                                    currentDate.minusDays(2),
                                    currentDate.minusDays(1),
                                    currentDate,
                                    currentDate.plusDays(1),
                                    currentDate.plusDays(2),
                                    currentDate.plusDays(3)
                                )

                                list.forEach {
                                    val date = it
                                    val eventNotification =
                                        habitUI.eventNotificationList.find { it.date.toLocalDate() == date }

                                    val isBad = date.toEpochDay() < currentDate.toEpochDay()
                                    val isDone = eventNotification?.isDone ?: false
                                    val isTarget = eventNotification != null
                                    val isDeadline = date == habitUI.habit.deadline
                                    val isCurrentDate = date == currentDate

                                    val widthDay = widthDays / 8
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.width(widthDay.pxToDp())
                                    ) {
                                        Card(
                                            shape = RoundedCornerShape(ExtraSmallPadding),
                                            colors = CardDefaults.cardColors(
                                                containerColor = when {
                                                    isCurrentDate -> AppTheme.colors.colorOnPrimary
                                                    isDeadline -> AppTheme.colors.colorDeadline
                                                    else -> transparent
                                                },
                                                contentColor = when {
                                                    isCurrentDate -> AppTheme.colors.colorPrimary
                                                    isDeadline -> AppTheme.colors.colorPrimary
                                                    else -> AppTheme.colors.colorOnPrimary
                                                },
                                            ),
                                            modifier = Modifier
                                                .size(LargePadding)
                                                .padding(bottom = ExtraSmallPadding),
                                        ) {
                                            Box(
                                                modifier = Modifier.fillMaxSize(),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = when (it.dayOfWeek!!) {
                                                        DayOfWeek.MONDAY -> stringResource(id = R.string.weekday_mon).lowercase()
                                                            .capitalize()
                                                        DayOfWeek.TUESDAY -> stringResource(id = R.string.weekday_tue).lowercase()
                                                            .capitalize()
                                                        DayOfWeek.WEDNESDAY -> stringResource(id = R.string.weekday_wed).lowercase()
                                                            .capitalize()
                                                        DayOfWeek.THURSDAY -> stringResource(id = R.string.weekday_thu).lowercase()
                                                            .capitalize()
                                                        DayOfWeek.FRIDAY -> stringResource(id = R.string.weekday_fri).lowercase()
                                                            .capitalize()
                                                        DayOfWeek.SATURDAY -> stringResource(id = R.string.weekday_sat).lowercase()
                                                            .capitalize()
                                                        DayOfWeek.SUNDAY -> stringResource(id = R.string.weekday_sun).lowercase()
                                                            .capitalize()
                                                    },
                                                    style = typography.labelMedium,
                                                    textDecoration = if (isTarget) TextDecoration.Underline else null
                                                )
                                            }
                                        }


                                        Card(
                                            onClick = {
                                                viewModel.dispatch(
                                                    HabitsViewAction.ToggleIsDoneEventNotification(
                                                        eventNotification!!
                                                    )
                                                )
                                            },
                                            enabled = isTarget && isBad || isTarget && isCurrentDate,
                                            shape = RoundedCornerShape((widthDay/4).pxToDp()),
                                            colors = CardDefaults.cardColors(
                                                containerColor = when {
                                                    isDone -> AppTheme.colors.colorIsDone
                                                    isTarget && isBad -> AppTheme.colors.colorIsBad
                                                    else -> AppTheme.colors.colorPrimary
                                                },
                                                contentColor = when {
                                                    isDeadline -> if (isBad) AppTheme.colors.colorIsBad else AppTheme.colors.colorDeadline
                                                    else -> AppTheme.colors.colorOnPrimary
                                                },
                                                disabledContainerColor = when {
                                                    isDone -> AppTheme.colors.colorIsDone
                                                    isTarget && isBad -> AppTheme.colors.colorIsBad
                                                    else -> AppTheme.colors.colorPrimary
                                                },
                                                disabledContentColor = when {
                                                    isDeadline -> if (isBad) AppTheme.colors.colorIsBad else AppTheme.colors.colorDeadline
                                                    else -> AppTheme.colors.colorOnPrimary
                                                },
                                            ),
                                            border = if (isTarget) BorderStroke(
                                                1.dp,
                                                AppTheme.colors.colorOnPrimary
                                            ) else null,
                                            modifier = Modifier
                                                .size(widthDay.pxToDp())
                                        ) {
                                            Box(
                                                modifier = Modifier.fillMaxSize(),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = it.dayOfMonth.toString(),
                                                    style = typography.titleMedium,
                                                    modifier = Modifier
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            Divider(
                                thickness = 2.dp,
                                color = AppTheme.colors.colorDivider,
                                modifier = Modifier.padding(top = ExtraSmallPadding / 2)
                            )

                            // Прогресс (% 1/10), Редактирование
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Row(
                                    modifier = Modifier
                                        .weight(1f),
                                    verticalAlignment = Alignment.CenterVertically

                                ) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_progress_check),
                                        contentDescription = stringResource(id = R.string.progress),
                                        tint = green500,
                                        modifier = Modifier
                                            .size(SmallIconSize)
                                    )
                                    val countIsDone =
                                        habitUI.eventNotificationList.filter { it.isDone }.size
                                    val all = habitUI.eventNotificationList.size
                                    val percent =
                                        (if (countIsDone == 0) 0 else countIsDone / all.toDouble() * 100).toInt()
                                    Text(
                                        text = "$percent%",
                                        color = AppTheme.colors.colorProgress,
                                        style = typography.labelMedium,
                                        modifier = Modifier.padding(start = SmallPadding)
                                    )
                                    Text(
                                        text = "$countIsDone/$all",
                                        color = AppTheme.colors.colorProgress,
                                        style = typography.labelSmall,
                                        modifier = Modifier.padding(start = SmallPadding)
                                    )

                                }

                                IconButton(onClick = { onNavigateToEditHabits(habitUI.habit.id) }) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_edit),
                                        contentDescription = "Редактирование",
                                        tint = AppTheme.colors.colorOnPrimary,
                                        modifier = Modifier
                                            .size(SmallIconSize)
                                    )
                                }


                            }

                        }

                    }


                }

                item() {
                    Row(Modifier
                        .padding(vertical = SmallPadding)
                    ) {
                        Button(
                            onClick = onNavigateToAddHabits,
                            modifier = Modifier,
                            shape = mediumRoundedCornerShape,
                            contentPadding = PaddingValues(
                                vertical = SmallPadding,
                                horizontal = LargePadding
                            ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AppTheme.colors.colorOnPrimary,
                                contentColor = AppTheme.colors.colorPrimary
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.create_new_habit).toUpperCase(),
                                style = typography.titleSmall,
                                color = AppTheme.colors.colorPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}