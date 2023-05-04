package com.glebalekseevjk.habit.ui.page.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.himanshoe.kalendar.common.KalendarStyle
import com.himanshoe.kalendar.ui.Kalendar
import com.glebalekseevjk.habit.ui.external.kalendar.common.data.KalendarEventCounter
import com.glebalekseevjk.habit.ui.page.habits.HabitsViewModel
import com.glebalekseevjk.habit.ui.theme.*
import java.time.LocalDate


data class Stats(
    val title: String,
    val value: String,
    val subtitle: String,
    val subValue: String,
    val containerColor: Color,
)

@Composable
fun HistoryPage(
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val stats = listOf(
        Stats(
            "Текущая\nсерия",
            "1",
            "Лучшая серия успехов: ",
            "111",
            containerColor = Color(0xFF7e8a97),
        ),
        Stats(
            "Привычка\nзавершена",
            "2",
            "Эта неделя: ",
            "1",
            containerColor = Color(0xFFf77a4e),
        ),
        Stats(
            "Доля за\nвершенных",
            "331%",
            "Привычка: ",
            "2/6",
            containerColor = Color(0xFF4e5778),
            ),
        Stats(
            "Идеальные\nдни",
            "111",
            "Эта неделя: ",
            "1",
            containerColor = Color(0xFF54a79c),
            ),
    )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MiddlePadding)
        ) {
            item {
                LazyRow {
                    items(stats){
                        Card(
                            modifier = Modifier
                                .padding(ExtraSmallPadding)
                                .height(192.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = it.containerColor,
                                contentColor = AppTheme.colors.colorPrimary
                            ),
                            shape = RoundedCornerShape(ExtraSmallPadding)
                        ) {
                            Column(modifier = Modifier.padding(SmallPadding)) {
                                Text(text = it.title.toUpperCase(), style = typography.titleLarge.copy(fontSize = 21.sp))
                                Text(text = it.value, style = typography.titleLarge.copy(fontSize = 72.sp))
                                Text(text = it.subtitle + " " + it.subValue, style = typography.labelSmall.copy(fontSize = 11.sp))
                            }
                        }
                    }
                }
            }

            item {
                Kalendar(
                    onCurrentDayClick = { date, _ ->
                        println(date)
                    },
                    kalendarEventCounterList = listOf(
                        KalendarEventCounter(LocalDate.now(), 4),
                        KalendarEventCounter(LocalDate.now().plusDays(1), 4),
                        KalendarEventCounter(LocalDate.now().plusDays(2), 1),
                        KalendarEventCounter(LocalDate.now().plusDays(3), 51),
                        KalendarEventCounter(LocalDate.now().plusDays(4), 411),
                    ),
                    modifier = Modifier
                        .padding(top = SmallPadding)
                        .padding(horizontal = ExtraSmallPadding)
                )
            }

            item {
                Card() {
                    
                }

            }
        }










}