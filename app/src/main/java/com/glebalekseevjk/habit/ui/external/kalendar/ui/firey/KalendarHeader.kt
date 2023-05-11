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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.himanshoe.kalendar.common.KalendarSelector
import com.himanshoe.kalendar.common.theme.Grid
import com.glebalekseevjk.habit.ui.theme.AppTheme
import com.glebalekseevjk.habit.ui.theme.SmallIconSize
import com.glebalekseevjk.habit.ui.theme.typography
import com.glebalekseevjk.habit.R

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

@Composable
fun ruLocaleMonths(name: String): String {
    val array = name.split(" ")
    when (array[0]) {
        "June" -> return stringResource(id = R.string.month_june) + " "+ array[1]
        "July" -> return stringResource(id = R.string.month_july) + " " + array[1]
        "August" -> return stringResource(id = R.string.month_august) + " " + array[1]
        "September" -> return stringResource(id = R.string.month_september) + " " + array[1]
        "October" -> return stringResource(id = R.string.month_october) + " " + array[1]
        "November" -> return stringResource(id = R.string.month_november) + " " + array[1]
        "December" -> return stringResource(id = R.string.month_october) + " " + array[1]
        "January" -> return stringResource(id = R.string.month_january) + " " + array[1]
        "February" -> return stringResource(id = R.string.month_february) + " " + array[1]
        "March" -> return stringResource(id = R.string.month_march) + " " + array[1]
        "April" -> return stringResource(id = R.string.month_april) + " " + array[1]
        "May" -> return stringResource(id = R.string.month_may) + " " + array[1]
        else -> return stringResource(id = R.string.unknown)
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
