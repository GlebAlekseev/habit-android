package com.glebalekseevjk.habit.broadcastreceiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.domain.entity.Habit
import com.glebalekseevjk.habit.domain.interactor.EventNotificationUseCase
import com.glebalekseevjk.habit.domain.interactor.HabitUseCase
import com.glebalekseevjk.habit.utils.checkFailure
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import javax.inject.Inject


@AndroidEntryPoint
class NotificationReceiver : BroadcastReceiver() {
    @Inject
    lateinit var eventNotificationUseCase: EventNotificationUseCase

    @Inject
    lateinit var habitUseCase: HabitUseCase

    override fun onReceive(context: Context, intent: Intent) {
        val eventNotificationId = intent.getIntExtra(EXTRA_ID, -1)
        if (eventNotificationId == -1) throw RuntimeException("Bad eventNotificationId from extra")
        CoroutineScope(Dispatchers.IO).launch {
            val eventNotification =
                eventNotificationUseCase.getEventNotification(eventNotificationId).checkFailure()
            val habit = habitUseCase.getHabit(eventNotification.habitId).checkFailure()
            val mBuilder = NotificationCompat.Builder(context, "com.glebalekseevjk.habit.event")
                .setSmallIcon(habit.iconId)
                .setContentTitle(
                    habit.title + when (habit.targetType) {
                        Habit.Companion.TargetType.OFF -> ""
                        Habit.Companion.TargetType.REPEAT -> " — ${habit.repeatCount} " + context.resources.getString(
                            R.string.times
                        )
                        Habit.Companion.TargetType.DURATION -> " — ${
                            habit.duration!!.format(
                                DateTimeFormatter.ofPattern(
                                    context.resources.getString(R.string.time_pattern)
                                )
                            )
                        }"
                    }
                )
                .setContentText(context.resources.getString(R.string.dont_forget))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(
                "com.glebalekseevjk.habit.event",
                context.resources.getString(R.string.habit_notifications),
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.enableLights(true)
            notificationManager.createNotificationChannel(channel)
            val id = System.currentTimeMillis() / 1000
            notificationManager.notify(id.toInt(), mBuilder.build())
        }
    }

    companion object {
        const val EXTRA_ID = "id"
    }
}