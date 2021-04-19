package com.duckest.duckest.ui.home.testlevel.adapter

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class LevelItem(
    @StringRes
    val title: Int,
    @DrawableRes
    val icon: Int,
    var done: Boolean = false
)