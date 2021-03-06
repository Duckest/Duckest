package com.duckest.duckest.ui.home.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duckest.duckest.data.DataStoreRepository
import com.duckest.duckest.data.NetworkResult
import com.duckest.duckest.data.domain.TestProgress
import com.duckest.duckest.data.network.RemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    repository: DataStoreRepository,
    private val remoteRepository: RemoteDataSource
) : ViewModel() {
    private val _response = MutableLiveData<NetworkResult<List<TestProgress>>>()
    val response: LiveData<NetworkResult<List<TestProgress>>>
        get() = _response
    val email = repository.getUserEmail()
    fun getProgress() = viewModelScope.launch {
        _response.value = NetworkResult.Loading()
        email?.let {
            try {
                val res = remoteRepository.getProgress(it)
                _response.value = NetworkResult.Success(res)
            } catch (e: IOException) {
                _response.value =
                    NetworkResult.Error(message = "Невозможно подключиться к сервису, проверьте свое подключение к интернету")
            } catch (e: Exception) {
                _response.value = NetworkResult.Error(message = e.message)
            }
        }
    }
}