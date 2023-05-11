package com.glebalekseevjk.habit.ui.widgets.modal_bottom_sheet

import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.commandiron.wheel_picker_compose.core.WheelPickerDefaults
import com.commandiron.wheel_picker_compose.core.WheelTextPicker
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.ui.theme.AppTheme
import com.glebalekseevjk.habit.ui.theme.LargePadding
import com.glebalekseevjk.habit.ui.theme.MiddlePadding
import com.glebalekseevjk.habit.ui.theme.typography

@Composable
fun ChooseRepeatCountModalBottomSheet(
    initValue: Int?,
    callback: (Int) -> Unit,
    onCancel: () -> Unit
) {
    var currentRepeatCountIndex by rememberSaveable {
        mutableStateOf((initValue ?: 1) - 1)
    }

    val listRepeatCounters = (1..1000).toList().map { it.toString() }


    Column(
        modifier = Modifier
            .padding(MiddlePadding),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(id = R.string.choose_repeat_count),
            color = AppTheme.colors.colorOnPrimary,
            style = typography.titleMedium,
        )

        WheelTextPicker(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(MiddlePadding),
            size = DpSize(256.dp, 128.dp),
            rowCount = 3,
            startIndex = currentRepeatCountIndex,
            style = typography.labelMedium,
            color = AppTheme.colors.colorOnPrimary,
            selectorProperties = WheelPickerDefaults.selectorProperties(
                color = AppTheme.colors.colorOnPrimary.copy(alpha = 0.2f),
                border = null
            ),
            texts = listRepeatCounters
        ) {
            if (it in listRepeatCounters.indices) {
                currentRepeatCountIndex = it
            }
            null
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
                    text = stringResource(id = R.string.cancel).toUpperCase(),
                    style = typography.titleMedium,
                    color = AppTheme.colors.colorPrimary
                )
            }
            Button(
                onClick = {
                    callback(currentRepeatCountIndex + 1)
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