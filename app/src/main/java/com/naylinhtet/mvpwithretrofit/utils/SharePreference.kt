package com.naylinhtet.mvpwithretrofit.utils

import android.content.Context
import android.content.SharedPreferences

class SharePreference(context: Context) {

    private val prefsName = "test"
    private val sharePref: SharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    fun save(KEY_NAME: String, text: String) {
        val editor: SharedPreferences.Editor = sharePref.edit()
        editor.putString(KEY_NAME, text)
        editor.apply()

    }

    fun save(KEY_NAME: String, value: Int) {
        val editor: SharedPreferences.Editor = sharePref.edit()
        editor.putInt(KEY_NAME, value)
        editor.apply()

    }

    fun save(KEY_NAME: String, status: Boolean) {
        val editor: SharedPreferences.Editor = sharePref.edit()
        editor.putBoolean(KEY_NAME, status)
        editor.apply()

    }


    fun getValueString(KEY_NAME: String): String? {
        return sharePref.getString(KEY_NAME, "")
    }

    fun getValueInt(KEY_NAME: String): Int {
        return sharePref.getInt(KEY_NAME, 0)

    }

    fun getValueBoolean(KEY_NAME: String, defaultValue: Boolean): Boolean {
        return sharePref.getBoolean(KEY_NAME, defaultValue)

    }

    fun removeAll() {
        val editor: SharedPreferences.Editor = sharePref.edit()
        editor.clear()
        editor.apply()
    }

    fun removeValue(KEY_NAME: String) {
        val editor: SharedPreferences.Editor = sharePref.edit()
        editor.remove(KEY_NAME)
        editor.apply()
    }
}