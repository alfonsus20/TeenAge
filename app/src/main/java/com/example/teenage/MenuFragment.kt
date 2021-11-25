package com.example.teenage

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MenuFragment : NavigationChildFragment() {
    private lateinit var rvDrinks: RecyclerView
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

        val myDB = SQLiteHelper(activity as AppCompatActivity)
        val cursor = myDB.getDrinks()

        if(cursor.count == 0){
            myDB.insertDrinks()
        }

        val cursor2 = myDB.getDrinks()

        while(cursor2.moveToNext()){
            list.add(Drink(cursor2.getString(1), cursor2.getInt(3), cursor2.getDouble(2)))
        }

        rvDrinks = view.findViewById(R.id.rv_drinks)
        rvDrinks.setHasFixedSize(true)

        rvDrinks.layoutManager = GridLayoutManager(activity, 3)
        rvDrinks.adapter = GridDrinkAdapter(list)
        rvDrinks.addItemDecoration(GridSpacing(12))

        return view
    }


    override fun onDestroyView() {
        (activity as AppCompatActivity).supportActionBar?.hide()
        super.onDestroyView()
    }
}