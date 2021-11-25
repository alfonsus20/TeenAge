package com.example.teenage

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HistoryDetailFragment : NavigationChildFragment() {
    private lateinit var tvHistoryDetail: TextView
    private lateinit var progressBar: ProgressBar

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateChildView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history_detail, container, false)
        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(requireArguments().getString("date"), dateFormat)
        val formattedDate = "${date.dayOfMonth} ${date.month.toString().toLowerCase().capitalize()} ${date.year}"

        tvHistoryDetail = view.findViewById(R.id.tv_history_detail)
        tvHistoryDetail.text = formattedDate

        progressBar = view.findViewById(R.id.progressBar)
        progressBar.max = 2000

        ObjectAnimator.ofInt(progressBar, "progress", 1600).setDuration(1500).start()

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