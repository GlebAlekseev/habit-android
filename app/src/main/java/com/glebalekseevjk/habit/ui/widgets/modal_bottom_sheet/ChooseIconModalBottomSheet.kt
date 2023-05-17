package com.glebalekseevjk.habit.ui.widgets.modal_bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.ui.theme.*
import com.glebalekseevjk.habit.ui.utils.pxToDp
import com.glebalekseevjk.habit.ui.widgets.AutoResizeText

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
    R.drawable.ic_lang,
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
            text = stringResource(id = R.string.choose_icon),
            color = AppTheme.colors.colorOnPrimary,
            style = typography.titleMedium,
        )
        LazyVerticalGrid(
            state = lazyGridState,
            columns = GridCells.Fixed(4),
            contentPadding = PaddingValues(SmallPadding),
            modifier = Modifier
                .padding(vertical = SmallPadding)
                .heightIn(SmallPadding, (LocalConfiguration.current.screenHeightDp/2).dp)
            ,
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
                                .size(LargeIconSize),
                            tint = if (it == currentIcon) AppTheme.colors.colorPrimary else AppTheme.colors.colorOnPrimary
                        )
                    }
                }
            }
        }

        var width by remember {
            mutableStateOf(0)
        }

        Row(
            modifier = Modifier
                .onSizeChanged {
                    width = it.width
                }
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { onCancel() },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.width((width/2.5).toInt().pxToDp()),
                contentPadding = PaddingValues(
                    vertical = SmallPadding,
                    horizontal = MiddlePadding
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.colors.colorOnPrimary,
                    contentColor = AppTheme.colors.colorPrimary
                )
            ) {
                AutoResizeText(
                    text = stringResource(id = R.string.cancel).toUpperCase(),
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
                modifier = Modifier.width((width/2.5).toInt().pxToDp()),
                contentPadding = PaddingValues(
                    vertical = SmallPadding,
                    horizontal = MiddlePadding
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.colors.colorOnPrimary,
                    contentColor = AppTheme.colors.colorPrimary
                )
            ) {
                AutoResizeText(
                    text = stringResource(id = R.string.save).toUpperCase(),
                    style = typography.titleMedium,
                    color = AppTheme.colors.colorPrimary
                )
            }
        }
    }
}