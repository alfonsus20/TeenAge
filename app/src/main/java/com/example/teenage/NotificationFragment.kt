package com.example.teenage

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.provider.CalendarContract
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class NotificationFragment : Fragment() {
    lateinit var rvAlarms: RecyclerView
    lateinit var btnAddAlarm: ImageButton
    lateinit var alarmAdapter: AlarmAdapter

    var listAlarm = arrayListOf<AlarmModel>()
    val calendar = Calendar.getInstance()

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notification, container, false)
        val myDB = SQLiteHelper(activity as MainActivity)

        rvAlarms = view.findViewById(R.id.rv_alarms)
        btnAddAlarm = view.findViewById(R.id.btn_add_alarm)

        rvAlarms.layoutManager = LinearLayoutManager(activity)

        listAlarm = getAlarmData()

        alarmAdapter = AlarmAdapter(listAlarm, activity as MainActivity)

        btnAddAlarm.setOnClickListener {
            val tpd = TimePickerDialog(
                activity, TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
                    val timeFormatted =
                        "${if (i < 10) "0${i.toString()}" else i.toString()}:${if (i2 < 10) "0${i2.toString()}" else i2.toString()}"
                    val newAlarm = AlarmModel(timeFormatted, true)
                    myDB.insertAlarm(newAlarm)
                    listAlarm.add(newAlarm)
                    alarmAdapter.notifyItemInserted(alarmAdapter.itemCount)
                    alarmAdapter.listAlarms = getAlarmData()
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true
            )
            tpd.show()
        }

        rvAlarms.adapter = alarmAdapter

        (activity as MainActivity).bottomNav.menu.getItem(2).isChecked = true
        return view
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
                AlarmModel(cursor.getString(1), cursor.getInt(2) > 0, cursor.getInt(0)),
            )
        }
        return list
    }

}