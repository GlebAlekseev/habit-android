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
import com.commandiron.wheel_picker_compose.WheelDatePicker
import com.commandiron.wheel_picker_compose.core.WheelPickerDefaults
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.ui.theme.*
import com.glebalekseevjk.habit.ui.utils.pxToDp
import com.glebalekseevjk.habit.ui.widgets.AutoResizeText
import java.time.LocalDate

@Composable
fun ChooseDateModalBottomSheet(
    initValue: LocalDate?,
    callback: (LocalDate) -> Unit,
    onCancel: () -> Unit
) {
    var currentDate by rememberSaveable {
        mutableStateOf(initValue ?: LocalDate.now().plusWeeks(1))
    }

    Column(
        modifier = Modifier
            .padding(MiddlePadding),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(id = R.string.choose_deadline),
            color = AppTheme.colors.colorOnPrimary,
            style = typography.titleMedium,
        )

        WheelDatePicker(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(MiddlePadding),
            startDate = initValue ?: LocalDate.now().plusWeeks(1),
            minDate = LocalDate.now(),
            maxDate = LocalDate.MAX,
            size = DpSize(256.dp, 128.dp),
            rowCount = 3,
            textStyle = typography.labelMedium,
            textColor = AppTheme.colors.colorOnPrimary,
            selectorProperties = WheelPickerDefaults.selectorProperties(
                color = AppTheme.colors.colorDivider,
                border = null
            ),
        ) { snappedTime ->
            currentDate = snappedTime
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
                    callback(currentDate)
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