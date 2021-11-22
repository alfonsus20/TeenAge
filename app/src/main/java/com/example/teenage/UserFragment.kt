package com.example.teenage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class UserFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        (activity as AppCompatActivity).supportActionBar?.hide()
        (activity as MainActivity).bottomNav.menu.getItem(3).isChecked = true
        return inflater.inflate(R.layout.fragment_user, container, false)
    }
}