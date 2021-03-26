package com.duckest.duckest

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import com.google.android.material.textfield.TextInputLayout

object Utils {
    fun setError(inputLayout: TextInputLayout, message: String) {
        inputLayout.isErrorEnabled = true
        inputLayout.error = message
    }

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