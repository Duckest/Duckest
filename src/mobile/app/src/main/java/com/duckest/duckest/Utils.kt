package com.duckest.duckest

import android.app.Activity
import android.content.Context
import android.util.Patterns
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

    fun checkEmailPattern(edit: TextInputEditText, layout: TextInputLayout): Boolean {
        if (!Patterns.EMAIL_ADDRESS
                .matcher(edit.text.toString().trim())
                .matches()
        ) {
            setError(layout, context!!.getString(R.string.sign_up_error_title_wrong_email))
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


    fun checkName(edit: TextInputEditText, layout: TextInputLayout): Boolean {
        //for testing able to use latin and cyrillic alphabet
        val regex = "^[a-zA-ZА-Яа-я]*\$".toRegex()
        // val regex  = "^[А-Яа-я]*\$".toRegex()
        val name = edit.text.toString().trim()
        if (!regex.matches(name)) {
            setError(layout,  context!!.getString(R.string.sign_up_wrong_name))
            return true
        }
        return false
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