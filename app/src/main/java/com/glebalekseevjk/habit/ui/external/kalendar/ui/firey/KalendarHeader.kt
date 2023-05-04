package com.himanshoe.kalendar.ui.firey

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.himanshoe.kalendar.common.KalendarSelector
import com.himanshoe.kalendar.common.theme.Grid
import com.glebalekseevjk.habit.ui.theme.AppTheme
import com.glebalekseevjk.habit.ui.theme.SmallIconSize
import com.glebalekseevjk.habit.ui.theme.typography

@Composable
internal fun KalendarHeader(
    text: String,
    onPreviousMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit,
    kalendarSelector: KalendarSelector,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.colorOnPrimary),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,

        ) {
        KalendarButton(
            kalendarSelector = kalendarSelector,
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Previous Month",
            onClick = onPreviousMonthClick
        )
        Text(
            modifier = Modifier.width(192.dp),
            style = typography.titleLarge,
            text = ruLocaleMonths(text),
            textAlign = TextAlign.Center,
            color = AppTheme.colors.colorPrimary
        )
        KalendarButton(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "Next Month",
            onClick = onNextMonthClick,
            kalendarSelector = kalendarSelector
        )
    }
}

fun ruLocaleMonths(name: String): String {
    val array = name.split(" ")
    when (array[0]) {
        "June" -> return "Июнь " + array[1]
        "July" -> return "Июль " + array[1]
        "August" -> return "Август " + array[1]
        "September" -> return "Сентябрь " + array[1]
        "October" -> return "Октябрь " + array[1]
        "November" -> return "Ноябрь " + array[1]
        "December" -> return "Декабрь  " + array[1]
        "January" -> return "Январь " + array[1]
        "February" -> return "Февраль " + array[1]
        "March" -> return "Март " + array[1]
        "April" -> return "Апрель " + array[1]
        "May" -> return "Май " + array[1]
        else -> return "Unknown"
    }

}

@Composable
private fun KalendarButton(
    imageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    kalendarSelector: KalendarSelector,
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
    ) {
        Icon(
            modifier = Modifier.size(SmallIconSize),
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = AppTheme.colors.colorPrimary
        )
    }
}
