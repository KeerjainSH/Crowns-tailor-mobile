package com.keerjain.crownstailor.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
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
    }
}