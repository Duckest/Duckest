package com.duckest.duckest.ui.home.feed.adapter

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

class TestItem(
    @StringRes
    val title: Int,
    @DrawableRes
    val icon: Int,
    var done: Boolean = false
)