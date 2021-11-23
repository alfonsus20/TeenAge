package com.example.teenage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class NotificationFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notification, container, false)
        val title: TextView = view.findViewById(R.id.TitleBar)
        title.setText("Notification")
        (activity as MainActivity).bottomNav.menu.getItem(2).isChecked = true
        return view
    }
}