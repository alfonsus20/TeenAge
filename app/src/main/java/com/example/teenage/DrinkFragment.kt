package com.example.teenage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.get
import androidx.fragment.app.Fragment

class DrinkFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater!!.inflate(R.layout.fragment_drink, container, false)

        val btnOpenMenu: Button = view.findViewById(R.id.btn_open_menu)

        val fragmentMenu = MenuFragment()

        btnOpenMenu.setOnClickListener {
            requireActivity().supportFragmentManager?.beginTransaction()
                .replace(R.id.fragment_container, fragmentMenu).commit()
        }

        (activity as MainActivity).bottomNav.menu.getItem(0).isChecked = true

        return view
    }
}