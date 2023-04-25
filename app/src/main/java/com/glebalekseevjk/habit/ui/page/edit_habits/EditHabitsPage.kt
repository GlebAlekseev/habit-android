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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.ui.theme.*
import com.glebalekseevjk.habit.ui.widgets.CustomTextField

@Composable
fun EditHabitsPage(
    habitId: Int? = null,
    templateId: Int? = null,
    onOpenChooseIconModalBottomSheet: () -> Unit,
    onOpenChooseIconColorModalBottomSheet: () -> Unit,
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
            modifier = Modifier
        )
        Text(
            text = "Регулярная привычка",
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


        // Выполнить в интервале


        // Окончание


    }



}