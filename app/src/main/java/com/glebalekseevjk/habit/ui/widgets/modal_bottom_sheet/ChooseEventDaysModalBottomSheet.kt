package com.glebalekseevjk.habit.ui.widgets.modal_bottom_sheet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.domain.entity.Habit.Companion.Day
import com.glebalekseevjk.habit.ui.theme.*
import com.glebalekseevjk.habit.ui.utils.pxToDp
import com.glebalekseevjk.habit.ui.widgets.AutoResizeText

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

        var width by remember {
            mutableStateOf(0)
        }

        val days = listOf(
            Triple(R.string.weekday_mon, { monday = !monday }, monday),
            Triple(R.string.weekday_tue, { tuesday = !tuesday }, tuesday),
            Triple(R.string.weekday_wed, { wednesday = !wednesday }, wednesday),
            Triple(R.string.weekday_thu, { thursday = !thursday }, thursday),
            Triple(R.string.weekday_fri, { friday = !friday }, friday),
            Triple(R.string.weekday_sat, { saturday = !saturday }, saturday),
            Triple(R.string.weekday_sun, { sunday = !sunday }, sunday),
        )

        Row(
            modifier = Modifier
                .onSizeChanged {
                    width = it.width
                }
                .fillMaxWidth()
                .padding(vertical = SmallPadding),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            days.forEach {
                Card(
                    onClick = it.second,
                    shape = RoundedCornerShape(ExtraSmallPadding),
                    colors = CardDefaults.cardColors(
                        containerColor = if (it.third) AppTheme.colors.colorOnPrimary else AppTheme.colors.colorPrimary,
                        contentColor = if (it.third) AppTheme.colors.colorPrimary else AppTheme.colors.colorOnPrimary,
                    ),
                    border = BorderStroke(2.dp, AppTheme.colors.colorOnPrimary),
                    modifier = Modifier.size((width / 7.5).toInt().pxToDp())
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
                        AutoResizeText(
                            stringResource(id = it.first),
                            style = typography.titleMedium,
                            modifier = Modifier
                                .padding(SmallPadding),
                            color = if (it.third) AppTheme.colors.colorPrimary else AppTheme.colors.colorOnPrimary
                        )
                    }
                }
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { onCancel() },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.width((width / 2.5).toInt().pxToDp()),
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
                modifier = Modifier.width((width / 2.5).toInt().pxToDp()),
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