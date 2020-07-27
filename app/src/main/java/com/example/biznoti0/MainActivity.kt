package com.example.biznoti0

import Fragments.*
import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//        setSupportActionBar(toolbar)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.frame_layout) as NavHostFragment? ?: return

        // Set up Action Bar
        val navController = host.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)

        // TODO STEP 9.5 - Create an AppBarConfiguration with the correct top-level destinations
        // You should also remove the old appBarConfiguration setup above
//        val drawerLayout : DrawerLayout? = findViewById(R.id.drawer_layout)
//        appBarConfiguration = AppBarConfiguration(
//                setOf(R.id.home_dest, R.id.deeplink_dest),
//                drawerLayout)
        // TODO END STEP 9.5

        setupActionBar(navController, appBarConfiguration)

//        setupNavigationMenu(navController)

        setupBottomNavMenu(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                Integer.toString(destination.id)
            }

            Toast.makeText(this@MainActivity, "Navigated to $dest",
                Toast.LENGTH_SHORT).show()
            Log.d("NavigationActivity", "Navigated to $dest")
        }
    }

    private fun setupBottomNavMenu(navController: NavController) {
        // Use NavigationUI to set up Bottom Nav
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav?.setupWithNavController(navController)
    }

//    private fun setupNavigationMenu(navController: NavController) {
//        // In split screen mode, you can drag this view out from the left
//        // This does NOT modify the actionbar
//        val sideNavView = findViewById<NavigationView>(R.id.nav_view)
//        sideNavView?.setupWithNavController(navController)
//    }

    private fun setupActionBar(navController: NavController,
                               appBarConfig : AppBarConfiguration
    ) {
        // TODO STEP 9.6 - Have NavigationUI handle what your ActionBar displays
        // This allows NavigationUI to decide what label to show in the action bar
        // By using appBarConfig, it will also determine whether to
        // show the up arrow or drawer menu icon
//        setupActionBarWithNavController(navController, appBarConfig)
        // TODO END STEP 9.6
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        val retValue = super.onCreateOptionsMenu(menu)
//        val navigationView = findViewById<NavigationView>(R.id.nav_view)
//        // The NavigationView already has these same navigation items, so we only add
//        // navigation items to the menu here if there isn't a NavigationView
//        if (navigationView == null) {
//            menuInflater.inflate(R.menu.overflow_menu, menu)
//            return true
//        }
//        return retValue
//    }

    // this is the function that defines the interaction with the overflow menu on top right of app screen
    // has the settings options go to the settings fragment
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return super.onOptionsItemSelected(item)
        return item.onNavDestinationSelected(findNavController(R.id.frame_layout))
                || super.onOptionsItemSelected(item)
        // TODO STEP 9.2 - Have Navigation UI Handle the item selection - make sure to delete
        //  the old return statement above
//        // Have the NavigationUI look for an action or destination matching the menu
//        // item id and navigate there if found.
//        // Otherwise, bubble up to the parent.
//        return item.onNavDestinationSelected(findNavController(R.id.my_nav_host_fragment))
//                || super.onOptionsItemSelected(item)
        // TODO END STEP 9.2
    }

    // TODO STEP 9.7 - Have NavigationUI handle up behavior in the ActionBar
//    override fun onSupportNavigateUp(): Boolean {
//        // Allows NavigationUI to support proper up navigation or the drawer layout
//        // drawer menu, depending on the situation
//        return findNavController(R.id.my_nav_host_fragment).navigateUp(appBarConfiguration)
//    }
    // TODO END STEP 9.7

//    lateinit var Pfragment:ProfileFragment
//    lateinit var Sfragment:SearchFragment
//    lateinit var Nfragment:NotificationFragment
//    lateinit var Hfragment:HomepageFragment
//    lateinit var Cfragment :CreatePost
//
//
//    @SuppressLint("SetTextI18n")
//    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
//        when (item.itemId) {
//            R.id.navigation_home -> {
//                Hfragment = HomepageFragment()
//                supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.frame_layout,Hfragment)
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                    .commit()
//
//                return@OnNavigationItemSelectedListener true
//            }
//
//            R.id.navigation_Create -> {
//                Cfragment = CreatePost()
//                supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.frame_layout,Cfragment)
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                    .commit()
//
//                item.isChecked = false
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.navigation_notifications -> {
//                Nfragment = NotificationFragment()
//                supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.frame_layout,Nfragment)
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                    .commit()
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.navigation_Profile -> {
//
//                Pfragment = ProfileFragment()
//                supportFragmentManager
//                   .beginTransaction()
//                    .replace(R.id.frame_layout,Pfragment)
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                    .commit()
//
//                return@OnNavigationItemSelectedListener true
//
//            }
//
//            R.id.search -> {
//
//                Sfragment = SearchFragment()
//
//                supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.frame_layout,Sfragment)
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                    .commit()
//                return@OnNavigationItemSelectedListener true
//            }
//
//
//        }
//
//        false
//    }
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        val navView: BottomNavigationView = findViewById(R.id.bottom_nav_view)
//        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
//        Hfragment = HomepageFragment()
//        supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.frame_layout,Hfragment)
//            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//            .commit()
//
//
//    }
//
//
//
//

    }


