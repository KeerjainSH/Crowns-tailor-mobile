package com.keerjain.crownstailor.utils

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.keerjain.crownstailor.data.entities.detail.TailorSession

class SessionManager(context: Context) {
    val spec = KeyGenParameterSpec.Builder(
        MasterKey.DEFAULT_MASTER_KEY_ALIAS,
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    )
        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
        .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
        .build()

    val masterKey = MasterKey.Builder(context)
        .setKeyGenParameterSpec(spec)
        .build()

    private var pref: SharedPreferences = EncryptedSharedPreferences
        .create(
            context,
            "Session",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

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

    companion object {
        const val KEY_LOGIN = "isLoggedIn"
        const val KEY_USERNAME = "username"
        const val KEY_FULL_NAME = "fullName"
        const val KEY_USER_ID = "userId"
        const val KEY_TOKEN = "token"
    }
}