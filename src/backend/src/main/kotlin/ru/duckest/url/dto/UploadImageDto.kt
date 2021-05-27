package ru.duckest.url.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UploadImageDto(
    @JsonProperty("test_type") val testType: String?,
    @JsonProperty("image_url") val imageUrl: String?
)