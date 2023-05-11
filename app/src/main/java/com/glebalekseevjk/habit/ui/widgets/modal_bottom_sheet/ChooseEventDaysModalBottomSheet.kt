package com.glebalekseevjk.habit.ui.widgets.modal_bottom_sheet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.domain.entity.Habit.Companion.Day
import com.glebalekseevjk.habit.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseEventDaysModalBottomSheet(
    initValue: Set<Day>,
    callback: (Set<Day>) -> Unit,
    onCancel: () -> Unit
) {
    var monday by rememberSaveable {
        mutableStateOf(initValue.contains(Day.MONDAY))
    }
    var tuesday by rememberSaveable {
        mutableStateOf(initValue.contains(Day.TUESDAY))
    }
    var wednesday by rememberSaveable {
        mutableStateOf(initValue.contains(Day.WEDNESDAY))
    }
    var thursday by rememberSaveable {
        mutableStateOf(initValue.contains(Day.THURSDAY))
    }
    var friday by rememberSaveable {
        mutableStateOf(initValue.contains(Day.FRIDAY))
    }
    var saturday by rememberSaveable {
        mutableStateOf(initValue.contains(Day.SATURDAY))
    }
    var sunday by rememberSaveable {
        mutableStateOf(initValue.contains(Day.SUNDAY))
    }




    Column(
        modifier = Modifier
            .padding(MiddlePadding),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {

        Text(
            text = stringResource(id = R.string.choose_event_days),
            color = AppTheme.colors.colorOnPrimary,
            style = typography.titleMedium,
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = SmallPadding),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Card(
                onClick = {
                    monday = !monday
                },
                shape = RoundedCornerShape(ExtraSmallPadding),
                colors = CardDefaults.cardColors(
                    containerColor = if (monday) AppTheme.colors.colorOnPrimary else AppTheme.colors.colorPrimary,
                    contentColor = if (monday) AppTheme.colors.colorPrimary else AppTheme.colors.colorOnPrimary,
                ),
                border = BorderStroke(2.dp, AppTheme.colors.colorOnPrimary),
            ) {
                Text(
                    stringResource(id = R.string.weekday_mon),
                    style = typography.titleMedium,
                    modifier = Modifier.padding(SmallPadding)
                )
            }
            Card(
                onClick = {
                    tuesday = !tuesday
                },
                shape = RoundedCornerShape(ExtraSmallPadding),
                colors = CardDefaults.cardColors(
                    containerColor = if (tuesday) AppTheme.colors.colorOnPrimary else AppTheme.colors.colorPrimary,
                    contentColor = if (tuesday) AppTheme.colors.colorPrimary else AppTheme.colors.colorOnPrimary,
                ),
                border = BorderStroke(2.dp, AppTheme.colors.colorOnPrimary),
            ) {
                Text(
                    stringResource(id = R.string.weekday_tue),
                    style = typography.titleMedium,
                    modifier = Modifier.padding(SmallPadding)
                )
            }
            Card(
                onClick = {
                    wednesday = !wednesday
                },
                shape = RoundedCornerShape(ExtraSmallPadding),
                colors = CardDefaults.cardColors(
                    containerColor = if (wednesday) AppTheme.colors.colorOnPrimary else AppTheme.colors.colorPrimary,
                    contentColor = if (wednesday) AppTheme.colors.colorPrimary else AppTheme.colors.colorOnPrimary,
                ),
                border = BorderStroke(2.dp, AppTheme.colors.colorOnPrimary),
            ) {
                Text(
                    stringResource(id = R.string.weekday_wed),
                    style = typography.titleMedium,
                    modifier = Modifier.padding(SmallPadding)
                )
            }
            Card(
                onClick = {
                    thursday = !thursday
                },
                shape = RoundedCornerShape(ExtraSmallPadding),
                colors = CardDefaults.cardColors(
                    containerColor = if (thursday) AppTheme.colors.colorOnPrimary else AppTheme.colors.colorPrimary,
                    contentColor = if (thursday) AppTheme.colors.colorPrimary else AppTheme.colors.colorOnPrimary,
                ),
                border = BorderStroke(2.dp, AppTheme.colors.colorOnPrimary),
            ) {
                Text(
                    stringResource(id = R.string.weekday_thu),
                    style = typography.titleMedium,
                    modifier = Modifier.padding(SmallPadding)
                )
            }
            Card(
                onClick = {
                    friday = !friday
                },
                shape = RoundedCornerShape(ExtraSmallPadding),
                colors = CardDefaults.cardColors(
                    containerColor = if (friday) AppTheme.colors.colorOnPrimary else AppTheme.colors.colorPrimary,
                    contentColor = if (friday) AppTheme.colors.colorPrimary else AppTheme.colors.colorOnPrimary,
                ),
                border = BorderStroke(2.dp, AppTheme.colors.colorOnPrimary),
            ) {
                Text(
                    stringResource(id = R.string.weekday_fri),
                    style = typography.titleMedium,
                    modifier = Modifier.padding(SmallPadding)
                )
            }
            Card(
                onClick = {
                    saturday = !saturday
                },
                shape = RoundedCornerShape(ExtraSmallPadding),
                colors = CardDefaults.cardColors(
                    containerColor = if (saturday) AppTheme.colors.colorOnPrimary else AppTheme.colors.colorPrimary,
                    contentColor = if (saturday) AppTheme.colors.colorPrimary else AppTheme.colors.colorOnPrimary,
                ),
                border = BorderStroke(2.dp, AppTheme.colors.colorOnPrimary),
            ) {
                Text(
                    stringResource(id = R.string.weekday_sat),
                    style = typography.titleMedium,
                    modifier = Modifier.padding(SmallPadding)
                )
            }
            Card(
                onClick = {
                    sunday = !sunday
                },
                shape = RoundedCornerShape(ExtraSmallPadding),
                colors = CardDefaults.cardColors(
                    containerColor = if (sunday) AppTheme.colors.colorOnPrimary else AppTheme.colors.colorPrimary,
                    contentColor = if (sunday) AppTheme.colors.colorPrimary else AppTheme.colors.colorOnPrimary,
                ),
                border = BorderStroke(2.dp, AppTheme.colors.colorOnPrimary),
            ) {
                Text(
                    stringResource(id = R.string.weekday_sun),
                    style = typography.titleMedium,
                    modifier = Modifier.padding(SmallPadding)
                )
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
                    val set = mutableSetOf<Day>()
                    if (monday) set.add(Day.MONDAY)
                    if (tuesday) set.add(Day.TUESDAY)
                    if (wednesday) set.add(Day.WEDNESDAY)
                    if (thursday) set.add(Day.THURSDAY)
                    if (friday) set.add(Day.FRIDAY)
                    if (saturday) set.add(Day.SATURDAY)
                    if (sunday) set.add(Day.SUNDAY)
                    callback(set)
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