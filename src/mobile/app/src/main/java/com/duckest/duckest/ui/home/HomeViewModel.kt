package com.duckest.duckest.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duckest.duckest.data.DataStoreRepository
import com.duckest.duckest.data.domain.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: DataStoreRepository
) : ViewModel() {
    val user: LiveData<UserProfile>
        get() = _user
    private val _user = MutableLiveData<UserProfile>()
    fun getUser() = viewModelScope.launch {
        repository.getUserEmail()?.let {
            _user.value = repository.getUser()
        }
    }

    fun clear() = viewModelScope.launch {
        repository.deleteAllData()
    }
}