package com.example.teenage

object AlarmData {
    private val alarmTimes = arrayOf("07:00", "10:00", "13:00", "16:00", "19:00", "22:00")

    private val alarmStatuses = booleanArrayOf(true, true, true, true, true, true)

    val alarmData: ArrayList<AlarmModel>
        get() {
            val list = arrayListOf<AlarmModel>()
            for (i in alarmTimes.indices) {
                list.add(AlarmModel(alarmTimes[i], alarmStatuses[i]))
            }
            return list
        }
}