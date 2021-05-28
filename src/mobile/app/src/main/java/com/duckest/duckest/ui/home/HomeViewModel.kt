package com.duckest.duckest.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duckest.duckest.data.DataStoreRepository
import com.duckest.duckest.data.network.RemoteDataSource
import com.duckest.duckest.data.domain.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: DataStoreRepository,
    private val remoteRepository: RemoteDataSource
) : ViewModel() {
    val user: LiveData<UserProfile>
        get() = _user
    private val _user = MutableLiveData<UserProfile>()
    fun getUser() = viewModelScope.launch {
        repository.getUserEmail()?.let {
            try {
                _user.value = repository.getUser()
            } catch (e: Exception) {
                // TODO: 20.05.2021
            }
        }
    }

    fun clear() = viewModelScope.launch {
        repository.deleteAllData()
    }
}