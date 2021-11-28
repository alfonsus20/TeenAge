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
    lateinit var radioMan: RadioButton
    lateinit var radioWoman: RadioButton

    var intMDay: Int = 0
    var intMMonth: Int = 0
    var intMYear: Int = 0


    var tanggalLahir: String = ""
    val myDB = SQLiteHelper(this)
    var userAlreadyExists = false

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

        radioGroupGender = findViewById<RadioGroup>(R.id.radioGroupGender)
        btnAccount = findViewById<Button>(R.id.btnAccount)
        etName = findViewById<EditText>(R.id.editTextName)
        etHeight = findViewById<EditText>(R.id.editTextHeight)
        etWeight = findViewById<EditText>(R.id.editTextWeight)
        radioMan = findViewById<RadioButton>(R.id.radioButtonMan)
        radioWoman = findViewById<RadioButton>(R.id.radioButtonWoman)

        var formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        var year = cal.get(Calendar.YEAR)
        var month = cal.get(Calendar.MONTH)
        var day = cal.get(Calendar.DAY_OF_MONTH)
        birthDateText = this.findViewById(R.id.birthDateText)

        val cursorCurrentUser = myDB.getUserById(1)

        if (cursorCurrentUser.moveToFirst()) {
            userAlreadyExists = true

            etName.setText(cursorCurrentUser.getString(1))
            etWeight.setText(cursorCurrentUser.getInt(3).toString())
            etHeight.setText(cursorCurrentUser.getInt(4).toString())
            if (cursorCurrentUser.getString(2).equals("male")) {
                radioMan.isChecked = true
            } else {
                radioWoman.isChecked = true
            }

            val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val date = LocalDate.parse(cursorCurrentUser.getString(5), dateFormat)
            val formattedDate =
                "${date.dayOfMonth} ${
                    date.month.toString().toLowerCase().capitalize()
                } ${date.year}"

            year = date.year
            month = date.monthValue
            day = date.dayOfMonth

            tanggalLahir =
                "$year-${if (month < 10) "0" + month else month}-${if (day < 10) "0" + day else day}"

            month -= 1

            birthDateText.setText(formattedDate)
            cursorCurrentUser.close()
        }

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

        btnAccount.setOnClickListener {
            if (checkAllField()) {
                val selectedGenderId = radioGroupGender.checkedRadioButtonId
                val gender =
                    findViewById<RadioButton>(selectedGenderId).text.toString().toLowerCase()

                val target: Int
                val tinggi = etHeight.text.toString().toInt()
                val berat = etWeight.text.toString().toInt()

                if (berat <= 10) {
                    target = 1000
                } else if (berat <= 20) {
                    target = 1500
                } else {
                    target = 1500 + (berat - 20) * 20
                }

                if (userAlreadyExists) {
                    myDB.updateUser(
                        UserModel(
                            etName.text.toString(),
                            gender,
                            etHeight.text.toString().toInt(),
                            etWeight.text.toString().toInt(),
                            tanggalLahir,
                            target,
                            1
                        )
                    )
                    Toast.makeText(
                        MainActivity@ this,
                        "Data berhasil diperbarui",
                        Toast.LENGTH_LONG
                    )
                } else {
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
                    myDB.insertAlarms()
                }
                Toast.makeText(MainActivity@ this, "Data berhasil disimpan", Toast.LENGTH_LONG)

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

}