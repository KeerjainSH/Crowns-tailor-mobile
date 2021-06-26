package com.keerjain.crownstailor.views

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.keerjain.crownstailor.R

class LoginActivity : AppCompatActivity() {

    private var doubleBackToExitOnce: Boolean = false
    private lateinit var navHostFragment: NavHostFragment
    private var isRegistration: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenCreated {
            navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment_login) as NavHostFragment
        }

        setContentView(R.layout.login_activity)
    }

    override fun onBackPressed() {
        if (navHostFragment.childFragmentManager.backStackEntryCount > 0) {
            if (isRegistration) {
                return
            } else {
                navHostFragment.navController.navigateUp()
                return
            }
        } else {
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
                kotlin.run {
                    this.doubleBackToExitOnce = false
                    Log.d("Double Back", doubleBackToExitOnce.toString())
                }
            }, 2000)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return false
    }

    fun enterRegistrationState() {
        isRegistration = true
    }
}