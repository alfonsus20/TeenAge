package com.example.teenage

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.roundToInt

class HistoryDetailFragment : NavigationChildFragment() {
    private lateinit var tvHistoryDetail: TextView
    private lateinit var tvTarget: TextView
    private lateinit var tvDone: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var circleProgressBar: ProgressBar
    private lateinit var tvPercentage: TextView
    private lateinit var rvHistory: RecyclerView
    var listHistory = arrayListOf<HistoryCompleteModel>()
    var cal = Calendar.getInstance()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateChildView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myDB = SQLiteHelper(activity as AppCompatActivity)
        val view = inflater.inflate(R.layout.fragment_history_detail, container, false)
        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(requireArguments().getString("date"), dateFormat)
        val formattedDate =
            "${date.dayOfMonth} ${date.month.toString().toLowerCase().capitalize()} ${date.year}"

        val cursor = myDB.getUserHistory(1)

        tvHistoryDetail = view.findViewById(R.id.tv_history_detail)
        tvHistoryDetail.text = formattedDate

        tvTarget = view.findViewById(R.id.tv_target_history)
        tvDone = view.findViewById(R.id.tv_done_history)

        var done = 0
        var target = 0

        val cursor2 = myDB.getUserById(1)

        if (cursor2.moveToFirst()) {
            target = cursor2.getInt(6)
            tvTarget.text = target.toString() + " ml"
            cursor2.close()
        }

        while (cursor.moveToNext()) {
            var dateFormatted = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

            if (LocalDate.parse(
                    cursor.getString(4),
                    dateFormatted
                ).toString().equals(requireArguments().getString("date"))
            ) {
                var sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
                cal.setTime(sdf.parse(cursor.getString(4)))

                var hours = cal.get(Calendar.HOUR)
                var minutes = cal.get(Calendar.MINUTE)

                var hourStr = if (hours < 10) "0" + hours.toString() else hours.toString()
                var hourMinute = if (minutes < 10) "0" + minutes.toString() else minutes.toString()

                listHistory.add(
                    HistoryCompleteModel(
                        cursor.getString(6),
                        cursor.getInt(8),
                        cursor.getInt(3),
                        "$hourStr:$hourMinute"
                    )
                )
                done += cursor.getInt(3)
            }
        }

        tvDone.text = done.toString() + " ml"


        progressBar = view.findViewById(R.id.progressBar_linear)
        progressBar.max = target
        progressBar.progress = done

        circleProgressBar = view.findViewById(R.id.progress_bar_circle)
        circleProgressBar.max = target
        circleProgressBar.progress = done

        var percentage = ((done.toDouble() / target.toDouble()) * 100).roundToInt()

        tvPercentage = view.findViewById(R.id.percentage_history)

        if (percentage >= 100) {
            tvPercentage.text = "100%"
        } else {
            tvPercentage.text  = percentage.toString() + "%"
        }

        ObjectAnimator.ofInt(progressBar, "progress", done).setDuration(1500).start()

        rvHistory = view.findViewById(R.id.rv_history)
        rvHistory.setHasFixedSize(true)
        rvHistory.layoutManager = LinearLayoutManager(activity)
        rvHistory.adapter = HistoryAdapter(listHistory)

        return view
    }

    @SuppressLint("RestrictedApi")
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setShowHideAnimationEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setTitle(Html.fromHtml("<font color='#14279B'>Detail</font>"))
        (activity as AppCompatActivity).supportActionBar?.show()
    }

    override fun onStop() {
        (activity as AppCompatActivity).supportActionBar?.hide()
        super.onStop()
    }
}