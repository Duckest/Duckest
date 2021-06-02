package com.duckest.duckest.ui.home.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.duckest.duckest.data.DataStoreRepository
import com.duckest.duckest.data.domain.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TestPassedViewModel @Inject constructor(
    val repository: DataStoreRepository
) : ViewModel() {
    private val _user = MutableLiveData<UserProfile>()
    val user: LiveData<UserProfile>
        get() = _user

    fun getUser() {
        _user.value = repository.getUser()
    }
}