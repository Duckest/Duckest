package com.duckest.duckest.data.domain

data class UserProfile(
    val email: String,
    val name: String? = null,
    val surname: String? = null,
    val patronymic: String? = null
)