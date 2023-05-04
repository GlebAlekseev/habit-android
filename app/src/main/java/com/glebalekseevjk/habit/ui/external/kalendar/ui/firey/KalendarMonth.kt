package com.himanshoe.kalendar.ui.firey

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.Text
import androidx.compose.material3.BadgeDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.himanshoe.kalendar.common.KalendarKonfig
import com.himanshoe.kalendar.common.KalendarSelector
import com.himanshoe.kalendar.common.data.KalendarEvent
import com.himanshoe.kalendar.ui.common.KalendarWeekDayNames
import com.himanshoe.kalendar.util.getMonthNameFormatter
import com.himanshoe.kalendar.util.validateMaxDate
import com.himanshoe.kalendar.util.validateMinDate
import com.glebalekseevjk.habit.ui.external.kalendar.common.data.KalendarEventCounter
import com.glebalekseevjk.habit.ui.theme.AppTheme
import com.glebalekseevjk.habit.ui.theme.ExtraSmallPadding
import com.glebalekseevjk.habit.ui.theme.typography
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.util.*

private const val DAYS_IN_WEEK = 7

@RequiresApi(Build.VERSION_CODES.O)
@Composable
internal fun KalendarMonth(
    selectedDay: LocalDate,
    yearMonth: YearMonth = YearMonth.now(),
    kalendarKonfig: KalendarKonfig,
    onCurrentDayClick: (LocalDate, KalendarEvent?) -> Unit,
    errorMessageLogged: (String) -> Unit,
    kalendarSelector: KalendarSelector,
    kalendarEvents: List<KalendarEvent>,
    kalendarEventCounterList: List<KalendarEventCounter> = emptyList()
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        val haptic = LocalHapticFeedback.current

        val monthState = remember {
            mutableStateOf(yearMonth)
        }
        val clickedDay = remember {
            mutableStateOf(selectedDay)
        }

        KalendarHeader(
            kalendarSelector = kalendarSelector,
            text = monthState.value.format(getMonthNameFormatter(kalendarKonfig.locale)),
            onPreviousMonthClick = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                val year = monthState.value.year
                val isLimitAttached = year.validateMinDate(kalendarKonfig.yearRange.min)
                if (isLimitAttached) {
                    monthState.value = monthState.value.minusMonths(1)
                } else {
                    errorMessageLogged("Minimum year limit reached")
                }
            },
            onNextMonthClick = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                val year = monthState.value.year
                val isLimitAttached = year.validateMaxDate(kalendarKonfig.yearRange.max)
                if (isLimitAttached) {
                    monthState.value = monthState.value.plusMonths(1)
                } else {
                    errorMessageLogged("Minimum year limit reached")
                }
            },
        )
        KalendarWeekDayNames(kalendarKonfig = kalendarKonfig)

        val days: List<LocalDate> = getDays(monthState)

        days.chunked(DAYS_IN_WEEK).forEachIndexed { index, weekDays ->
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val size = (maxWidth / DAYS_IN_WEEK)
                Row(
                    horizontalArrangement = Arrangement.End, modifier = Modifier
                        .align(if (index == 0) Alignment.CenterEnd else Alignment.CenterStart)
                ) {
                    weekDays.forEach { localDate ->
                        val isFromCurrentMonth = YearMonth.from(localDate) == monthState.value
                        if (isFromCurrentMonth) {
                            val isSelected = monthState.value.month == clickedDay.value.month &&
                                    monthState.value.year == clickedDay.value.year &&
                                    localDate == clickedDay.value

                            val formatter = SimpleDateFormat("dd:MM:yyyy hh:mm:ss", Locale.US)

                            val count = kalendarEventCounterList.firstOrNull { it.date == localDate }

                            BadgedBox(
                                badge = {
                                    count?.let {
                                        Card(
                                            shape = RoundedCornerShape(ExtraSmallPadding),
                                            colors = CardDefaults.cardColors(
                                                containerColor = if (isSelected) AppTheme.colors.colorPrimary else AppTheme.colors.colorOnPrimary,
                                            ),
                                            modifier = Modifier.offset(-16.dp,12.dp)
                                        ) {
                                            Text(
                                                text = it.counter.toString(),
                                                modifier = Modifier.padding(ExtraSmallPadding/2),
                                                softWrap = false,
                                                style = typography.labelSmall,
                                                color = if (isSelected) AppTheme.colors.colorOnPrimary else AppTheme.colors.colorPrimary,
                                            )
                                        }
                                    }
                                },
                            ) {
                                KalendarDay(
                                    size = size,
                                    date = localDate,
                                    isSelected = isSelected,
                                    isToday = localDate == LocalDate.now(),
                                    kalendarSelector = kalendarSelector,
                                    kalendarEvents = kalendarEvents,
                                    onDayClick = { date, event ->
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        clickedDay.value = date
                                        onCurrentDayClick(date, event)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun getDays(monthState: MutableState<YearMonth>): List<LocalDate> {
    return mutableListOf<LocalDate>().apply {
        val firstDay = monthState.value.atDay(1)
        val firstSunday = if (firstDay.dayOfWeek == java.time.DayOfWeek.SUNDAY) {
            firstDay
        } else {
            firstDay.minusDays(firstDay.dayOfWeek.value.toLong())
        }
        repeat(6) { weekIndex ->
            (0..6).forEach { dayIndex ->
                add(firstSunday.plusDays((7 * weekIndex + dayIndex).toLong()))
            }
        }
    }
}
