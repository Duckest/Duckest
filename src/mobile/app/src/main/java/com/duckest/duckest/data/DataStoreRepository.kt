package com.duckest.duckest.data

import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(
    private val prefs: SharedPreferences
) {
    private var userEmail: String? = null
    fun getUserEmail(): String? {
        if (userEmail != null) return userEmail
        if (prefs.contains(PREF_KEY_ID)) {
            userEmail = prefs.getString(PREF_KEY_ID, null) ?: ""
        }
        return userEmail
    }

    fun saveUserEmail(id: String) {
        prefs.edit {
            putString(PREF_KEY_ID, id)
        }
    }

    fun deleteUserEmail() {
        prefs.edit {
            if (prefs.contains(PREF_KEY_ID)) {
                remove(PREF_KEY_ID)
            }
        }
        userEmail = null
    }

    companion object {
        const val PREF_KEY_ID = "PREF_KEY_ID"
    }
}