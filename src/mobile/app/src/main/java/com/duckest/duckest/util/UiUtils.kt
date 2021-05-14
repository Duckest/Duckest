package com.duckest.duckest.util

import com.duckest.duckest.R
import com.duckest.duckest.ui.home.feed.adapter.TestItem
import com.duckest.duckest.ui.home.testlevel.adapter.LevelItem

object UiUtils {
    fun getTests() = listOf(
        TestItem(R.string.feed_test_title_javascript, R.mipmap.img_javascript, done = false),
        TestItem(R.string.feed_test_title_python, R.mipmap.img_python, done = false),
        TestItem(R.string.feed_test_title_python, R.mipmap.img_python, done = true)
    )

//    fun getLevels() = listOf(
//        LevelItem(R.string.level_title_junior, R.mipmap.img_first, done = false),
//        LevelItem(R.string.level_title_middle, R.mipmap.img_second, done = false),
//        LevelItem(R.string.level_title_senior, R.mipmap.img_third, done = true)
//    )
}