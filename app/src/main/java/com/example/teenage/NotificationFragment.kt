package com.example.teenage

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teenage.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NotificationFragment : Fragment() {
    lateinit var rvAlarms: RecyclerView
    lateinit var btnAddAlarm: ImageButton
    lateinit var alarmAdapter: AlarmAdapter
    lateinit var alarmManager: AlarmManager
    lateinit var binding: ActivityMainBinding

    var listAlarm = arrayListOf<AlarmModel>()
    val calendar = Calendar.getInstance()

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notification, container, false)
        val myDB = SQLiteHelper(activity as MainActivity)

        createNotificationChannel()

        rvAlarms = view.findViewById(R.id.rv_alarms)
        btnAddAlarm = view.findViewById(R.id.btn_add_alarm)

        rvAlarms.layoutManager = LinearLayoutManager(activity)

        listAlarm = getAlarmData()

        alarmAdapter = AlarmAdapter(listAlarm, activity as MainActivity)

        btnAddAlarm.setOnClickListener {
            val tpd = TimePickerDialog(
                activity, TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
                    val timeFormatted =
                        "${if (i < 10) "0${i}" else i.toString()}:${if (i2 < 10) "0${i2}" else i2.toString()}"

                    calendar.set(Calendar.HOUR_OF_DAY, i)
                    calendar.set(Calendar.MINUTE, i2)

                    val newAlarm = AlarmModel(timeFormatted, true, calendar.timeInMillis)
                    myDB.insertAlarm(newAlarm)
                    listAlarm.add(newAlarm)
                    alarmAdapter.notifyItemInserted(alarmAdapter.itemCount)
                    alarmAdapter.listAlarms = getAlarmData()
                    setAlarm(newAlarm)
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true
            )
            tpd.show()
        }

        rvAlarms.adapter = alarmAdapter

        (activity as MainActivity).bottomNav.menu.getItem(2).isChecked = true
        return view
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "teenage"
            val description = "Pengingat minum air"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("teenage", name, importance)
            channel.description = description

            val notificationManager =
                requireContext().getSystemService(NotificationManager::class.java)

            notificationManager.createNotificationChannel(channel)
        }
    }

    fun setAlarm(alarm: AlarmModel) {
        alarmManager = requireContext().getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, alarm.id.toInt(), intent, 0)

        val df = SimpleDateFormat("HH:mm").parse(alarm.time)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP, df.time, AlarmManager.INTERVAL_DAY, pendingIntent
        )
    }

    fun cancelAlarm(idAlarm: Long) {
        alarmManager = requireContext().getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, idAlarm.toInt(), intent,0)
        alarmManager.cancel(pendingIntent)
    }

    @SuppressLint("RestrictedApi")
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setShowHideAnimationEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setTitle(Html.fromHtml("<font color='#14279B'>Notification</font>"))
        (activity as AppCompatActivity).supportActionBar?.show()
    }

    override fun onStop() {
        (activity as AppCompatActivity).supportActionBar?.hide()
        super.onStop()
    }

    fun getAlarmData(): ArrayList<AlarmModel> {
        val myDB = SQLiteHelper(activity as MainActivity)
        val cursor = myDB.getAlarms()
        val list = arrayListOf<AlarmModel>()

        while (cursor.moveToNext()) {
            list.add(
                AlarmModel(cursor.getString(1), cursor.getInt(2) > 0, cursor.getLong(0)),
            )
        }
        return list
    }

}