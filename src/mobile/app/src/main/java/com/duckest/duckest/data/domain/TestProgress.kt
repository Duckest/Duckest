package com.duckest.duckest.data.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class TestProgress(
    @SerializedName("test_type")
    val testType: String?,
    @SerializedName("test_levels")
    val testLevelProgress: ArrayList<TestLevelProgress>?,
    @SerializedName("is_level_completed")
    val isLevelCompleted: Boolean?,
    @SerializedName("image_url")
    val imageUrl: String?
)

@Parcelize
data class TestLevelProgress(
    @SerializedName("test_level")
    val testLevel: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("is_test_completed")
    val isTestCompleted: Boolean?,
    @SerializedName("progress")
    val progress: Double?
) : Parcelable

@Parcelize
class TestLevelProgresses : ArrayList<TestLevelProgress>(), Parcelable