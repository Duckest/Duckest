package com.duckest.duckest.ui.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duckest.duckest.data.DataStoreRepository
import com.duckest.duckest.data.Error
import com.duckest.duckest.data.NetworkResult
import com.duckest.duckest.data.network.RemoteDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: DataStoreRepository,
    private val remoteRepository: RemoteDataSource,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    val response: MutableLiveData<NetworkResult<Status>>
        get() = _response
    private val _response = MutableLiveData<NetworkResult<Status>>()

    val responseUser: MutableLiveData<NetworkResult<UserStatus>>
        get() = _responseUser
    private val _responseUser = MutableLiveData<NetworkResult<UserStatus>>()

    val error: MutableLiveData<Error>
        get() = _error
    private val _error = MutableLiveData<Error>()

    fun signIn(email: String, password: String) = viewModelScope.launch {
        response.value = NetworkResult.Loading()
        firebaseAuth.signInWithEmailAndPassword(
            email, password
        ).addOnSuccessListener {
            val user = it.user
            when {
                user == null -> {
                    // TODO: 04.04.2021  
                }
                user.isEmailVerified -> {
                    repository.saveUserEmail(email)
                    response.value = NetworkResult.Success(Status.VERIFIED)
                }
                else -> {
                    response.value = NetworkResult.Success(Status.NOT_VERIFIED)
                }
            }
        }.addOnFailureListener { e ->
            when (e) {
                is FirebaseAuthInvalidCredentialsException -> {
                    error.value = Error.WRONG_PASSWORD
                }
                is FirebaseAuthInvalidUserException -> {
                    error.value = Error.WRONG_EMAIL
                }
            }
        }
    }

    fun clearEmail() {
        repository.deleteAllData()
    }

    fun getUserInfo() = viewModelScope.launch {
        try {
            val email = repository.getUserEmail()
            if (!email.isNullOrEmpty()) {
                val res = remoteRepository.getUser(email)
                if (res != null) {
                    repository.saveUser(res)
                    _responseUser.value = NetworkResult.Success(UserStatus.SUCCESSFUL)
                } else {
                    _responseUser.value =
                        NetworkResult.Error(
                            message = "Данные о пользователя не найдены",
                            data = UserStatus.NO_DATA
                        )
                }
            } else {
                _responseUser.value =
                    NetworkResult.Error(
                        message = "Что-то пошло не так, попробуйте войти позже",
                        data = UserStatus.EMPTY_EMAIL
                    )
            }
        } catch (e: UnknownHostException) {
            _response.value =
                NetworkResult.Error(message = "Невозможно подключиться к сервису, проверьте свое подключение к интернету")
        } catch (e: Exception) {
            _responseUser.value = NetworkResult.Error(message = e.message, data = UserStatus.ERROR)
        }
    }

    enum class Status {
        NOT_VERIFIED, VERIFIED
    }

    enum class UserStatus {
        SUCCESSFUL, NO_DATA, EMPTY_EMAIL, ERROR
    }
}