package com.glebalekseevjk.habit.ui.page.edit_habits

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import com.glebalekseevjk.habit.ui.theme.*
import com.glebalekseevjk.habit.ui.widgets.CustomTextField
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun EditHabitPage(
    habitId: Int? = null,
    templateId: Int? = null,
    onOpenChooseIconModalBottomSheet: ((Int) -> Unit) -> Unit,
    onOpenChooseIconColorModalBottomSheet: ((Int) -> Unit) -> Unit,
    onOpenChooseEventDayModalBottomSheet: ((Set<Habit.Companion.Day>) -> Unit) -> Unit,
    onOpenChooseTimeModalBottomSheet: ((LocalTime) -> Unit) -> Unit,
    onOpenChooseFinishDateModalBottomSheet: ((LocalDate) -> Unit) -> Unit,
    onSaveHabit: () -> Unit,
    viewModel: EditHabitViewModel = hiltViewModel()
) {
    val viewStates = viewModel.viewStates

    LaunchedEffect(Unit){
        println("================= viewmodel=${viewModel} viewModel.viewStates=${viewModel.viewStates}")
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

            Column(Modifier.padding(horizontal = LargePadding)) {
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
                    modifier = Modifier.padding(bottom = SmallPadding)
                )

                Text(
                    text = when (habit.habitType) {
                        Habit.Companion.HabitType.REGULAR -> stringResource(id = R.string.regular_text)
                        Habit.Companion.HabitType.HARMFUL -> stringResource(id = R.string.harmful_text)
                        Habit.Companion.HabitType.DISPOSABLE -> stringResource(id = R.string.disposable_text)
                    },
                    color = AppTheme.colors.colorOnPrimary,
                    style = typography.labelSmall,
                    modifier = Modifier.padding(top = ExtraSmallPadding)
                )


                // Выбор иконки и цвета -> (Нижняя панель)
                Row(
                    Modifier
                        .padding(top = SmallPadding)
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
                                onOpenChooseIconModalBottomSheet {
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
                                onOpenChooseIconColorModalBottomSheet {
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

                // Повторение
                Column(
                    modifier = Modifier
                        .padding(top = MiddlePadding)
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
                                onOpenChooseEventDayModalBottomSheet {
                                    viewModel.dispatch(EditHabitViewAction.SetDaysOfRepeat(it))
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
                            text = habit.daysOfRepeat.sorted().joinToString(", "),
                            color = AppTheme.colors.colorOnPrimary,
                            style = typography.titleSmall
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

                // Выполнить в интервале
                Column(
                    modifier = Modifier
                        .padding(top = MiddlePadding)
                        .fillMaxWidth()
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
                        Text(
                            text = habit.startExecutionInterval.toString(),
                            color = AppTheme.colors.colorAccent,
                            style = typography.titleLarge,
                            modifier = Modifier
                                .clickable(
                                    indication = rememberRipple(
                                        bounded = true,
                                        radius = (32 + 8).dp,
                                        color = AppTheme.colors.colorOnPrimary
                                    ),
                                    interactionSource = remember {
                                        MutableInteractionSource()
                                    }
                                ) {
                                    onOpenChooseTimeModalBottomSheet {
                                        viewModel.dispatch(
                                            EditHabitViewAction.SetStartExecutionInterval(
                                                it
                                            )
                                        )
                                    }
                                }
                                .padding(ExtraSmallPadding)
                        )
                        Text(
                            text = " и ",
                            color = AppTheme.colors.colorOnPrimary,
                            style = typography.titleMedium
                        )
                        Text(
                            text = habit.endExecutionInterval.toString(),
                            color = AppTheme.colors.colorAccent,
                            style = typography.titleLarge,
                            modifier = Modifier
                                .clickable(
                                    indication = rememberRipple(
                                        bounded = true,
                                        radius = (32 + 8).dp,
                                        color = AppTheme.colors.colorOnPrimary
                                    ),
                                    interactionSource = remember {
                                        MutableInteractionSource()
                                    }
                                ) {
                                    onOpenChooseTimeModalBottomSheet {
                                        viewModel.dispatch(
                                            EditHabitViewAction.SetEndExecutionInterval(
                                                it
                                            )
                                        )
                                    }
                                }
                                .padding(ExtraSmallPadding)
                        )

                    }
                }


                // Окончание
                Column(
                    modifier = Modifier
                        .padding(top = MiddlePadding)
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
                                onOpenChooseFinishDateModalBottomSheet {
                                    viewModel.dispatch(EditHabitViewAction.SetDeadline(it))
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
                        Text(
                            text = "май 18, 2023",
                            color = AppTheme.colors.colorOnPrimary,
                            style = typography.titleSmall
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

                // Создать привычку

                Button(
                    onClick = {
                        viewModel.dispatch(EditHabitViewAction.SaveHabit)
                        onSaveHabit()
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
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
        }
    }
}