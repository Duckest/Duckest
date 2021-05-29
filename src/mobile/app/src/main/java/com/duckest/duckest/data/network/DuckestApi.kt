package com.duckest.duckest.data.network

import com.duckest.duckest.data.domain.Test
import com.duckest.duckest.data.domain.TestProgress
import com.duckest.duckest.data.domain.TestResult
import com.duckest.duckest.data.domain.UserProfile
import retrofit2.http.*

interface DuckestApi {
    @GET("/user")
    suspend fun getUserBy(
        @Query("email")
        email: String
    ): UserProfile?

    @PUT("/user")
    suspend fun updateUserData(
        @Body
        user: UserProfile
    )

    @POST("/user")
    suspend fun signUpUser(
        @Body
        user: UserProfile?
    )

    @GET("/test")
    suspend fun getTest(
        @Query("test_type")
        testType: String,
        @Query("test_level")
        testLevel: String
    ): Test

    @GET("/progress")
    suspend fun getProgress(
        @Query("email")
        email: String
    ): List<TestProgress>

    @POST("/progress")
    suspend fun sendProgress(
        @Body
        testResult: TestResult
    ): Boolean
}