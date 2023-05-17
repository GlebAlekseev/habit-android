package com.glebalekseevjk.habit.ui.page.edit_habits

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.domain.entity.Habit
import com.glebalekseevjk.habit.domain.entity.Habit.Companion.HabitType.*
import com.glebalekseevjk.habit.domain.entity.Habit.Companion.TargetType
import com.glebalekseevjk.habit.ui.theme.*
import com.glebalekseevjk.habit.ui.widgets.AutoResizeText
import com.glebalekseevjk.habit.ui.widgets.CustomTextField
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditHabitPage(
    habitId: Int? = null,
    templateId: Int? = null,
    onOpenChooseIconModalBottomSheet: (Int, ((Int) -> Unit)) -> Unit,
    onOpenChooseIconColorModalBottomSheet: (Int, ((Int) -> Unit)) -> Unit,
    onOpenChooseEventDayModalBottomSheet: (Set<Habit.Companion.Day>, ((Set<Habit.Companion.Day>) -> Unit)) -> Unit,
    onOpenChooseTimeStartModalBottomSheet: (LocalTime?, LocalTime?, LocalTime?, ((LocalTime) -> Unit)) -> Unit,
    onOpenChooseTimeEndModalBottomSheet: (LocalTime?, LocalTime?, LocalTime?, ((LocalTime) -> Unit)) -> Unit,
    onOpenChooseFinishDateModalBottomSheet: (LocalDate?, ((LocalDate) -> Unit)) -> Unit,
    onOpenChooseTargetType: (TargetType, ((TargetType) -> Unit)) -> Unit,
    onOpenChooseRepeatCount: (Int?, ((Int) -> Unit)) -> Unit,
    onOpenChooseDuration: (LocalTime?, ((LocalTime) -> Unit)) -> Unit,
    onSaveHabit: () -> Unit,
    viewModel: EditHabitViewModel = hiltViewModel()
) {
    val viewStates = viewModel.viewStates
    val focusManager = LocalFocusManager.current
    var isFocusTextField by rememberSaveable {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit) {
        if (viewStates is EditHabitViewState.Init) {
            viewModel.dispatch(EditHabitViewAction.InitParams(habitId, templateId))
        }
    }

    BackHandler(isFocusTextField) {
        focusManager.clearFocus()
        isFocusTextField = false
    }

    when (viewStates) {
        EditHabitViewState.Failure -> TODO("Ошибка")
        is EditHabitViewState.Init, is EditHabitViewState.Loading -> {
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
        is EditHabitViewState.Loaded -> {
            val habit = viewStates.habit
            var titleTextField by remember {
                mutableStateOf(TextFieldValue(habit.title))
            }
            val focusRequester = remember {
                FocusRequester()
            }

            LaunchedEffect(Unit) {
                if (isFocusTextField) {
                    delay(100)
                    try {
                        focusRequester.requestFocus()
                        isFocusTextField = true
                    }catch (_: Exception){}
                }
            }

            LazyColumn(
                state = viewStates.lazyListState,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        CustomTextField(
                            value = titleTextField,
                            onValueChange = { newText ->
                                titleTextField = newText
                                viewModel.dispatch(EditHabitViewAction.SetTitle(newText.text.trim()))
                            },
                            placeholder = {
                                Text(
                                    text = stringResource(id = R.string.title),
                                    color = AppTheme.colors.colorOnPrimary,
                                    style = typography.labelLarge,
                                )
                            },
                            textStyle = typography.labelLarge,
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = AppTheme.colors.colorOnPrimary,
                                backgroundColor = AppTheme.colors.colorPrimary,
                                cursorColor = AppTheme.colors.colorCursor,
                                focusedIndicatorColor = AppTheme.colors.colorOnPrimary,
                                unfocusedIndicatorColor = AppTheme.colors.colorOnPrimary,
                            ),
                            modifier = Modifier
                                .focusRequester(focusRequester)
                                .padding(bottom = SmallPadding)
                                .padding(horizontal = LargePadding)
                                .onFocusChanged {
                                    if (it.isFocused) {
                                        isFocusTextField = true
                                    }
                                }
                        )
                    }
                }

                item {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = when (habit.habitType) {
                                REGULAR -> stringResource(id = R.string.regular_text)
                                HARMFUL -> stringResource(id = R.string.harmful_text)
                                DISPOSABLE -> stringResource(id = R.string.disposable_text)
                            },
                            color = AppTheme.colors.colorOnPrimary,
                            style = typography.labelSmall,
                            modifier = Modifier
                                .padding(top = ExtraSmallPadding)
                                .padding(horizontal = LargePadding)
                        )
                    }

                }

                item {
                    // Выбор иконки и цвета -> (Нижняя панель)
                    Row(
                        Modifier
                            .padding(top = SmallPadding)
                            .padding(horizontal = LargePadding)
                            .background(
                                AppTheme.colors.colorSecondary,
                                shape = smallRoundedCornerShape
                            )
                            .fillMaxWidth(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = habit.iconId),
                            contentDescription = habit.title,
                            modifier = Modifier
                                .padding(
                                    start = LargePadding,
                                    end = MiddlePadding,
                                    top = MiddlePadding,
                                    bottom = MiddlePadding
                                )
                                .size(LargeIconSize),
                            tint = Color(habit.colorRGBA)
                        )
                        Column(
                            modifier = Modifier
                        ) {
                            Card(
                                onClick = {
                                    focusManager.clearFocus()
                                    isFocusTextField = false
                                    onOpenChooseIconModalBottomSheet(habit.iconId) {
                                        viewModel.dispatch(EditHabitViewAction.SetIconId(it))
                                    }
                                },
                                colors = CardDefaults.cardColors(
                                    contentColor = AppTheme.colors.colorOnPrimary,
                                    containerColor = transparent
                                ),
                                shape = RoundedCornerShape(ExtraSmallPadding),
                                ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = LargePadding)
                                        .padding(vertical = SmallPadding),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.icon),
                                        color = AppTheme.colors.colorOnPrimary,
                                        style = typography.titleMedium
                                    )
                                    Icon(
                                        ImageVector.vectorResource(id = R.drawable.ic_back),
                                        stringResource(id = R.string.back),
                                        tint = AppTheme.colors.colorOnPrimary,
                                        modifier = Modifier
                                            .padding(end = SmallPadding)
                                            .size(SmallIconSize / 2, SmallIconSize)
                                            .rotate(180f)
                                    )
                                }
                            }

                            Divider(modifier = Modifier.padding(start = MiddlePadding))
                            Card(
                                onClick = {
                                    focusManager.clearFocus()
                                    isFocusTextField = false
                                    onOpenChooseIconColorModalBottomSheet(habit.colorRGBA) {
                                        viewModel.dispatch(EditHabitViewAction.SetColorRGBA(it))
                                    }
                                },
                                shape = RoundedCornerShape(ExtraSmallPadding),
                                colors = CardDefaults.cardColors(
                                    contentColor = AppTheme.colors.colorOnPrimary,
                                    containerColor = transparent
                                ),
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = LargePadding)
                                        .padding(vertical = SmallPadding),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.color),
                                        color = AppTheme.colors.colorOnPrimary,
                                        style = typography.titleMedium
                                    )
                                    Icon(
                                        ImageVector.vectorResource(id = R.drawable.ic_back),
                                        stringResource(id = R.string.back),
                                        tint = AppTheme.colors.colorOnPrimary,
                                        modifier = Modifier
                                            .padding(end = SmallPadding)
                                            .size(SmallIconSize / 2, SmallIconSize)
                                            .rotate(180f)
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    when (habit.habitType) {
                        DISPOSABLE -> {}
                        REGULAR, HARMFUL -> {
                            // Повторение
                            Column(
                                modifier = Modifier
                                    .padding(top = MiddlePadding)
                                    .padding(horizontal = LargePadding)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = stringResource(id = R.string.repeat).toUpperCase(),
                                    color = AppTheme.colors.colorOnPrimary,
                                    style = typography.titleMedium
                                )
                                Card(
                                    onClick = {
                                        focusManager.clearFocus()
                                        isFocusTextField = false
                                        onOpenChooseEventDayModalBottomSheet(habit.daysOfRepeat) {
                                            viewModel.dispatch(
                                                EditHabitViewAction.SetDaysOfRepeat(
                                                    it
                                                )
                                            )
                                        }
                                    },
                                    shape = RoundedCornerShape(ExtraSmallPadding),
                                    colors = CardDefaults.cardColors(
                                        contentColor = AppTheme.colors.colorOnPrimary,
                                        containerColor = transparent
                                    ),
                                    modifier = Modifier.padding(top = MiddlePadding)
                                ) {
                                    val nameDays = habit.daysOfRepeat.sorted().map {
                                        when (it) {
                                            Habit.Companion.Day.MONDAY -> stringResource(id = R.string.weekday_mon).toLowerCase()
                                            Habit.Companion.Day.TUESDAY -> stringResource(id = R.string.weekday_tue).toLowerCase()
                                            Habit.Companion.Day.WEDNESDAY -> stringResource(id = R.string.weekday_wed).toLowerCase()
                                            Habit.Companion.Day.THURSDAY -> stringResource(id = R.string.weekday_thu).toLowerCase()
                                            Habit.Companion.Day.FRIDAY -> stringResource(id = R.string.weekday_fri).toLowerCase()
                                            Habit.Companion.Day.SATURDAY -> stringResource(id = R.string.weekday_sat).toLowerCase()
                                            Habit.Companion.Day.SUNDAY -> stringResource(id = R.string.weekday_sun).toLowerCase()
                                        }
                                    }.joinToString(", ")
                                    Row(
                                        modifier = Modifier
                                            .background(
                                                AppTheme.colors.colorSecondary,
                                                shape = smallRoundedCornerShape
                                            )
                                            .padding(
                                                vertical = MiddlePadding
                                            )
                                            .padding(start = MiddlePadding)
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.habits_day),
                                            color = AppTheme.colors.colorOnPrimary,
                                            style = typography.titleMedium
                                        )

                                        key(nameDays) {
                                            Box(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .padding(ExtraSmallPadding),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                AutoResizeText(
                                                    text = nameDays,
                                                    color = AppTheme.colors.colorOnPrimary,
                                                    style = typography.labelSmall
                                                )
                                            }
                                        }

                                        Icon(
                                            ImageVector.vectorResource(id = R.drawable.ic_back),
                                            stringResource(id = R.string.back),
                                            tint = AppTheme.colors.colorOnPrimary,
                                            modifier = Modifier
                                                .padding(end = SmallPadding)
                                                .size(SmallIconSize / 2, SmallIconSize)
                                                .rotate(180f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    when (habit.habitType) {
                        HARMFUL -> {}
                        REGULAR, DISPOSABLE -> {

                            // Выполнить в интервале
                            Column(
                                modifier = Modifier
                                    .padding(top = MiddlePadding)
                                    .padding(horizontal = LargePadding)
                                    .fillMaxWidth(),
                            ) {
                                Text(
                                    text = stringResource(id = R.string.execute_in_interval).toUpperCase(),
                                    color = AppTheme.colors.colorOnPrimary,
                                    style = typography.titleMedium
                                )
                                Row(
                                    modifier = Modifier
                                        .padding(top = MiddlePadding)
                                        .background(
                                            AppTheme.colors.colorSecondary,
                                            shape = smallRoundedCornerShape
                                        )
                                        .padding(
                                            vertical = MiddlePadding
                                        )
                                        .padding(start = MiddlePadding)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.between) + " ",
                                        color = AppTheme.colors.colorOnPrimary,
                                        style = typography.titleMedium
                                    )
                                    Card(
                                        onClick = {
                                            focusManager.clearFocus()
                                            isFocusTextField = false
                                            onOpenChooseTimeStartModalBottomSheet(
                                                habit.startExecutionInterval,
                                                null,
                                                habit.endExecutionInterval
                                            ) {
                                                viewModel.dispatch(
                                                    EditHabitViewAction.SetStartExecutionInterval(
                                                        it
                                                    )
                                                )
                                            }
                                        },
                                        shape = RoundedCornerShape(ExtraSmallPadding),
                                        colors = CardDefaults.cardColors(
                                            containerColor = AppTheme.colors.colorOnPrimary,
                                            contentColor = AppTheme.colors.colorPrimary
                                        ),
                                        modifier = Modifier.padding(horizontal = ExtraSmallPadding/2)
                                    ) {
                                        Text(
                                            text = habit.startExecutionInterval.toString(),
                                            style = typography.titleMedium,
                                            color = AppTheme.colors.colorPrimary,
                                            modifier = Modifier.padding(ExtraSmallPadding / 2)
                                        )
                                    }

                                    Text(
                                        text = " " + stringResource(id = R.string.and) + " ",
                                        color = AppTheme.colors.colorOnPrimary,
                                        style = typography.titleMedium
                                    )
                                    Card(
                                        onClick = {
                                            focusManager.clearFocus()
                                            isFocusTextField = false
                                            onOpenChooseTimeEndModalBottomSheet(
                                                habit.endExecutionInterval,
                                                habit.startExecutionInterval,
                                                null
                                            ) {
                                                viewModel.dispatch(
                                                    EditHabitViewAction.SetEndExecutionInterval(
                                                        it
                                                    )
                                                )
                                            }
                                        },
                                        shape = RoundedCornerShape(ExtraSmallPadding),
                                        colors = CardDefaults.cardColors(
                                            containerColor = AppTheme.colors.colorOnPrimary,
                                            contentColor = AppTheme.colors.colorPrimary
                                        ),
                                        modifier = Modifier.padding(horizontal = ExtraSmallPadding/2)
                                    ) {
                                        Text(
                                            text = habit.endExecutionInterval.toString(),
                                            style = typography.titleMedium,
                                            color = AppTheme.colors.colorPrimary,
                                            modifier = Modifier.padding(ExtraSmallPadding / 2)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    when (habit.habitType) {
                        REGULAR, HARMFUL, DISPOSABLE -> {
                            // Окончание
                            Column(
                                modifier = Modifier
                                    .padding(top = MiddlePadding)
                                    .padding(horizontal = LargePadding)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = stringResource(id = R.string.deadline).toUpperCase(),
                                    color = AppTheme.colors.colorOnPrimary,
                                    style = typography.titleMedium
                                )
                                Card(
                                    onClick = {
                                        focusManager.clearFocus()
                                        isFocusTextField = false
                                        onOpenChooseFinishDateModalBottomSheet(habit.deadline) {
                                            viewModel.dispatch(
                                                EditHabitViewAction.SetDeadline(
                                                    it
                                                )
                                            )
                                        }
                                    },
                                    shape = RoundedCornerShape(ExtraSmallPadding),
                                    colors = CardDefaults.cardColors(
                                        containerColor = AppTheme.colors.colorSecondary,
                                        contentColor = AppTheme.colors.colorOnPrimary
                                    ),
                                    modifier = Modifier
                                        .padding(top = MiddlePadding)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .padding(
                                                vertical = MiddlePadding
                                            )
                                            .padding(start = MiddlePadding)
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.deadline_title),
                                            color = AppTheme.colors.colorOnPrimary,
                                            style = typography.titleMedium
                                        )
                                        habit.deadline?.let {
                                            Box(modifier = Modifier
                                                .weight(1f)
                                                .padding(ExtraSmallPadding),
                                                contentAlignment = Alignment.Center
                                            ){
                                                AutoResizeText(
                                                    text = it.format(
                                                        DateTimeFormatter.ofPattern(
                                                            stringResource(id = R.string.date_2_pattern)
                                                        )
                                                    )
                                                        .toString(),
                                                    color = AppTheme.colors.colorOnPrimary,
                                                    style = typography.titleSmall,
                                                )
                                            }

                                        }

                                        Icon(
                                            ImageVector.vectorResource(id = R.drawable.ic_back),
                                            stringResource(id = R.string.back),
                                            tint = AppTheme.colors.colorOnPrimary,
                                            modifier = Modifier
                                                .padding(end = SmallPadding)
                                                .size(SmallIconSize / 2, SmallIconSize)
                                                .rotate(180f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    when (habit.habitType) {
                        DISPOSABLE -> {}
                        REGULAR, HARMFUL -> {
                            //    val targetType: TargetType = TargetType.OFF,
                            //    val repeatCount: Int?,
                            //    val duration: LocalTime?,
                            // Цель на день
                            Column(
                                modifier = Modifier
                                    .padding(top = MiddlePadding)
                                    .padding(horizontal = LargePadding)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = stringResource(id = R.string.daily_goal).toUpperCase(),
                                    color = AppTheme.colors.colorOnPrimary,
                                    style = typography.titleMedium
                                )
                                Card(
                                    onClick = {
                                        focusManager.clearFocus()
                                        isFocusTextField = false
                                        onOpenChooseTargetType(habit.targetType) {
                                            viewModel.dispatch(
                                                EditHabitViewAction.SetTargetType(
                                                    it
                                                )
                                            )
                                        }
                                    },
                                    shape = RoundedCornerShape(ExtraSmallPadding),
                                    colors = CardDefaults.cardColors(
                                        containerColor = AppTheme.colors.colorSecondary,
                                        contentColor = AppTheme.colors.colorOnPrimary
                                    ),
                                    modifier = Modifier
                                        .padding(top = MiddlePadding)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .padding(
                                                vertical = MiddlePadding
                                            )
                                            .padding(start = MiddlePadding)
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.target),
                                            color = AppTheme.colors.colorOnPrimary,
                                            style = typography.titleMedium
                                        )
                                        Box(modifier = Modifier
                                            .weight(1f)
                                            .padding(ExtraSmallPadding),
                                            contentAlignment = Alignment.Center
                                        ){
                                            AutoResizeText(
                                                text = when (habit.targetType) {
                                                    TargetType.OFF -> stringResource(id = R.string.target_type_off)
                                                    TargetType.REPEAT -> stringResource(id = R.string.target_type_repeat)
                                                    TargetType.DURATION -> stringResource(id = R.string.target_type_duration)
                                                },
                                                color = AppTheme.colors.colorOnPrimary,
                                                style = typography.titleSmall,
                                            )
                                        }

                                        Icon(
                                            ImageVector.vectorResource(id = R.drawable.ic_back),
                                            stringResource(id = R.string.back),
                                            tint = AppTheme.colors.colorOnPrimary,
                                            modifier = Modifier
                                                .padding(end = SmallPadding)
                                                .size(SmallIconSize / 2, SmallIconSize)
                                                .rotate(180f)
                                        )
                                    }
                                }
                            }
                            when (habit.targetType) {
                                TargetType.OFF -> {}
                                TargetType.REPEAT -> {
                                    Card(
                                        onClick = {
                                            focusManager.clearFocus()
                                            isFocusTextField = false
                                            onOpenChooseRepeatCount(habit.repeatCount) {
                                                viewModel.dispatch(
                                                    EditHabitViewAction.SetRepeatCount(
                                                        it
                                                    )
                                                )
                                            }
                                        },
                                        shape = RoundedCornerShape(ExtraSmallPadding),
                                        colors = CardDefaults.cardColors(
                                            containerColor = AppTheme.colors.colorSecondary,
                                            contentColor = AppTheme.colors.colorOnPrimary
                                        ),
                                        modifier = Modifier
                                            .padding(horizontal = LargePadding)
                                            .padding(top = MiddlePadding)
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .padding(
                                                    vertical = MiddlePadding
                                                )
                                                .padding(start = MiddlePadding)
                                                .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = stringResource(id = R.string.choose_repeat_count),
                                                color = AppTheme.colors.colorOnPrimary,
                                                style = typography.titleMedium
                                            )
                                            habit.repeatCount?.let {
                                                Box(modifier = Modifier
                                                    .weight(1f)
                                                    .padding(ExtraSmallPadding),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    AutoResizeText(
                                                        text = it.toString(),
                                                        color = AppTheme.colors.colorOnPrimary,
                                                        style = typography.titleSmall,
                                                    )

                                                }
                                            }

                                            Icon(
                                                ImageVector.vectorResource(id = R.drawable.ic_back),
                                                stringResource(id = R.string.back),
                                                tint = AppTheme.colors.colorOnPrimary,
                                                modifier = Modifier
                                                    .padding(end = SmallPadding)
                                                    .size(SmallIconSize / 2, SmallIconSize)
                                                    .rotate(180f)
                                            )
                                        }
                                    }
                                }
                                TargetType.DURATION -> {
                                    Card(
                                        onClick = {
                                            focusManager.clearFocus()
                                            isFocusTextField = false
                                            onOpenChooseDuration(habit.duration) {
                                                viewModel.dispatch(
                                                    EditHabitViewAction.SetDuration(
                                                        it
                                                    )
                                                )
                                            }
                                        },
                                        shape = RoundedCornerShape(ExtraSmallPadding),
                                        colors = CardDefaults.cardColors(
                                            containerColor = AppTheme.colors.colorSecondary,
                                            contentColor = AppTheme.colors.colorOnPrimary
                                        ),
                                        modifier = Modifier
                                            .padding(horizontal = LargePadding)
                                            .padding(top = MiddlePadding)
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .background(
                                                    AppTheme.colors.colorSecondary,
                                                    shape = smallRoundedCornerShape
                                                )
                                                .padding(
                                                    vertical = MiddlePadding
                                                )
                                                .padding(start = MiddlePadding)
                                                .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = stringResource(id = R.string.target_type_duration),
                                                color = AppTheme.colors.colorOnPrimary,
                                                style = typography.titleMedium
                                            )
                                            habit.duration?.let {
                                                Box(modifier = Modifier
                                                    .weight(1f)
                                                    .padding(ExtraSmallPadding),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    AutoResizeText(
                                                        text = it.format(
                                                            DateTimeFormatter.ofPattern(
                                                                "hh ч. mm м."
                                                            )
                                                        ),
                                                        color = AppTheme.colors.colorOnPrimary,
                                                        style = typography.titleSmall,
                                                    )
                                                }
                                            }

                                            Icon(
                                                ImageVector.vectorResource(id = R.drawable.ic_back),
                                                stringResource(id = R.string.back),
                                                tint = AppTheme.colors.colorOnPrimary,
                                                modifier = Modifier
                                                    .padding(end = SmallPadding)
                                                    .size(SmallIconSize / 2, SmallIconSize)
                                                    .rotate(180f)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

                item {
                    // Создать привычку
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        if (habitId != null) {
                            Button(
                                onClick = {
                                    viewModel.dispatch(EditHabitViewAction.RemoveHabit)
                                    onSaveHabit()
                                },
                                modifier = Modifier
                                    .padding(horizontal = LargePadding)
                                    .padding(top = MiddlePadding),
                                shape = mediumRoundedCornerShape,
                                contentPadding = PaddingValues(
                                    vertical = SmallPadding,
                                    horizontal = LargePadding
                                ),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = red500,
                                    contentColor = AppTheme.colors.colorPrimary
                                )
                            ) {
                                Text(
                                    text = stringResource(id = R.string.remove).toUpperCase(),
                                    style = typography.titleSmall,
                                    color = AppTheme.colors.colorPrimary
                                )
                            }
                        }

                        Button(
                            enabled = !viewStates.habit.title.isEmpty(),
                            onClick = {
                                viewModel.dispatch(EditHabitViewAction.SaveHabit)
                                onSaveHabit()
                            },
                            modifier = Modifier
                                .padding(horizontal = LargePadding)
                                .padding(top = MiddlePadding),
                            shape = mediumRoundedCornerShape,
                            contentPadding = PaddingValues(
                                vertical = SmallPadding,
                                horizontal = LargePadding
                            ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AppTheme.colors.colorOnPrimary,
                                contentColor = AppTheme.colors.colorPrimary,
                                disabledContentColor = AppTheme.colors.colorPrimary.copy(alpha = 0.2f),
                                disabledContainerColor = AppTheme.colors.colorOnPrimary.copy(alpha = 0.2f),
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.save).toUpperCase(),
                                style = typography.titleSmall,
                                color = AppTheme.colors.colorPrimary
                            )
                        }
                    }

                }

                item {
                    Row(modifier = Modifier.padding(vertical = SmallPadding)) {}
                }
            }
        }
    }
}