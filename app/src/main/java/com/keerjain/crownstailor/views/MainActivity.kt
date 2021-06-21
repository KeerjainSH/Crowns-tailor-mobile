package com.keerjain.crownstailor.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.utils.SessionManager
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val sessionManager by inject<SessionManager>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_CrownsTailor)

        AppCompatDelegate
            .setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO
            )

        val isLoggedIn = sessionManager.getLoginState()

        if (!isLoggedIn) {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            this@MainActivity.finishAfterTransition()
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.main_container)

        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.navigation_home, R.id.navigation_offer, R.id.navigation_order, R.id.navigation_other
        ).build()

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}