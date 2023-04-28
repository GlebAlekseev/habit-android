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
import com.glebalekseevjk.habit.ui.page.add_habits.AddHabitViewModel
import com.glebalekseevjk.habit.ui.theme.*
import com.glebalekseevjk.habit.ui.widgets.CustomTextField

@Composable
fun EditHabitPage(
    habitId: Int? = null,
    templateId: Int? = null,
    onOpenChooseIconModalBottomSheet: () -> Unit,
    onOpenChooseIconColorModalBottomSheet: () -> Unit,
    onOpenChooseEventDayModalBottomSheet: () -> Unit,
    onOpenChooseTimeModalBottomSheet: () -> Unit,
    onOpenChooseFinishDateModalBottomSheet: () -> Unit,
    onSaveHabit: () -> Unit,
    viewModel: EditHabitViewModel = hiltViewModel()
) {
    // Поле ввода -> текстовый ввод
    var habitTitle by remember { mutableStateOf(TextFieldValue("")) }
    Column(Modifier.padding(horizontal = LargePadding)) {
        CustomTextField(
            value = habitTitle,
            onValueChange = { newText ->
                habitTitle = newText
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
//        Text(
//            text = "Регулярная привычка",
//            color = AppTheme.colors.colorOnPrimary,
//            style = typography.labelSmall,
//            modifier = Modifier.padding(top = ExtraSmallPadding)
//        )


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
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_sleep),
                contentDescription = "Вставайте рано",
                modifier = Modifier
                    .padding(
                        start = LargePadding,
                        end = MiddlePadding,
                        top = MiddlePadding,
                        bottom = MiddlePadding
                    )
                    .size(LargeIconSize),
                tint = Color(0xFFFFC46C)
            )
            Column(
                modifier = Modifier
//                    .padding(start = LargePadding)
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
                        onOpenChooseIconModalBottomSheet()
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
                        onOpenChooseIconColorModalBottomSheet()
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
                        onOpenChooseEventDayModalBottomSheet()
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
                    text = "Каждый день",
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
                    text = "19:00",
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
                            onOpenChooseTimeModalBottomSheet()
                        }
                        .padding(ExtraSmallPadding)
                )
                Text(
                    text = " и ",
                    color = AppTheme.colors.colorOnPrimary,
                    style = typography.titleMedium
                )
                Text(
                    text = "23:00",
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
                            onOpenChooseTimeModalBottomSheet()
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
                        onOpenChooseFinishDateModalBottomSheet()
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