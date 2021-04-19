package com.duckest.duckest

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

object Utils {
    var context: Context? = null
        set(value) {
            field = value?.applicationContext
        }

    fun setError(inputLayout: TextInputLayout, message: String) {
        inputLayout.isErrorEnabled = true
        inputLayout.error = message
    }

    fun isEmptyField(edit: TextInputEditText, layout: TextInputLayout): Boolean {
        if (edit.text.toString().trim().isEmpty()) {
            context?.let {
                setError(layout, context!!.getString(R.string.sign_up_error_title_empty))
            }
            return true
        }
        return false
    }

    fun setTextChangeListener(edit: TextInputEditText, layout: TextInputLayout) {
        edit.addTextChangedListener {
            if (!it.isNullOrEmpty()) {
                layout.isErrorEnabled = false
            }
        }
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