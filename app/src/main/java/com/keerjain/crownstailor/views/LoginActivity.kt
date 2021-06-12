package com.keerjain.crownstailor.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.keerjain.crownstailor.R
import com.keerjain.crownstailor.views.login.LoginFragment

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance())
                .commitNow()
        }
    }
}