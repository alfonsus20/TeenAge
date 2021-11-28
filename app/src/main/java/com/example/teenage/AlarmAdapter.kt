package com.example.teenage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class AlarmAdapter(val listAlarms: ArrayList<AlarmModel>, val context: Context) :
    RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        var view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_alarm, parent, false)
        return AlarmViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        holder.tvTime.text = listAlarms[position].time
        holder.switch.isChecked = listAlarms[position].status

        holder.btnDeleteAlarm.setOnClickListener {
            Toast.makeText(context, "delete button", Toast.LENGTH_SHORT).show()
        }

        holder.btnEditAlarm.setOnClickListener {
            Toast.makeText(context, "edit button", Toast.LENGTH_SHORT).show()
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