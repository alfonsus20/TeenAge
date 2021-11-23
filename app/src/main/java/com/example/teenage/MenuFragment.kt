package com.example.teenage

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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

        rvDrinks = view.findViewById(R.id.rv_drinks)
        rvDrinks.setHasFixedSize(true)

        list.addAll(DrinksData.listData)
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