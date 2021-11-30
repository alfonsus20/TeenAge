package com.example.teenage

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class AlarmAdapter(var listAlarms: ArrayList<AlarmModel>, val context: Context) :
    RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {

    val myDB = SQLiteHelper(context)
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        var view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_alarm, parent, false)
        return AlarmViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        holder.tvTime.text = listAlarms[position].time
        holder.switch.isChecked = listAlarms[position].status

        holder.btnDeleteAlarm.setOnClickListener {
            val pendingIntent =
                PendingIntent.getBroadcast(context, listAlarms[position].id.toInt(), intent, 0)
            alarmManager.cancel(pendingIntent)

            myDB.deleteAlarm(listAlarms[position].id)
            listAlarms.removeAt(position)

            notifyItemRemoved(position)
            notifyDataSetChanged()
        }

        holder.btnEditAlarm.setOnClickListener {
            var df = SimpleDateFormat("HH:mm").parse(listAlarms[position].time)
            val oldId = listAlarms[position].id
            val tpd = TimePickerDialog(
                context,
                TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
                    val timeFormatted =
                        "${if (i < 10) "0${i}" else i.toString()}:${if (i2 < 10) "0${i2}" else i2.toString()}"

                    val calendar = Calendar.getInstance()

                    calendar.set(Calendar.HOUR_OF_DAY, i)
                    calendar.set(Calendar.MINUTE, i2)
                    calendar.set(Calendar.SECOND, 0)

                    alarmManager.cancel(
                        PendingIntent.getBroadcast(
                            context,
                            listAlarms[position].id.toInt(),
                            intent,
                            0
                        )
                    )

                    val newAlarm = AlarmModel(
                        timeFormatted,
                        listAlarms[position].status,
                        calendar.timeInMillis
                    )

                    myDB.updateAlarm(oldId, newAlarm)
                    listAlarms[position] = newAlarm

                    val pendingIntent =
                        PendingIntent.getBroadcast(context, newAlarm.id.toInt(), intent, 0)
                    val df = SimpleDateFormat("HH:mm:ss").parse("${listAlarms[position].time}:00")
                    alarmManager.setRepeating(
                        AlarmManager.RTC_WAKEUP, df.time, AlarmManager.INTERVAL_DAY, pendingIntent
                    )

                    listAlarms[position].status = true

                    notifyItemChanged(position)
                },
                df.hours,
                df.minutes,
                true
            )
            tpd.show()
        }

        holder.switch.setOnCheckedChangeListener { compoundButton, b ->
            val newAlarm = AlarmModel(listAlarms[position].time, b, listAlarms[position].id)
            myDB.updateAlarm(listAlarms[position].id, newAlarm)
            listAlarms[position] = newAlarm
            val pendingIntent = PendingIntent.getBroadcast(context, newAlarm.id.toInt(), intent, 0)
            val df = SimpleDateFormat("HH:mm:ss").parse("${listAlarms[position].time}:00")
            if (b) {
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP, df.time, AlarmManager.INTERVAL_DAY, pendingIntent
                )
            } else {
                alarmManager.cancel(pendingIntent)
            }
        }
    }

    override fun getItemCount(): Int {
        return listAlarms.size
    }

    inner class AlarmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTime: TextView = itemView.findViewById(R.id.tv_time)
        var btnEditAlarm: ImageView = itemView.findViewById(R.id.btn_edit)
        var btnDeleteAlarm: ImageView = itemView.findViewById(R.id.btn_delete)
        var switch: Switch = itemView.findViewById(R.id.switch_alarm)
    }
}