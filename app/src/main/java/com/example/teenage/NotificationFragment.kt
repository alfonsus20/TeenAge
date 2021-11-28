package com.example.teenage

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NotificationFragment : Fragment() {
    lateinit var rvAlarms: RecyclerView
    var listAlarm = arrayListOf<AlarmModel>()

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notification, container, false)
        rvAlarms = view.findViewById(R.id.rv_alarms)
        rvAlarms.layoutManager = LinearLayoutManager(activity)

        listAlarm.add(
            AlarmModel("09:00", false),
        )

        listAlarm.add(
            AlarmModel("08:00", true),
        )

        listAlarm.add(
            AlarmModel("10:20", false),
        )

        listAlarm.add(
            AlarmModel("11:00", true),
        )


        rvAlarms.adapter = AlarmAdapter(listAlarm, activity as MainActivity)

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
}