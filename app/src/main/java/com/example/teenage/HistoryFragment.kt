package com.example.teenage

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class HistoryFragment : Fragment() {
    private lateinit var calendarView: CalendarView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        val fragmentHistoryDetail = HistoryDetailFragment()
        calendarView = view.findViewById(R.id.calendarView)

        calendarView.setOnDateChangeListener { calendarView, i, i2, i3 ->
            var monthStr = (i2 + 1).toString()
            var dayStr = i3.toString()

            if (i2 < 10) {
                monthStr = "0" + monthStr
            }

            if (i3 < 10) {
                dayStr = "0" + dayStr
            }

            val date = "$i-$monthStr-$dayStr"

            val bundle = Bundle()
            bundle.putString("date", date)
            fragmentHistoryDetail.setArguments(bundle)

            requireActivity().supportFragmentManager?.beginTransaction()
                .replace(R.id.fragment_container, fragmentHistoryDetail).addToBackStack(null)
                .commit()
        }

        (activity as MainActivity).bottomNav.menu.getItem(1).isChecked = true
        return view
    }


    @SuppressLint("RestrictedApi")
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setShowHideAnimationEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setTitle(Html.fromHtml("<font color='#14279B'>History</font>"))
        (activity as AppCompatActivity).supportActionBar?.show()
    }

    override fun onStop() {
        (activity as AppCompatActivity).supportActionBar?.hide()
        super.onStop()
    }
}