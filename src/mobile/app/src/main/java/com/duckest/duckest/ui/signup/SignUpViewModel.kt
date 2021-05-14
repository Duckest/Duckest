package com.duckest.duckest.ui.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duckest.duckest.data.Error
import com.duckest.duckest.data.NetworkResult
import com.duckest.duckest.data.domain.UserProfile
import com.duckest.duckest.data.network.RemoteDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val remoteDataSource: RemoteDataSource
) : ViewModel() {

    private val _error = MutableLiveData<Error>()
    val error: LiveData<Error>
        get() = _error

    private val _responseFirebase = MutableLiveData<NetworkResult<UserProfile>>()
    val responseFirebase: LiveData<NetworkResult<UserProfile>>
        get() = _responseFirebase

    private val _response = MutableLiveData<NetworkResult<String>>()
    val response: LiveData<NetworkResult<String>>
        get() = _response

    fun registerUser(user: UserProfile, password: String) = viewModelScope.launch {
        _responseFirebase.value = NetworkResult.Loading()
        firebaseAuth.createUserWithEmailAndPassword(
            user.email,
            password
        ).addOnSuccessListener {
            it.user!!.sendEmailVerification().addOnSuccessListener {
                _responseFirebase.value = NetworkResult.Success(data = user)
            }.addOnFailureListener {
                _responseFirebase.value = NetworkResult.Error(
                    data = null,
                    message = "Невозможно отправить подтверждение",
                    typeError = null
                )
            }

        }.addOnFailureListener { e ->
            when (e) {
                is FirebaseAuthWeakPasswordException -> _error.value = Error.WEAK_PASSWORD
                is FirebaseAuthInvalidCredentialsException -> _error.value = Error.OTHER
                is FirebaseAuthUserCollisionException -> _error.value = Error.EMAIL_ALREADY_EXIST
            }
        }
    }

    fun registerUser(user: UserProfile?) = viewModelScope.launch {
        try {
            remoteDataSource.signUpUser(user)
            _response.value = NetworkResult.Success(data = "user saved")
        } catch (e: Exception) {
            _response.value = NetworkResult.Error(message = e.message)
        }
    }
}