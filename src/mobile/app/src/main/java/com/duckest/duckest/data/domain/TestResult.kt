package com.duckest.duckest.data.domain

import com.google.gson.annotations.SerializedName

data class TestResult(
    val email: String,
    @SerializedName("test_type")
    val testType: String?,
    @SerializedName("test_level")
    val testLevel: String?,
    val result: Double?
)
