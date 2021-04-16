package com.duckest.duckest.ui.intro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duckest.duckest.data.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    val needToNavigate: LiveData<Endpoint?>
        get() = _needToNavigate
    private val _needToNavigate = MutableLiveData<Endpoint?>()
    fun getUser() = viewModelScope.launch {
        val user = dataStoreRepository.getUserEmail()
        if (user == null) {
            _needToNavigate.value = Endpoint.LOGIN
        } else {
            _needToNavigate.value = Endpoint.FEED
        }
    }

    enum class Endpoint {
        FEED,
        LOGIN
    }
}