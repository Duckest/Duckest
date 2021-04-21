package com.duckest.duckest

import android.content.Context
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

object Utils {

    fun setError(inputLayout: TextInputLayout, message: String) {
        inputLayout.isErrorEnabled = true
        inputLayout.error = message
    }

    fun isEmptyField(edit: TextInputEditText, layout: TextInputLayout, context: Context): Boolean {
        if (edit.text.toString().trim().isEmpty()) {
            setError(layout, context.getString(R.string.sign_up_error_title_empty))
            return true
        }
        return false
    }

    fun checkEmailPattern(edit: TextInputEditText, layout: TextInputLayout, context: Context): Boolean {
        if (!Patterns.EMAIL_ADDRESS
                .matcher(edit.text.toString().trim())
                .matches()
        ) {
            setError(layout, context.getString(R.string.sign_up_error_title_wrong_email))
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


    fun checkName(edit: TextInputEditText, layout: TextInputLayout, context: Context): Boolean {
        //for testing able to use latin and cyrillic alphabet
        val regex = "^[a-zA-ZА-Яа-я]*\$".toRegex()
        // val regex  = "^[А-Яа-я]*\$".toRegex()
        val name = edit.text.toString().trim()
        if (!regex.matches(name)) {
            setError(layout, context.getString(R.string.sign_up_wrong_name))
            return true
        }
        return false
    }

    fun hideKeyboard(context: Context, view: View) =
        (context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as? InputMethodManager)!!
            .hideSoftInputFromWindow(view.windowToken, 0)
}
