package com.example.teenage

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class NotificationFragment : Fragment() {
    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notification, container, false)
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