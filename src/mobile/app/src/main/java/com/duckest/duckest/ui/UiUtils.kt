package com.duckest.duckest.ui

import com.duckest.duckest.R
import com.duckest.duckest.ui.home.feed.adapter.TestItem

object UiUtils {
    fun getTests() = listOf(
        TestItem(R.string.feed_test_title_javascript, R.drawable.img_javascript, done = false),
        TestItem(R.string.feed_test_title_python, R.drawable.img_python, done = false)
    )
}