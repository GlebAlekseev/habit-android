package com.glebalekseevjk.habit.ui.widgets.modal_bottom_sheet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.glebalekseevjk.habit.ui.theme.*

val colorList = listOf(
    Color.Blue,
    Color.Cyan,
    Color.Red,
    Color.Green,
    Color.Magenta,
    Color.Yellow,
    Color.LightGray,
    Color.Gray,
    Color.Black,
).windowed(3, 3)


@Composable
fun ChooseColorModalBottomSheet(callback: (Int)->Unit) {
    Column(
        modifier = Modifier
            .padding(MiddlePadding),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Выбор цвета значка",
            color = AppTheme.colors.colorOnPrimary,
            style = typography.titleMedium,
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height((256 + 32 + 16).dp)
                .padding(vertical = MiddlePadding)
        ) {
            items(colorList) { colorRow ->

                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    colorRow.forEach {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(SmallPadding)
                                .border(
                                    border = BorderStroke(16.dp, it.copy(alpha = 0.4f)),
                                    shape = RoundedCornerShape(MiddlePadding)
                                )
                                .padding(ExtraSmallPadding)
                                .background(it, shape = RoundedCornerShape(MiddlePadding))
                                .size(96.dp)
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
                onClick = {
//                          callback()
                },
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