package com.himanshoe.kalendar.ui.firey
/*
* MIT License
*
* Copyright (c) 2022 Himanshu Singh
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANT1IES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.himanshoe.kalendar.common.KalendarKonfig
import com.himanshoe.kalendar.common.KalendarStyle
import com.himanshoe.kalendar.common.data.KalendarEvent
import com.himanshoe.kalendar.common.theme.KalendarTheme
import com.glebalekseevjk.habit.ui.external.kalendar.common.data.KalendarEventCounter
import com.glebalekseevjk.habit.ui.theme.AppTheme
import com.glebalekseevjk.habit.ui.theme.ExtraSmallPadding
import java.time.LocalDate


@Composable
internal fun KalendarFirey(
    modifier: Modifier = Modifier,
    kalendarKonfig: KalendarKonfig = KalendarKonfig(),
    kalendarStyle: KalendarStyle = KalendarStyle(),
    selectedDay: LocalDate = LocalDate.now(),
    kalendarEvents: List<KalendarEvent>,
    onCurrentDayClick: (LocalDate, KalendarEvent?) -> Unit,
    errorMessageLogged: (String) -> Unit,
    kalendarEventCounterList: List<KalendarEventCounter> = emptyList(),
) {

    KalendarTheme {
        val color = kalendarStyle.kalendarBackgroundColor ?: KalendarTheme.colors.selectedColor
        val calendarBackgroundColor =
            kalendarStyle.kalendarColor ?: KalendarTheme.colors.background
        Card(
            modifier = modifier,
            elevation = 0.dp,
            shape = RoundedCornerShape(ExtraSmallPadding),
            border = BorderStroke(1.dp, AppTheme.colors.colorOnPrimary),
            backgroundColor = AppTheme.colors.colorOnPrimary.copy(alpha = 0.1f),
        ) {
            KalendarView(
                kalendarSelector = kalendarStyle.kalendarSelector,
                kalendarKonfig = kalendarKonfig,
                errorMessageLogged = errorMessageLogged,
                selectedDay = selectedDay,
                kalendarEvents = kalendarEvents,
                onCurrentDayClick = { date, event ->
                    onCurrentDayClick(date, event)
                },
                kalendarEventCounterList = kalendarEventCounterList
            )
        }
    }
}
