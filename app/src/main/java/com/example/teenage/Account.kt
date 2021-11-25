package com.example.teenage

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class Account : AppCompatActivity() {

    lateinit var birthDateText: TextView
    var cal = Calendar.getInstance()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        var formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        birthDateText = this.findViewById(R.id.birthDateText)

        birthDateText.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                var date = LocalDate.of(mYear, mMonth+1, mDay).format(formatter)
                birthDateText.text = date.toString()
            },year, month, day )
            dpd.show()
        }
    }
}