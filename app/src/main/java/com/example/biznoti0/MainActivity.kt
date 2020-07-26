package com.example.biznoti0

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {

                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_Create -> {

                item.isChecked = false
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_Profile -> {

                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_dashboard -> {
               
                return@OnNavigationItemSelectedListener true
            }


        }

        false
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }
}
