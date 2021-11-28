package com.example.teenage

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object AlarmData {
    private val alarmTimes = arrayOf("07:00", "10:00", "13:00", "16:00", "19:00", "22:00")

    private val alarmStatuses = booleanArrayOf(true, true, true, true, true, true)

    val alarmData: ArrayList<AlarmModel>
        get() {
            val list = arrayListOf<AlarmModel>()

            for (i in alarmTimes.indices) {
                val df = SimpleDateFormat("HH:mm").parse(alarmTimes[i])
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR_OF_DAY, df.hours)
                calendar.set(Calendar.MINUTE, df.minutes)
                list.add(AlarmModel(alarmTimes[i], alarmStatuses[i], calendar.timeInMillis))
            }
            return list
        }
}