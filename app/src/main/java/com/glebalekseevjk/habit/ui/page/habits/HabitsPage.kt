package com.glebalekseevjk.habit.ui.page.habits

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.ui.theme.AppTheme
import com.glebalekseevjk.habit.ui.theme.typography
import com.glebalekseevjk.habit.ui.utils.pxToDp

val testData = 1

data class TestHabit(
    val title: String,
    val iconId: Int,
    val type: Type,
    val colorIcon: Color,
    val stage: Int,
    val dayList: List<Day>
) {
    data class Day(
        val day: String,
        val dayInt: Int
    )

    enum class Type {
        EVERY_DAY,
    }
}

val testHabitList = listOf(
    TestHabit(
        "Пейте 8 стаканов воды",
        R.drawable.ic_glass,
        TestHabit.Type.EVERY_DAY,
        Color.Red,
        14,
        listOf(
            TestHabit.Day("Чт", 12),
            TestHabit.Day("Пт", 13),
            TestHabit.Day("Сб", 14),
            TestHabit.Day("Вс", 15),
            TestHabit.Day("Пн", 16),
            TestHabit.Day("Вт", 17),
            TestHabit.Day("Ср", 18),
        )
    ),
)


@Composable
fun HabitsPage() {
    LazyColumn(
        verticalArrangement = Arrangement.Top, modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(testHabitList) {
            var elementHeight by remember {
                mutableStateOf(0)
            }
            Column(
                Modifier
                    .padding(24.dp)
                    .background(
                        AppTheme.colors.colorOnPrimary.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(12.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    Modifier
                        .padding(12.dp)
                        .fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(Modifier.height(elementHeight.pxToDp())) {
                        Box(
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .background(Color.Green)
                                .width(8.dp)
                                .fillMaxHeight()
                        )
                        Column(
                            verticalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            Text(
                                text = it.title,
                                style = typography.titleItem,
                                color = AppTheme.colors.colorOnPrimary
                            )
                            when (it.type) {
                                TestHabit.Type.EVERY_DAY -> {
                                    Box(
                                        contentAlignment = Alignment.Center, modifier = Modifier
                                            .background(
                                                Color.Green.copy(alpha = 0.2f),
                                                shape = RoundedCornerShape(4.dp),
                                            )
                                            .padding(8.dp)
                                    ) {
                                        Text(
                                            text = "Каждый день",
                                            color = Color.Green,
                                            style = typography.tag
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Box(
                        Modifier
                            .background(
                                it.colorIcon.copy(alpha = 0.25f),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .onSizeChanged {
                                if (elementHeight != it.height) {
                                    elementHeight = it.height
                                }
                            }
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = it.iconId),
                            contentDescription = null,
                            tint = it.colorIcon,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    it.dayList.forEach {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(horizontal = 3.dp)
                        ) {
                            Text(
                                text = it.day,
                                color = AppTheme.colors.colorOnPrimary,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .background(
                                        AppTheme.colors.colorOnPrimary.copy(alpha = 0.2f),
                                        shape = RoundedCornerShape(100.dp)
                                    )
                                    .padding(12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "${it.dayInt}", color = AppTheme.colors.colorOnPrimary)
                            }
                        }
                    }
                }

                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    thickness = 2.dp,
                    color = AppTheme.colors.divider
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                ) {
                    Row() {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_progress_check),
                            contentDescription = null,
                            tint = Color.Green
                        )
                        Text(
                            text = "${it.stage}%",
                            color = Color.Green,
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    }
                    Row() {
//                        Icon(imageVector = ImageVector.vectorResource(id = it.iconId), contentDescription = null)
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_edit),
                            contentDescription = null,
                            modifier = Modifier.padding(horizontal = 12.dp),
                            tint = AppTheme.colors.colorOnPrimary
                        )
                    }
                }

            }
        }

        item() {
            Row(Modifier.padding(vertical = 25.dp)) {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier,
                    contentPadding = PaddingValues(vertical = 24.dp, horizontal = 48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppTheme.colors.colorOnPrimary,
                        contentColor = AppTheme.colors.colorPrimary
                    )
                ) {
                    Text(
                        text = "Создайте новую привычку".toUpperCase(),
                        style = typography.button,
                        color = AppTheme.colors.colorPrimary
                    )
                }
            }
        }
    }
}