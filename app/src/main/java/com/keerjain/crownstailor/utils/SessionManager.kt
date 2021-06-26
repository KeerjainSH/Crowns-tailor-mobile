package com.keerjain.crownstailor.utils

import android.content.Context
import android.content.SharedPreferences
import com.keerjain.crownstailor.data.entities.detail.TailorSession

class SessionManager(context: Context) {
    private var pref: SharedPreferences =
        context.getSharedPreferences("Session", Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = pref.edit()

    fun createLoginSession(session: TailorSession) {
        editor.putBoolean(KEY_LOGIN, true)
        editor.putString(KEY_USERNAME, session.username)
        editor.putString(KEY_FULL_NAME, session.name)
        editor.commit()
    }

    fun logout() {
        editor.clear()
        editor.commit()
    }

    fun getLoginState() = pref.getBoolean(KEY_LOGIN, false)

    fun getSessionData(): TailorSession? {
        val isLoggedIn = getLoginState()
        return if (isLoggedIn) {
            TailorSession(
                username = pref.getString(KEY_USERNAME, "null").toString(),
                name = pref.getString(KEY_FULL_NAME, "null").toString()
            )
        } else {
            null
        }
    }

    fun saveToPreference(key: String, value: String) = editor.putString(key, value).commit()

    fun getFromPreference(key: String) = pref.getString(key, "")

    companion object {
        const val KEY_LOGIN = "isLoggedIn"
        const val KEY_USERNAME = "username"
        const val KEY_FULL_NAME = "fullName"
    }
}