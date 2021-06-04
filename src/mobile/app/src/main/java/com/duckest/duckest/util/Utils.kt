package com.duckest.duckest.util

import android.content.Context
import android.graphics.*
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.duckest.duckest.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlin.math.roundToInt

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

    fun checkEmailPattern(
        edit: TextInputEditText,
        layout: TextInputLayout,
        context: Context
    ): Boolean {
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
        // val regex = "^[a-zA-ZА-Яа-я]*\$".toRegex()
        val regex = "^[А-Яа-я]*\$".toRegex()
        val name = edit.text.toString().trim()
        if (!regex.matches(name)) {
            setError(layout, context.getString(R.string.sign_up_wrong_name))
            return true
        }
        return false
    }

    fun result(score: Int, total: Int) =
        (((score * 1.0) / total) * 100)


    fun hideKeyboard(context: Context, view: View) =
        (context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as? InputMethodManager)!!
            .hideSoftInputFromWindow(view.windowToken, 0)

//    private fun getTextSize(text1: String, text2: String) =
//        if (text1.length >= 21 || text2.length >= 20) 90 else 125

    fun drawTextToBitmap(
        context: Context,
        gResId: Int,
        textSize: Int = 30,
        text1: String,
        text2: String
    ): Bitmap {
        val resources = context.resources
        val scale = resources.displayMetrics.density
        var bitmap = BitmapFactory.decodeResource(resources, gResId)
        var bitmapConfig = bitmap.config
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888
        }
        bitmap = bitmap.copy(bitmapConfig, true)
        val canvas = Canvas(bitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.BLACK
        paint.textSize = (textSize * scale).roundToInt().toFloat()
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE)
        val bounds = Rect()
        paint.getTextBounds(text1, 0, text1.length, bounds)
        val xOffset = (13.33f * scale + 0.5f).toInt()
        val yOffset = (14.67f * scale + 0.5f).toInt()
        var x = (bitmap.width - bounds.width()) / 2f - 650
        var y = (bitmap.height + bounds.height()) / 2f - 200
        canvas.drawText(text1, x + xOffset, y + yOffset, paint)
        paint.getTextBounds(text2, 0, text2.length, bounds)
        x = (bitmap.width - bounds.width()) / 2f - 700
        y = (bitmap.height + bounds.height()) / 2f + 60
        canvas.drawText(text2, x + xOffset, y + yOffset, paint)
        return bitmap
    }
}
