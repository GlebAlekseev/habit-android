package com.glebalekseevjk.habit.ui.page.edit_habits

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.glebalekseevjk.habit.ui.widgets.CustomTextField
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

    LaunchedEffect(Unit) {
        viewModel.dispatch(EditHabitViewAction.InitParams(habitId, templateId))
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
                                viewModel.dispatch(EditHabitViewAction.SetTitle(newText.text))
                            },
                            placeholder = {
                                Text(
                                    text = "Название",
                                    color = AppTheme.colors.colorOnPrimary,
                                    style = typography.labelLarge
                                )
                            },
                            textStyle = typography.labelLarge,
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = AppTheme.colors.colorOnPrimary,
                                backgroundColor = AppTheme.colors.colorPrimary,
                                cursorColor = AppTheme.colors.colorAccent,
                                focusedIndicatorColor = AppTheme.colors.colorOnPrimary,
                                unfocusedIndicatorColor = AppTheme.colors.colorOnPrimary,
                            ),
                            modifier = Modifier
                                .padding(bottom = SmallPadding)
                                .padding(horizontal = LargePadding)
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
                                AppTheme.colors.colorOnPrimary.copy(alpha = 0.1f),
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
                            Row(modifier = Modifier
                                .clickable(
                                    indication = rememberRipple(
                                        bounded = true,
                                        radius = 128.dp,
                                        color = AppTheme.colors.colorOnPrimary
                                    ),
                                    interactionSource = remember {
                                        MutableInteractionSource()
                                    }
                                ) {
                                    onOpenChooseIconModalBottomSheet(habit.iconId) {
                                        viewModel.dispatch(EditHabitViewAction.SetIconId(it))
                                    }
                                }
                                .fillMaxWidth()
                                .padding(start = LargePadding)
                                .padding(vertical = SmallPadding),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Значок",
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
                            Divider(modifier = Modifier.padding(start = MiddlePadding))
                            Row(modifier = Modifier
                                .clickable(
                                    indication = rememberRipple(
                                        bounded = true,
                                        radius = 128.dp,
                                        color = AppTheme.colors.colorOnPrimary
                                    ),
                                    interactionSource = remember {
                                        MutableInteractionSource()
                                    }
                                ) {
                                    onOpenChooseIconColorModalBottomSheet(habit.colorRGBA) {
                                        viewModel.dispatch(EditHabitViewAction.SetColorRGBA(it))
                                    }
                                }
                                .fillMaxWidth()
                                .padding(start = LargePadding)
                                .padding(vertical = SmallPadding),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Цвет",
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
                                    text = "Повторение".toUpperCase(),
                                    color = AppTheme.colors.colorOnPrimary,
                                    style = typography.titleMedium
                                )
                                Row(
                                    modifier = Modifier
                                        .padding(top = MiddlePadding)
                                        .clickable(
                                            indication = rememberRipple(
                                                bounded = true,
                                                radius = (128 + 32 + 16).dp,
                                                color = AppTheme.colors.colorOnPrimary
                                            ),
                                            interactionSource = remember {
                                                MutableInteractionSource()
                                            }
                                        ) {
                                            onOpenChooseEventDayModalBottomSheet(habit.daysOfRepeat) {
                                                viewModel.dispatch(
                                                    EditHabitViewAction.SetDaysOfRepeat(
                                                        it
                                                    )
                                                )
                                            }
                                        }
                                        .background(
                                            AppTheme.colors.colorOnPrimary.copy(alpha = 0.1f),
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
                                        text = "Дни привычек",
                                        color = AppTheme.colors.colorOnPrimary,
                                        style = typography.titleMedium
                                    )
                                    Text(
                                        text = habit.daysOfRepeat.sorted().map {
                                            when (it) {
                                                Habit.Companion.Day.MONDAY -> "пн"
                                                Habit.Companion.Day.TUESDAY -> "вт"
                                                Habit.Companion.Day.WEDNESDAY -> "ср"
                                                Habit.Companion.Day.THURSDAY -> "чт"
                                                Habit.Companion.Day.FRIDAY -> "пт"
                                                Habit.Companion.Day.SATURDAY -> "сб"
                                                Habit.Companion.Day.SUNDAY -> "вс"
                                            }
                                        }.joinToString(", "),
                                        color = AppTheme.colors.colorOnPrimary,
                                        style = typography.labelSmall
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
                                    text = "Выполнить в интервале".toUpperCase(),
                                    color = AppTheme.colors.colorOnPrimary,
                                    style = typography.titleMedium
                                )
                                Row(
                                    modifier = Modifier
                                        .padding(top = MiddlePadding)
                                        .background(
                                            AppTheme.colors.colorOnPrimary.copy(alpha = 0.1f),
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
                                        text = "Между ",
                                        color = AppTheme.colors.colorOnPrimary,
                                        style = typography.titleMedium
                                    )
                                    Card(onClick = {
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
                                        colors =  CardDefaults.cardColors(
                                            containerColor = AppTheme.colors.colorOnPrimary,
                                            contentColor = AppTheme.colors.colorPrimary
                                        ),
                                        modifier = Modifier.padding(horizontal = SmallPadding)
                                    ) {
                                        Text(
                                            text = habit.startExecutionInterval.toString(),
                                            style = typography.titleLarge,
                                            color = AppTheme.colors.colorPrimary,
                                            modifier = Modifier.padding(ExtraSmallPadding)
                                        )
                                    }

                                    Text(
                                        text = " и ",
                                        color = AppTheme.colors.colorOnPrimary,
                                        style = typography.titleMedium
                                    )
                                    Card(onClick = {
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
                                        colors =  CardDefaults.cardColors(
                                            containerColor = AppTheme.colors.colorOnPrimary,
                                            contentColor = AppTheme.colors.colorPrimary
                                        ),
                                        modifier = Modifier.padding(horizontal = SmallPadding)
                                    ) {
                                        Text(
                                            text = habit.endExecutionInterval.toString(),
                                            style = typography.titleLarge,
                                            color = AppTheme.colors.colorPrimary,
                                            modifier = Modifier.padding(ExtraSmallPadding)
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
                                    text = "Окончание".toUpperCase(),
                                    color = AppTheme.colors.colorOnPrimary,
                                    style = typography.titleMedium
                                )
                                Row(
                                    modifier = Modifier
                                        .padding(top = MiddlePadding)
                                        .clickable(
                                            indication = rememberRipple(
                                                bounded = true,
                                                radius = (128 + 32 + 16).dp,
                                                color = AppTheme.colors.colorOnPrimary
                                            ),
                                            interactionSource = remember {
                                                MutableInteractionSource()
                                            }
                                        ) {
                                            onOpenChooseFinishDateModalBottomSheet(habit.deadline) {
                                                viewModel.dispatch(
                                                    EditHabitViewAction.SetDeadline(
                                                        it
                                                    )
                                                )
                                            }
                                        }
                                        .background(
                                            AppTheme.colors.colorOnPrimary.copy(alpha = 0.1f),
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
                                        text = "Последний день",
                                        color = AppTheme.colors.colorOnPrimary,
                                        style = typography.titleMedium
                                    )
                                    habit.deadline?.let {
                                        Text(
                                            text = it.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
                                                .toString(),
                                            color = AppTheme.colors.colorOnPrimary,
                                            style = typography.titleSmall
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
                                    text = "Цель на день".toUpperCase(),
                                    color = AppTheme.colors.colorOnPrimary,
                                    style = typography.titleMedium
                                )
                                Row(
                                    modifier = Modifier
                                        .padding(top = MiddlePadding)
                                        .clickable(
                                            indication = rememberRipple(
                                                bounded = true,
                                                radius = (128 + 32 + 16).dp,
                                                color = AppTheme.colors.colorOnPrimary
                                            ),
                                            interactionSource = remember {
                                                MutableInteractionSource()
                                            }
                                        ) {
                                            onOpenChooseTargetType(habit.targetType) {
                                                viewModel.dispatch(
                                                    EditHabitViewAction.SetTargetType(
                                                        it
                                                    )
                                                )
                                            }
                                        }
                                        .background(
                                            AppTheme.colors.colorOnPrimary.copy(alpha = 0.1f),
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
                                        text = "Цель",
                                        color = AppTheme.colors.colorOnPrimary,
                                        style = typography.titleMedium
                                    )
                                    Text(
                                        text = when (habit.targetType) {
                                            TargetType.OFF -> "Выкл"
                                            TargetType.REPEAT -> "Повторения"
                                            TargetType.DURATION -> "Длительность"
                                        },
                                        color = AppTheme.colors.colorOnPrimary,
                                        style = typography.titleSmall,
                                        modifier = Modifier.padding(start = LargePadding*3)
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

                                when (habit.targetType) {
                                    TargetType.OFF -> {}
                                    TargetType.REPEAT -> {
                                        Row(
                                            modifier = Modifier
                                                .padding(top = MiddlePadding)
                                                .clickable(
                                                    indication = rememberRipple(
                                                        bounded = true,
                                                        radius = (128 + 32 + 16).dp,
                                                        color = AppTheme.colors.colorOnPrimary
                                                    ),
                                                    interactionSource = remember {
                                                        MutableInteractionSource()
                                                    }
                                                ) {
                                                    onOpenChooseRepeatCount(habit.repeatCount) {
                                                        viewModel.dispatch(
                                                            EditHabitViewAction.SetRepeatCount(
                                                                it
                                                            )
                                                        )
                                                    }
                                                }
                                                .background(
                                                    AppTheme.colors.colorOnPrimary.copy(alpha = 0.1f),
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
                                                text = "Кол-во повторов",
                                                color = AppTheme.colors.colorOnPrimary,
                                                style = typography.titleMedium
                                            )
                                            habit.repeatCount?.let {
                                                Text(
                                                    text = it.toString(),
                                                    color = AppTheme.colors.colorOnPrimary,
                                                    style = typography.titleSmall,
                                                    modifier = Modifier.padding(start = MiddlePadding)
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
                                    TargetType.DURATION -> {
                                        Row(
                                            modifier = Modifier
                                                .padding(top = MiddlePadding)
                                                .clickable(
                                                    indication = rememberRipple(
                                                        bounded = true,
                                                        radius = (128 + 32 + 16).dp,
                                                        color = AppTheme.colors.colorOnPrimary
                                                    ),
                                                    interactionSource = remember {
                                                        MutableInteractionSource()
                                                    }
                                                ) {
                                                    onOpenChooseDuration(habit.duration) {
                                                        viewModel.dispatch(
                                                            EditHabitViewAction.SetDuration(
                                                                it
                                                            )
                                                        )
                                                    }
                                                }
                                                .background(
                                                    AppTheme.colors.colorOnPrimary.copy(alpha = 0.1f),
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
                                                text = "Длительность",
                                                color = AppTheme.colors.colorOnPrimary,
                                                style = typography.titleMedium
                                            )
                                            habit.duration?.let {
                                                Text(
                                                    text = it.format(DateTimeFormatter.ofPattern("hh ч. mm м.")),
                                                    color = AppTheme.colors.colorOnPrimary,
                                                    style = typography.titleSmall,
                                                    modifier = Modifier.padding(start = LargePadding)
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
                            }

                        }
                    }
                }

                item {
                    // Создать привычку
                    Button(
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
                            contentColor = AppTheme.colors.colorPrimary
                        )
                    ) {
                        Text(
                            text = "Сохранить".toUpperCase(),
                            style = typography.titleSmall,
                            color = AppTheme.colors.colorPrimary
                        )
                    }
                }

                item {
                    Row(modifier = Modifier.padding(vertical = SmallPadding)) {}
                }
            }
        }
    }
}