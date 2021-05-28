package com.duckest.duckest.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.duckest.duckest.data.domain.UserProfile
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(
    private val prefs: SharedPreferences
) {
    private var userEmail: String? = null
    private var user: UserProfile? = null

    fun getUserEmail(): String? {
        if (userEmail != null) return userEmail
        if (prefs.contains(PREF_KEY_ID)) {
            userEmail = prefs.getString(PREF_KEY_ID, null) ?: ""
        }
        return userEmail
    }

    fun saveUserEmail(email: String) {
        prefs.edit {
            putString(PREF_KEY_ID, email)
        }
    }

    fun saveUser(userProfile: UserProfile) {
        user = null
        prefs.edit {
            putString(PREF_KEY_NAME, userProfile.name)
            putString(PREF_KEY_SURNAME, userProfile.surname)
            putString(PREF_KEY_PATRONYMIC, userProfile.patronymic)
            apply()
        }
    }

    fun getUser(): UserProfile? {
        if (user != null) return user
        if (prefs.contains(PREF_KEY_ID) &&
            prefs.contains(PREF_KEY_NAME) &&
            prefs.contains(PREF_KEY_SURNAME) &&
            prefs.contains(PREF_KEY_PATRONYMIC)
        ) {
            user = UserProfile(
                prefs.getString(PREF_KEY_ID, null) ?: "",
                prefs.getString(PREF_KEY_NAME, null) ?: "",
                prefs.getString(PREF_KEY_SURNAME, null) ?: "",
                prefs.getString(PREF_KEY_PATRONYMIC, null) ?: "",
            )

        }
        return user
    }

    fun deleteAllData() {
        prefs.edit {
            if (prefs.contains(PREF_KEY_ID)) {
                remove(PREF_KEY_ID)
            }
            if (prefs.contains(PREF_KEY_NAME)) {
                remove(PREF_KEY_NAME)
            }
            if (prefs.contains(PREF_KEY_SURNAME)) {
                remove(PREF_KEY_SURNAME)
            }
            if (prefs.contains(PREF_KEY_PATRONYMIC)) {
                remove(PREF_KEY_PATRONYMIC)
            }
        }
        userEmail = null
        user = null
    }

    companion object {
        const val PREF_KEY_ID = "PREF_KEY_ID"
        const val PREF_KEY_NAME = "PREF_KEY_NAME"
        const val PREF_KEY_SURNAME = "PREF_KEY_SURNAME"
        const val PREF_KEY_PATRONYMIC = "PREF_KEY_PATRONYMIC"

    }
}