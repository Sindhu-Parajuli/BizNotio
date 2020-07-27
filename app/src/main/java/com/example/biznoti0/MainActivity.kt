package com.example.biznoti0

import Fragments.*
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {


    lateinit var Pfragment:ProfileFragment
    lateinit var Sfragment:SearchFragment
    lateinit var Nfragment:NotificationFragment
    lateinit var Hfragment:HomepageFragment
    lateinit var Cfragment :CreatePost


    @SuppressLint("SetTextI18n")
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                Hfragment = HomepageFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout,Hfragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()

                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_Create -> {
                Cfragment = CreatePost()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout,Cfragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()

                item.isChecked = false
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                Nfragment = NotificationFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout,Nfragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_Profile -> {

                Pfragment = ProfileFragment()
                supportFragmentManager
                   .beginTransaction()
                    .replace(R.id.frame_layout,Pfragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()

                return@OnNavigationItemSelectedListener true

            }

            R.id.search -> {

                Sfragment = SearchFragment()

                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout,Sfragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
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
        Hfragment = HomepageFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout,Hfragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()


    }





    }


