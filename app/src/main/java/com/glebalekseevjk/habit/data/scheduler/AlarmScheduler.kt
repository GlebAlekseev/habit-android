package com.glebalekseevjk.habit.data.scheduler

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.glebalekseevjk.habit.broadcastreceiver.NotificationReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class AlarmScheduler @Inject constructor(@ApplicationContext val context: Context) {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    fun schedule(packageContext: Class<*>, id: Int, date: LocalDateTime) {
        if (date.isAfter(LocalDateTime.now())) {
            val broadcastIntent = Intent(context, packageContext).apply {
                putExtra(NotificationReceiver.EXTRA_ID, id)
            }
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                id,
                broadcastIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
            )

            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                pendingIntent
            )
        }
    }

    fun cancel(packageContext: Class<*>, id: Int) {
        val broadcastIntent = Intent(context, packageContext)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id,
            broadcastIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
        )
        alarmManager.cancel(pendingIntent)
    }
}