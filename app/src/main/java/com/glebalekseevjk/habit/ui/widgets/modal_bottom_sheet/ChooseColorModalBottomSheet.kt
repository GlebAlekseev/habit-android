package com.glebalekseevjk.habit.ui.widgets.modal_bottom_sheet

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.ui.theme.*

val colorList = listOf(
    Color(0xFFec5c8c),
    Color(0xFFcf3b5f),
    Color(0xFFae6d84),
    Color(0xFF9f7963),
    Color(0xFFc69c84),
    Color(0xFFbeb775),
    Color(0xFFf0d044),
    Color(0xFF8eac50),
    Color(0xFF4a662f),
    Color(0xFF0a7272),
    Color(0xFF659c9d),
    Color(0xFF07d4dc),
    Color(0xFFbcd2dd),
    Color(0xFF9c7ab8),
    Color(0xFFef6224),
    Color(0xFFe82882),
)


@Composable
fun ChooseColorModalBottomSheet(initValue: Int, callback: (Int) -> Unit, onCancel: () -> Unit) {
    var currentColor by rememberSaveable {
        mutableStateOf(initValue)
    }

    val lazyGridState = rememberLazyGridState()

    Column(
        modifier = Modifier
            .padding(MiddlePadding),
    ) {
        Text(
            text = stringResource(id = R.string.choose_color),
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
            items(colorList) {

                var size = LargePadding + MiddlePadding
                val isCurrent = it.toArgb() == currentColor
                if (!isCurrent) size += (ExtraSmallPadding * 2)
                val padding = if (isCurrent) ExtraSmallPadding else 0.dp


                val modifier = if (isCurrent) Modifier.border(
                    SmallPadding, it.copy(alpha = 0.4f), RoundedCornerShape(
                        ExtraSmallPadding
                    )
                ) else Modifier
                Box(Modifier.padding(SmallPadding), contentAlignment = Alignment.Center) {
                    Button(
                        onClick = {
                            currentColor = it.toArgb()
                        },
                        modifier = modifier
                            .padding(padding)
                            .size(size),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = it
                        ),
                        shape = RoundedCornerShape(ExtraSmallPadding),
                    ) { }
                }
            }
        }


        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(
                onClick = {
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
                    text = stringResource(id = R.string.cancel).toUpperCase(),
                    style = typography.titleMedium,
                    color = AppTheme.colors.colorPrimary
                )
            }
            Button(
                onClick = {
                    callback(currentColor)
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
                    text = stringResource(id = R.string.save).toUpperCase(),
                    style = typography.titleMedium,
                    color = AppTheme.colors.colorPrimary
                )
            }
        }
    }
}