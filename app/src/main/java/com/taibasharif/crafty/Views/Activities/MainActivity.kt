package com.taibasharif.crafty.Views.Activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.taibasharif.crafty.CloudinaryHelper.Companion.initializeCloudinary
import com.taibasharif.crafty.R
import com.taibasharif.crafty.ViewModels.MainVM
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    val viewModel = MainVM()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeCloudinary(this)


        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNavigationView.setupWithNavController(navController)


        val toolbar: Toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        val imageView = findViewById<ImageView>(R.id.drawer_icon)
        val showchats= findViewById<ImageView>(R.id.showchats)
        showchats.setOnClickListener {
            // Start the ChatListActivity when the admin clicks the show chats icon
            val intent = Intent(this,  ChatListActivity::class.java)
            startActivity(intent)
        }

        imageView.setOnClickListener { view: View? ->
            if (drawer.isDrawerVisible(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START)
            } else {
                drawer.openDrawer(GravityCompat.START)
            }
        }



        lifecycleScope.launch {
            viewModel.currentUser.collect {
                if (it == null) {
                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                    finish()
                }
                //TODO: display user data in nav drawer
            }
        }

    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.out) { // Logout menu item
            viewModel.logout()
        } else if (item.itemId==R.id.info){
        }
        return true

    }
    }





