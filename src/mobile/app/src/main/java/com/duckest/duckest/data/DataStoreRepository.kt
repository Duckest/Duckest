package com.duckest.duckest.data

import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(
    private val prefs: SharedPreferences
) {
    private var userId: String? = null
    fun getUserId(): String? {
        if (userId != null) return userId
        if (prefs.contains(PREF_KEY_ID)) {
            userId = prefs.getString(PREF_KEY_ID, null) ?: ""
        }
        return userId
    }

    fun saveUserId(id: String) {
        prefs.edit {
            putString(PREF_KEY_ID, id)
        }
    }

    fun deleteUserId() {
        prefs.edit {
            if (prefs.contains(PREF_KEY_ID)) {
                remove(PREF_KEY_ID)
            }
        }
        userId = null
    }

    companion object {
        const val PREF_KEY_ID = "PREF_KEY_ID"
    }
}