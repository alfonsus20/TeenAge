package com.example.teenage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        replaceFragment(DrinkFragment())

        supportActionBar?.hide()

        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_drink -> replaceFragment(DrinkFragment())
                R.id.nav_schedule -> replaceFragment(HistoryFragment())
                R.id.nav_notification -> replaceFragment(NotificationFragment())
                R.id.nav_user -> replaceFragment(UserFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}