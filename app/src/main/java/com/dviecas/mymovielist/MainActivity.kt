package com.dviecas.mymovielist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
    }

    fun setupNavigation() {
        NavigationUI.setupWithNavController(
            findViewById<BottomNavigationView>(R.id.bottom_navigation),
            (supportFragmentManager.findFragmentById(R.id.main_content) as NavHostFragment).navController)
    }

}