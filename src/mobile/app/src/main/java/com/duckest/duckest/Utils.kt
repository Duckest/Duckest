package com.duckest.duckest

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService

object Utils {
    fun hideKeyboard(activity: Activity) {
        // Check if no view has focus:
        val view = activity.currentFocus
        view?.let { v ->
            val imm: InputMethodManager? = activity.getSystemService()
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
            v.clearFocus()
        }
    }
}