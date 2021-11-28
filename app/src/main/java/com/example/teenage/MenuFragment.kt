package com.example.teenage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class MenuFragment : NavigationChildFragment() {
    private lateinit var rvDrinks: RecyclerView
    lateinit var etDrinkVolume: EditText
    lateinit var btnOk: Button
    lateinit var gridDrinkAdapter: GridDrinkAdapter
    private var list: ArrayList<Drink> = arrayListOf()

    @SuppressLint("RestrictedApi")
    override fun onCreateChildView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.setShowHideAnimationEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setTitle(Html.fromHtml("<font color='#14279B'>Menu</font>"))
        (activity as AppCompatActivity).supportActionBar?.show()

        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        etDrinkVolume = view.findViewById(R.id.tv_drink_volume)
        btnOk = view.findViewById(R.id.btn_ok)

        val myDB = SQLiteHelper(activity as AppCompatActivity)
        val cursor = myDB.getDrinks()

        if (cursor.count == 0) {
            myDB.insertDrinks()
        }

        val cursor2 = myDB.getDrinks()

        while (cursor2.moveToNext()) {
            list.add(
                Drink(
                    cursor2.getInt(0),
                    cursor2.getString(1),
                    DrinksData.drinkPictures[cursor2.getInt(3)],
                    cursor2.getDouble(2)
                )
            )
        }

        rvDrinks = view.findViewById(R.id.rv_drinks)
        rvDrinks.setHasFixedSize(true)

        rvDrinks.layoutManager = GridLayoutManager(activity, 3)
        gridDrinkAdapter = GridDrinkAdapter(list)
        rvDrinks.adapter = gridDrinkAdapter
        rvDrinks.addItemDecoration(GridSpacing(12))

        btnOk.setOnClickListener {
            if (checkFieldValid()) {
                myDB.insertHistory(
                    HistoryModel(
                        1,
                        gridDrinkAdapter.selectedDrinkIndex,
                        (etDrinkVolume.text.toString()
                            .toInt() * gridDrinkAdapter.selectedDrinkRate).roundToInt()
                    )
                )
                Toast.makeText(activity, "History Berhasil Ditambahkan", Toast.LENGTH_LONG).show()
                requireActivity().supportFragmentManager?.beginTransaction()
                    .replace(R.id.fragment_container, DrinkFragment()).addToBackStack(null).commit()
            }
        }
        return view
    }


    fun checkFieldValid(): Boolean {
        if (gridDrinkAdapter.selectedDrinkIndex == -1) {
            Toast.makeText(activity, "Pilih Jenis Minuman", Toast.LENGTH_LONG).show()
            return false
        }

        if (TextUtils.isEmpty(etDrinkVolume.text)) {
            Toast.makeText(activity, "Masukkan Volume Minuman", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    override fun onDestroyView() {
        (activity as AppCompatActivity).supportActionBar?.hide()
        super.onDestroyView()
    }
}