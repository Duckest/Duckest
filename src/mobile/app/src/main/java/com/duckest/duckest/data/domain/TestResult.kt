package com.duckest.duckest.data.domain

import com.google.gson.annotations.SerializedName

data class TestResult(
    val email: String,
    val test_type: String?,
    val test_level: String?,
    val result: Double?
)
