package com.duckest.duckest.data.network

import com.duckest.duckest.data.NetworkResult
import com.duckest.duckest.data.domain.*
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class RemoteDataSource @Inject constructor(
    private val duckestApi: DuckestApi
) {
    suspend fun signUpUser(user: UserProfile?) {
        duckestApi.signUpUser(user)
    }

    suspend fun getUser(email: String): UserProfile? {
        return duckestApi.getUserBy(email)
    }

    suspend fun updateUser(user: UserProfile) {
        return duckestApi.updateUserData(user)
    }

    suspend fun getTest(typeLevelPair: TypeLevelPair): Test {
        return duckestApi.getTest(typeLevelPair.testType, typeLevelPair.testLevel)
    }

    suspend fun getProgress(email: String): List<TestProgress> {
        return duckestApi.getProgress(email)
    }

    suspend fun sendProgress(testResult: TestResult): Boolean {
        return duckestApi.sendProgress(testResult)
    }
}