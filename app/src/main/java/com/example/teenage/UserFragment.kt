package com.example.teenage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class UserFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user, container, false)

        val title: TextView = view.findViewById(R.id.TitleBar)
        title.setText("User")
        val accountTextView:TextView = view.findViewById(R.id.account)
        accountTextView.setOnClickListener {
            val intent = Intent (activity,Account::class.java)
            startActivity(intent)
        }

//        (activity as AppCompatActivity).supportActionBar?.hide()
        (activity as MainActivity).bottomNav.menu.getItem(3).isChecked = true
        return view
    }
}