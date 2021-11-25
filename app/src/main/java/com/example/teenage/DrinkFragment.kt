package com.example.teenage

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.roundToInt

class DrinkFragment : Fragment() {
    lateinit var currentUser: UserModel
    lateinit var tvTarget: TextView
    lateinit var tvDone: TextView
    lateinit var progressBarDrink: ProgressBar
    lateinit var tvProgressBarDrink: TextView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_drink, container, false)
        val btnOpenMenu: Button = view.findViewById(R.id.btn_open_menu)

        progressBarDrink = view.findViewById(R.id.progress_bar_drink)
        progressBarDrink.max = 0
        progressBarDrink.progress = 0

        tvProgressBarDrink = view.findViewById(R.id.tv_progress_bar_drink)
        tvProgressBarDrink.text = "0%"

        val fragmentMenu = MenuFragment()

        tvTarget = view.findViewById<TextView>(R.id.tv_target)
        tvDone = view.findViewById<TextView>(R.id.tv_done)

        val myDB = SQLiteHelper(activity as AppCompatActivity)
        val cursor1 = myDB.getUserById(1)
        val cursor2 = myDB.getUserHistory(1)
        var done = 0
        var target = 0

        if (cursor1.moveToFirst()) {
            target = cursor1.getInt(6)
            tvTarget.text = target.toString() + " ml"
            progressBarDrink.max = cursor1.getInt(6)
            cursor1.close()
        }

        if (cursor2.count > 0) {
            while (cursor2.moveToNext()) {
                var currentDataDate = LocalDate.parse(
                    cursor2.getString(4),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                ).toString()
                var todayDate = SimpleDateFormat("yyyy-MM-dd").format(Date())

                if (currentDataDate.equals(todayDate)) {
                    done += cursor2.getInt(3)
                }
            }
            progressBarDrink.progress = done

            var percentage = ((done.toDouble() / target.toDouble()) * 100).roundToInt()

            if (percentage >= 100) {
                tvProgressBarDrink.text = "100%"
            } else {
                tvProgressBarDrink.text = percentage.toString() + "%"
            }
        }

        tvDone.text = done.toString() + " ml"

        btnOpenMenu.setOnClickListener {
            requireActivity().supportFragmentManager?.beginTransaction()
                .replace(R.id.fragment_container, fragmentMenu).addToBackStack(null).commit()
        }

        (activity as MainActivity).bottomNav.menu.getItem(0).isChecked = true

        return view
    }
}