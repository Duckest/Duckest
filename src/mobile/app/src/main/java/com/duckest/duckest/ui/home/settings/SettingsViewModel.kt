package com.duckest.duckest.ui.home.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duckest.duckest.data.DataStoreRepository
import com.duckest.duckest.data.Error
import com.duckest.duckest.data.NetworkResult
import com.duckest.duckest.data.domain.UserProfile
import com.duckest.duckest.data.network.RemoteDataSource
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val repository: DataStoreRepository,
    private val remoteRepository: RemoteDataSource
) : ViewModel() {
    val response: LiveData<NetworkResult<Status>>
        get() = _response
    private val _response = MutableLiveData<NetworkResult<Status>>()

    private val _userResponse = MutableLiveData<NetworkResult<Status>>()
    val userResponse: LiveData<NetworkResult<Status>>
        get() = _userResponse
    val user: LiveData<UserProfile?>
        get() = _user
    private val _user = MutableLiveData<UserProfile?>()

    var email: String? = repository.getUserEmail()

    fun getUserProfile() = viewModelScope.launch {
        try {
            email?.let {
                val res = repository.getUser()
                _user.value = res
            }
        } catch (e: UnknownHostException) {
            _response.value =
                NetworkResult.Error(message = "Невозможно подключиться к сервису, проверьте своеz подключение к интернету")
        } catch (e: Exception) {
            _response.value = NetworkResult.Error(message = e.message)
        }
    }

    fun updateUserProfile(name: String?, surname: String?, patronymic: String?) =
        viewModelScope.launch {
            _userResponse.value = NetworkResult.Loading()
            try {
                email?.let {
                    val user = UserProfile(
                        it,
                        name,
                        surname,
                        patronymic
                    )
                    remoteRepository.updateUser(
                        user
                    )
                    repository.saveUser(user)
                }
                _userResponse.value = NetworkResult.Success(Status.UPDATED)
            } catch (e: Exception) {
                _userResponse.value = NetworkResult.Error(e.message)
            }
        }

    fun changePassword(oldPassword: String, newPassword: String) = viewModelScope.launch {
        _response.value = NetworkResult.Loading()
        val user = firebaseAuth.currentUser!!
        val credential = EmailAuthProvider.getCredential(
            repository.getUserEmail()!!,
            oldPassword
        )
        user.reauthenticate(credential).addOnSuccessListener {
            user.updatePassword(newPassword).addOnSuccessListener {
                _response.value = NetworkResult.Success(Status.UPDATED)

            }.addOnFailureListener {
                when (it) {
                    is FirebaseAuthWeakPasswordException -> _response.value =
                        NetworkResult.Error(typeError = Error.WEAK_PASSWORD)
                    else -> _response.value = NetworkResult.Error(message = it.message)
                }
            }

        }.addOnFailureListener {
            when (it) {
                is FirebaseAuthInvalidCredentialsException -> _response.value =
                    NetworkResult.Error(typeError = Error.WRONG_PASSWORD)
                else -> _response.value = NetworkResult.Error(message = it.message)
            }
        }
    }

    enum class Status {
        UPDATED, ERROR
    }

}