package com.keerjain.crownstailor.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private var pref: SharedPreferences =
        context.getSharedPreferences("Session", Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = pref.edit()

    fun createLoginSession(username: String) {
        editor.putBoolean(KEY_LOGIN, true)
        editor.putString(KEY_USERNAME, username)
        editor.commit()
    }

    fun logout() {
        editor.clear()
        editor.commit()
    }

    fun getLoginState() = pref.getBoolean(KEY_LOGIN, false)

    fun saveToPreference(key: String, value: String) = editor.putString(key, value).commit()

    fun getFromPreference(key: String) = pref.getString(key, "")

    companion object {
        const val KEY_LOGIN = "isLoggedIn"
        const val KEY_USERNAME = "username"
    }
}