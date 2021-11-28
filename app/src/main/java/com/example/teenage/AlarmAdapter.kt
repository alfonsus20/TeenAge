package com.example.teenage

import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
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

class AlarmAdapter(var listAlarms: ArrayList<AlarmModel>, val context: Context) :
    RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {

    val myDB = SQLiteHelper(context)

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
            myDB.deleteAlarm(listAlarms[position].id!!)
            listAlarms.removeAt(position)
            notifyItemRemoved(position)
            notifyDataSetChanged()
        }

        holder.btnEditAlarm.setOnClickListener {
            var df = SimpleDateFormat("HH:mm").parse(listAlarms[position].time)

            val tpd = TimePickerDialog(
                context,
                TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
                    val timeFormatted =
                        "${if (i < 10) "0${i}" else i.toString()}:${if (i2 < 10) "0${i2}" else i2.toString()}"
                    val newAlarm = AlarmModel(timeFormatted, listAlarms[position].status, listAlarms[position].id!!)
                    myDB.updateAlarm(newAlarm)
                    listAlarms[position] = newAlarm
                    notifyItemChanged(position)
                },
                df.hours,
                df.minutes,
                true
            )
            tpd.show()
        }
        
        holder.switch.setOnCheckedChangeListener { compoundButton, b ->
            val newAlarm = AlarmModel(listAlarms[position].time, b, listAlarms[position].id!!)
            myDB.updateAlarm(newAlarm)
            listAlarms[position] = newAlarm
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