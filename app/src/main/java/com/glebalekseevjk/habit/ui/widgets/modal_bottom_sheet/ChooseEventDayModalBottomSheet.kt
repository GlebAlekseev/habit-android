package com.glebalekseevjk.habit.ui.widgets.modal_bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.commandiron.wheel_picker_compose.WheelDatePicker
import com.glebalekseevjk.habit.ui.theme.*


val days = listOf(
    "ПН","ВТ","СР","ЧТ","ПТ","СБ", "ВС"
)

@Composable
fun ChooseEventDayModalBottomSheet() {
    val radioOptions = listOf("Определенные дни недели", "X д. в неделю", "X д. в месяц", "X д. в год")

    var selectedItem by remember {
        mutableStateOf(radioOptions[0])
    }

    Column(
        modifier = Modifier
            .padding(MiddlePadding),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {

        Text(
            text = "Выберите время",
            color = AppTheme.colors.colorOnPrimary,
            style = typography.titleMedium,
        )



        Column(modifier = Modifier.fillMaxWidth()) {
            for (label in radioOptions){
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = SmallPadding)
                        .selectable(
                            selected = (selectedItem == label),
                            onClick = { selectedItem = label },
                            role = Role.RadioButton
                        )
                        .background(AppTheme.colors.colorOnPrimary)
                        .padding(SmallPadding),
                        horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text(text = label, color = AppTheme.colors.colorPrimary, style = typography.titleSmall)
                        RadioButton(selected = selectedItem == label, onClick = { selectedItem = label },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = AppTheme.colors.colorPrimary,
                            unselectedColor = AppTheme.colors.colorPrimary
                        ))
                    }
                    if (label == radioOptions[0]){
                        Text("Каждый день", color = AppTheme.colors.colorPrimary, style = typography.titleSmall)
                        Row(modifier = Modifier.fillMaxWidth()) {
                            for (day in days){
                                Box(modifier = Modifier
                                    .padding(ExtraSmallPadding)
                                    .background(AppTheme.colors.colorOnPrimary, shape = RoundedCornerShape(4.dp))
                                    .padding(ExtraSmallPadding), contentAlignment = Alignment.Center) {
                                    Text(text = day, color = AppTheme.colors.colorPrimary, style = typography.titleMedium)
                                }
                            }
                        }
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