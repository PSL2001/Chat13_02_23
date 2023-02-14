package com.example.chat13_02_2023.prefs

import android.content.Context

class Prefs(c: Context) {
    val storage = c.getSharedPreferences("ChatMaster", 0)

    fun saveEmail(email: String) {
        storage.edit().putString("email", email).apply()
    }

    fun getEmail(): String? {
        return storage.getString("email", "")
    }

    fun deleteAll() {
        storage.edit().clear().apply()
    }
}