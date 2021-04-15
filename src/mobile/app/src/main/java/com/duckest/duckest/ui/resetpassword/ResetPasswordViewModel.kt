package com.duckest.duckest.ui.resetpassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duckest.duckest.data.Error
import com.duckest.duckest.data.NetworkResult
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    val response: MutableLiveData<NetworkResult<Status>>
        get() = _response
    private val _response = MutableLiveData<NetworkResult<Status>>()
    fun resetPassword(email: String) = viewModelScope.launch {
        _response.value = NetworkResult.Loading()
        firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener {
            _response.value = NetworkResult.Success(Status.SENT)
        }.addOnFailureListener {
            _response.value = NetworkResult.Error(it.message, Error.WRONG_EMAIL, Status.ERROR)
        }

    }

    enum class Status {
        SENT, ERROR
    }
}