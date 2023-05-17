package com.glebalekseevjk.habit.ui.widgets.modal_bottom_sheet

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.commandiron.wheel_picker_compose.core.WheelPickerDefaults
import com.commandiron.wheel_picker_compose.core.WheelTextPicker
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.domain.entity.Habit.Companion.TargetType
import com.glebalekseevjk.habit.ui.theme.*
import com.glebalekseevjk.habit.ui.utils.pxToDp
import com.glebalekseevjk.habit.ui.widgets.AutoResizeText

@Composable
fun ChooseTargetTypeModalBottomSheet(
    initValue: TargetType,
    callback: (TargetType) -> Unit,
    onCancel: () -> Unit
) {
    var currentTargetTypeIndex by rememberSaveable {
        mutableStateOf(
            when (initValue) {
                TargetType.OFF -> 2
                TargetType.REPEAT -> 1
                TargetType.DURATION -> 0
            }
        )
    }

    val listTargetType = listOf(
        stringResource(id = R.string.target_type_duration),
        stringResource(id = R.string.target_type_repeat),
        stringResource(id = R.string.target_type_off)
    )

    Column(
        modifier = Modifier
            .padding(MiddlePadding),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(id = R.string.choose_target_type),
            color = AppTheme.colors.colorOnPrimary,
            style = typography.titleMedium,
        )

        WheelTextPicker(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(MiddlePadding),
            size = DpSize(256.dp, 128.dp),
            rowCount = 3,
            startIndex = currentTargetTypeIndex,
            style = typography.labelMedium,
            color = AppTheme.colors.colorOnPrimary,
            selectorProperties = WheelPickerDefaults.selectorProperties(
                color = AppTheme.colors.colorDivider,
                border = null
            ),
            texts = listTargetType
        ) {
            if (it in listTargetType.indices) {
                currentTargetTypeIndex = it
            }
            null
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
                    callback(
                        when (listTargetType[currentTargetTypeIndex]) {
                            listTargetType[0] -> TargetType.DURATION
                            listTargetType[1] -> TargetType.REPEAT
                            listTargetType[2] -> TargetType.OFF
                            else -> throw RuntimeException()
                        }
                    )
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