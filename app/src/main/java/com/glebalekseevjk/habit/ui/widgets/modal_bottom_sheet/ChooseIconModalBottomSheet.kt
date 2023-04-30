package com.glebalekseevjk.habit.ui.widgets.modal_bottom_sheet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.ui.theme.*

val iconList = listOf(
    R.drawable.ic_edit,
    R.drawable.ic_back,
    R.drawable.ic_glass,
    R.drawable.ic_sleep,
    R.drawable.ic_lang,
    R.drawable.ic_history,
    R.drawable.ic_habits,
    R.drawable.ic_disposable,
    R.drawable.ic_night,
).windowed(3, 3)

@Composable
fun ChooseIconModalBottomSheet(callback: (Int)->Unit) {
    Column(
        modifier = Modifier
            .padding(MiddlePadding),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Выбор иконки",
            color = AppTheme.colors.colorOnPrimary,
            style = typography.titleMedium,
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height((256 + 32 + 16).dp)
                .padding(vertical = MiddlePadding)
        ) {
            items(iconList) { iconRow ->

                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    iconRow.forEach {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = it),
                            contentDescription = "Редактировать",
                            modifier = Modifier
                                .weight(1f)
                                .padding(SmallPadding)
                                .size(64.dp),
                            tint = AppTheme.colors.colorOnPrimary
                        )
                    }
                }

            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(
                onClick = { },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier,
                contentPadding = PaddingValues(
                    vertical = MiddlePadding,
                    horizontal = LargePadding
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.colors.colorOnPrimary,
                    contentColor = AppTheme.colors.colorPrimary
                )
            ) {
                Text(
                    text = "Отмена".toUpperCase(),
                    style = typography.titleMedium,
                    color = AppTheme.colors.colorPrimary
                )
            }
            Button(
                onClick = {},
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier,
                contentPadding = PaddingValues(
                    vertical = MiddlePadding,
                    horizontal = LargePadding
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.colors.colorOnPrimary,
                    contentColor = AppTheme.colors.colorPrimary
                )
            ) {
                Text(
                    text = "Сохранить".toUpperCase(),
                    style = typography.titleMedium,
                    color = AppTheme.colors.colorPrimary
                )
            }
        }
    }
}