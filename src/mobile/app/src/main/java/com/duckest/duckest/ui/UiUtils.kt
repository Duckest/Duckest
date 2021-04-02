package com.duckest.duckest.ui

import com.duckest.duckest.R
import com.duckest.duckest.ui.home.feed.adapter.TestItem
import com.duckest.duckest.ui.home.testlevel.adapter.LevelItem

object UiUtils {
    fun getTests() = listOf(
        TestItem(R.string.feed_test_title_javascript, R.drawable.img_javascript, done = false),
        TestItem(R.string.feed_test_title_python, R.drawable.img_python, done = false),
        TestItem(R.string.feed_test_title_python, R.drawable.img_python, done = true)
    )

    fun getLevels() = listOf(
        LevelItem(R.string.level_title_junior, R.drawable.img_python, done = false),
        LevelItem(R.string.level_title_middle, R.drawable.img_python, done = false),
        LevelItem(R.string.level_title_senior, R.drawable.img_python, done = true)
    )
}