package com.himanshoe.kalendar.ui.common
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
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import com.himanshoe.kalendar.common.KalendarKonfig
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.ui.theme.AppTheme
import com.glebalekseevjk.habit.ui.theme.typography
import java.text.DateFormatSymbols

private const val DAYS_IN_WEEK = 7
private const val ZERO = 0

@Composable
internal fun KalendarWeekDayNames(kalendarKonfig: KalendarKonfig) {
    val weekdays = DateFormatSymbols(kalendarKonfig.locale).weekdays.takeLast(DAYS_IN_WEEK)

    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val width = (maxWidth / DAYS_IN_WEEK)
        Row(modifier = Modifier.fillMaxWidth()) {
            weekdays.forEach { weekDay: String ->
                KalendarWeekDay(
                    modifier = Modifier
                        .requiredWidth(width)
                        .alpha(0.7F)
                        .wrapContentHeight(),
                    text = ruLocale(weekDay.subSequence(ZERO, kalendarKonfig.weekCharacters).toString())
                )
            }
        }
    }
}

@Composable
fun ruLocale(name: String): String{
    when(name){
        "Sun" -> return stringResource(id = R.string.weekday_sun)
        "Mon" -> return stringResource(id = R.string.weekday_mon)
        "Tue" -> return stringResource(id = R.string.weekday_tue)
        "Wed" -> return stringResource(id = R.string.weekday_wed)
        "Thu" -> return stringResource(id = R.string.weekday_thu)
        "Fri" -> return stringResource(id = R.string.weekday_fri)
        "Sat" -> return stringResource(id = R.string.weekday_sat)
        else -> return stringResource(id = R.string.unknown)
    }
}

@Composable
internal fun KalendarWeekDay(
    modifier: Modifier = Modifier,
    text: String,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, modifier = Modifier, style = typography.labelMedium, color = AppTheme.colors.colorOnPrimary)
    }
}
