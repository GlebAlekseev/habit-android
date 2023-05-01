package com.glebalekseevjk.habit.ui.widgets.modal_bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.ui.theme.*

val iconList = listOf(
    R.drawable.ic_regular,
    R.drawable.ic_harmful,
    R.drawable.ic_disposable,
    R.drawable.ic_categories_budget,
    R.drawable.ic_categories_eat,
    R.drawable.ic_categories_important,
    R.drawable.ic_categories_leisure,
    R.drawable.ic_categories_morning,
    R.drawable.ic_categories_pets,
    R.drawable.ic_categories_productivity,
    R.drawable.ic_categories_sleep,
    R.drawable.ic_categories_sports,
    R.drawable.ic_categories_trend,
    R.drawable.ic_categories_trend_1,
    R.drawable.ic_categories_trend_2,
    R.drawable.ic_categories_trend_3,
    R.drawable.ic_categories_trend_4,
    R.drawable.ic_categories_trend_5,
    R.drawable.ic_categories_trend_6,
)

@Composable
fun ChooseIconModalBottomSheet(initValue: Int, callback: (Int) -> Unit, onCancel: () -> Unit) {
    var currentIcon by rememberSaveable {
        mutableStateOf(initValue)
    }

    val lazyGridState = rememberLazyGridState()

    Column(
        modifier = Modifier
            .padding(MiddlePadding),
    ) {
        Text(
            text = "Выбор значка",
            color = AppTheme.colors.colorOnPrimary,
            style = typography.titleMedium,
        )
        LazyVerticalGrid(
            state = lazyGridState,
            columns = GridCells.Fixed(4),
            contentPadding = PaddingValues(SmallPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            items(iconList) {
                Box(Modifier.padding(ExtraSmallPadding), contentAlignment = Alignment.Center) {
                    IconButton(onClick = {
                        currentIcon = it
                    }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = it),
                            contentDescription = "Редактировать",
                            modifier = Modifier
                                .background(
                                    if (it == currentIcon) AppTheme.colors.colorOnPrimary else AppTheme.colors.colorPrimary,
                                    shape = RoundedCornerShape(ExtraSmallPadding)
                                )
                                .padding(ExtraSmallPadding)
                                .size(LargeIconSize)
                            ,
                            tint = if (it == currentIcon) AppTheme.colors.colorPrimary else AppTheme.colors.colorOnPrimary
                        )
                    }
                }
            }
        }


        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(
                onClick = { onCancel() },
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
                    callback(currentIcon)
                    onCancel()
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