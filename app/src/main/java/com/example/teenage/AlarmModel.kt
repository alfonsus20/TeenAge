package com.example.teenage

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.text.SimpleDateFormat
import java.util.*


data class AlarmModel(
    var time: String = "",
    var status: Boolean = false,
    var id: Int? = null,
) {
    val calendar = Calendar.getInstance()

    fun schedule(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var df = SimpleDateFormat("HH:mm").parse(time)

        var intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra("status", status)

        val pendingIntent = PendingIntent.getBroadcast(context, id!!, intent, 0)

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, df.time, pendingIntent)
    }
}