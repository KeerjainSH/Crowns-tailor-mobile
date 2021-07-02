package com.keerjain.crownstailor.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.utils.SessionManager
import com.keerjain.crownstailor.views.settings.ProfileFragment
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private var doubleBackToExitOnce: Boolean = false
    private val sessionManager by inject<SessionManager>()
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navView: BottomNavigationView
    private var isSettings = false

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

        navView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.main_container)
        navView.setupWithNavController(navController)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
    }

    override fun onBackPressed() {
        if (navHostFragment.childFragmentManager.backStackEntryCount == 0) {
            if (doubleBackToExitOnce) {
                finishAfterTransition()
                return
            }

            this.doubleBackToExitOnce = true

            Toast.makeText(
                this,
                resources.getString(R.string.exit_confirmation),
                Toast.LENGTH_SHORT
            ).show()

            Handler(Looper.getMainLooper()).postDelayed({
                kotlin.run { doubleBackToExitOnce = false }
            }, 2000)
        } else {
            super.onBackPressed()
            return
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return false
    }

    fun showBottomBar() {
        navView.visibility = View.VISIBLE
    }

    fun removeBottomBar() {
        navView.visibility = View.GONE
    }

    fun goToSettings() {
        isSettings = true
    }

    fun outFromSettings() {
        isSettings = false
    }

    fun moveSettings(menu: Int) {
        if (isSettings) {
            val profileFragment = navHostFragment.childFragmentManager.fragments[0] as ProfileFragment
            profileFragment.moveToSetting(menu)
        }
    }
}