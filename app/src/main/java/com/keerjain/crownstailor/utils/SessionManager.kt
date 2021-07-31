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
        session.userId?.let { editor.putInt(KEY_USER_ID, it) }
        session.token?.let { editor.putString(KEY_TOKEN, it) }
        editor.commit()
    }

    fun getToken() = pref.getString(KEY_TOKEN, "null")

    fun getUserId() = pref.getInt(KEY_USER_ID, 0)

    fun changeLoginFullName(fullName: String) {
        editor.putString(KEY_FULL_NAME, fullName)
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
                name = pref.getString(KEY_FULL_NAME, "null").toString(),
                userId = pref.getInt(KEY_USER_ID, 0),
                token = pref.getString(KEY_TOKEN, "null").toString()
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
        const val KEY_USER_ID = "userId"
        const val KEY_TOKEN = "token"
    }
}