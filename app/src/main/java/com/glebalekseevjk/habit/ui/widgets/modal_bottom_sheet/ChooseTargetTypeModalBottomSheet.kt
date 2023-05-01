package com.glebalekseevjk.habit.ui.widgets.modal_bottom_sheet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.commandiron.wheel_picker_compose.WheelTimePicker
import com.commandiron.wheel_picker_compose.core.WheelPickerDefaults
import com.commandiron.wheel_picker_compose.core.WheelTextPicker
import com.glebalekseevjk.habit.domain.entity.Habit.Companion.TargetType
import com.glebalekseevjk.habit.ui.theme.*
import java.time.LocalTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseTargetTypeModalBottomSheet(
    initValue: TargetType,
    callback: (TargetType) -> Unit,
    onCancel: () -> Unit
) {
    var currentTargetTypeIndex by rememberSaveable {
        mutableStateOf(when(initValue){
            TargetType.OFF -> 2
            TargetType.REPEAT -> 1
            TargetType.DURATION -> 0
        })
    }

    val listTargetType = listOf("Длительность", "Повторение", "Выкл")

    Column(
        modifier = Modifier
            .padding(MiddlePadding),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "Выбор типа цели",
            color = AppTheme.colors.colorOnPrimary,
            style = typography.titleMedium,
        )

        WheelTextPicker(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(MiddlePadding),
            size = DpSize(256.dp,128.dp),
            rowCount = 3,
            startIndex = currentTargetTypeIndex,
            style = typography.labelMedium,
            color = AppTheme.colors.colorOnPrimary,
            selectorProperties = WheelPickerDefaults.selectorProperties(
                color = AppTheme.colors.colorOnPrimary.copy(alpha = 0.2f),
                border = null
            ),
            texts = listTargetType
        ) {
            if (it in listTargetType.indices){
                currentTargetTypeIndex = it
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
                    text = "Отмена".toUpperCase(),
                    style = typography.titleMedium,
                    color = AppTheme.colors.colorPrimary
                )
            }
            Button(
                onClick = {
                    callback(
                        when(listTargetType[currentTargetTypeIndex]){
                            listTargetType[0] ->TargetType.DURATION
                            listTargetType[1] ->TargetType.REPEAT
                            listTargetType[2] ->TargetType.OFF
                            else -> throw RuntimeException()
                        }
                    )
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
















//    val radioOptions = listOf("Длительность", "Повторение", "Выкл")
//    var selectedItem by rememberSaveable {
//        mutableStateOf(
//            when (initValue) {
//                TargetType.OFF -> radioOptions[2]
//                TargetType.REPEAT -> radioOptions[1]
//                TargetType.DURATION -> radioOptions[0]
//            }
//        )
//    }
//
//
//    Column(
//        modifier = Modifier
//            .padding(MiddlePadding),
//        verticalArrangement = Arrangement.SpaceBetween,
//    ) {
//        Text(
//            text = "Выбор типа цели",
//            color = AppTheme.colors.colorOnPrimary,
//            style = typography.titleMedium,
//        )
//
//
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = LargePadding),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            radioOptions.forEach { label ->
//                Card(
//                    onClick = {
//                        selectedItem = label
//                    },
//                    border = BorderStroke(2.dp, AppTheme.colors.colorOnPrimary),
//                    shape = RoundedCornerShape(ExtraSmallPadding),
//                    colors = CardDefaults.cardColors(
//                        containerColor = if (selectedItem == label) AppTheme.colors.colorOnPrimary else AppTheme.colors.colorPrimary,
//                        contentColor = if (selectedItem == label) AppTheme.colors.colorPrimary else AppTheme.colors.colorOnPrimary,
//                    ),
//                ) {
//                    Text(
//                        text = label,
//                        modifier = Modifier.padding(SmallPadding),
//                        style = typography.labelMedium
//                    )
//                }
//            }
//        }
//
//
//        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//            Button(
//                onClick = { onCancel() },
//                shape = RoundedCornerShape(16.dp),
//                modifier = Modifier,
//                contentPadding = PaddingValues(
//                    vertical = MiddlePadding,
//                    horizontal = LargePadding
//                ),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = AppTheme.colors.colorOnPrimary,
//                    contentColor = AppTheme.colors.colorPrimary
//                )
//            ) {
//                Text(
//                    text = "Отмена".toUpperCase(),
//                    style = typography.titleMedium,
//                    color = AppTheme.colors.colorPrimary
//                )
//            }
//            Button(
//                onClick = {
//                    callback(
//                        when (selectedItem) {
//                            radioOptions[0] -> TargetType.DURATION
//                            radioOptions[1] -> TargetType.REPEAT
//                            radioOptions[2] -> TargetType.OFF
//                            else -> throw RuntimeException()
//                        }
//                    )
//                    onCancel()
//                },
//                shape = RoundedCornerShape(16.dp),
//                modifier = Modifier,
//                contentPadding = PaddingValues(
//                    vertical = MiddlePadding,
//                    horizontal = LargePadding
//                ),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = AppTheme.colors.colorOnPrimary,
//                    contentColor = AppTheme.colors.colorPrimary
//                )
//            ) {
//                Text(
//                    text = "Сохранить".toUpperCase(),
//                    style = typography.titleMedium,
//                    color = AppTheme.colors.colorPrimary
//                )
//            }
//        }
//    }

}