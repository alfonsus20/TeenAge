package com.example.teenage

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.roundToInt

class Account : AppCompatActivity() {
    var cal = Calendar.getInstance()
    lateinit var birthDateText: TextView
    lateinit var radioGroupGender: RadioGroup
    lateinit var btnAccount: Button
    lateinit var etName: EditText
    lateinit var etWeight: EditText
    lateinit var etHeight: EditText
    var intMDay: Int = 0
    var intMMonth: Int = 0
    var intMYear: Int = 0


    var tanggalLahir: String = ""
    val myDB = SQLiteHelper(this)

    @SuppressLint("RestrictedApi")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setShowHideAnimationEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true);
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        supportActionBar?.setTitle(Html.fromHtml("<font color='#14279B'>Account</font>"))
        supportActionBar?.show()

        setContentView(R.layout.activity_account)
        var formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        birthDateText = this.findViewById(R.id.birthDateText)

        birthDateText.setOnClickListener {
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    var date = LocalDate.of(mYear, mMonth + 1, mDay).format(formatter)
                    birthDateText.text = date.toString()

                    var monthStr = (mMonth + 1).toString()
                    var dayStr = mDay.toString()

                    intMDay = mDay
                    intMMonth = mMonth + 1
                    intMYear = mYear

                    if (mMonth < 10) {
                        monthStr = "0" + monthStr
                    }

                    if (mDay < 10) {
                        dayStr = "0" + dayStr
                    }

                    tanggalLahir = "$mYear-$monthStr-$dayStr"
                },
                year,
                month,
                day
            )
            dpd.show()
        }

        radioGroupGender = findViewById<RadioGroup>(R.id.radioGroupGender)
        btnAccount = findViewById<Button>(R.id.btnAccount)
        etName = findViewById<EditText>(R.id.editTextName)
        etHeight = findViewById<EditText>(R.id.editTextHeight)
        etWeight = findViewById<EditText>(R.id.editTextWeight)

        btnAccount.setOnClickListener {
            if (checkAllField()) {
                val selectedGenderId = radioGroupGender.checkedRadioButtonId
                val gender =
                    findViewById<RadioButton>(selectedGenderId).text.toString().toLowerCase()

                val target: Int
                val usia = getAge()
                val tinggi = etHeight.text.toString().toInt()
                val berat = etWeight.text.toString().toInt()

                if (berat <= 10) {
                    target = 1000
                } else if (berat <= 20) {
                    target = 1500
                } else {
                    target = 1500 + (berat - 20) * 20
                }

                myDB.insertUser(
                    UserModel(
                        etName.text.toString(),
                        gender,
                        etHeight.text.toString().toInt(),
                        etWeight.text.toString().toInt(),
                        tanggalLahir,
                        target
                    )
                )

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun checkAllField(): Boolean {
        val selectedGenderId = radioGroupGender.checkedRadioButtonId

        if (TextUtils.isEmpty(etName.text)) {
            Toast.makeText(this, "Masukkan Nama", Toast.LENGTH_LONG).show()
            return false
        }

        if (selectedGenderId == -1) {
            Toast.makeText(this, "Pilih Gender", Toast.LENGTH_LONG).show()
            return false
        }

        if (TextUtils.isEmpty(etWeight.text)) {
            Toast.makeText(this, "Masukkan Berat", Toast.LENGTH_LONG).show()
            return false
        }

        if (TextUtils.isEmpty(etHeight.text)) {
            Toast.makeText(this, "Masukkan Tinggi", Toast.LENGTH_LONG).show()
            return false
        }

        if (TextUtils.isEmpty(tanggalLahir)) {
            Toast.makeText(this, "Masukkan Tanggal Lahir", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAge(): Int {
        return Period.between(
            LocalDate.of(intMYear, intMMonth, intMMonth),
            LocalDate.now()
        ).years
    }
}