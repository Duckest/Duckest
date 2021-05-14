package com.duckest.duckest.ui.home.testlevel.adapter

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class LevelItem(
    val title: String?,
    @DrawableRes
    val icon: Int,
    var done: Boolean? = false,
    val progress: Double?
)