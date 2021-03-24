package com.duckest.duckest.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.duckest.duckest.data.Error
import com.duckest.duckest.data.NetworkResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _error = MutableLiveData<Error>()
    val error: LiveData<Error>
        get() = _error

    private val _response = MutableLiveData<NetworkResult<String>>()
    val response: LiveData<NetworkResult<String>>
        get() = _response

    fun registerUser(email: String, password: String) {
        _response.value = NetworkResult.Loading()
        firebaseAuth.createUserWithEmailAndPassword(
            email,
            password
        ).addOnSuccessListener {
            it.user!!.sendEmailVerification().addOnSuccessListener {
                _response.value = NetworkResult.Success("Подтвердите аккаунт на почте")
            }.addOnFailureListener {
                _response.value = NetworkResult.Error("Невозможно отправить подтверждение")
            }
        }.addOnFailureListener { e ->
            when (e) {
                is FirebaseAuthWeakPasswordException -> _error.value = Error.WEAK_PASSWORD
                is FirebaseAuthInvalidCredentialsException -> _error.value = Error.OTHER
                is FirebaseAuthUserCollisionException -> _error.value = Error.EMAIL_ALREADY_EXIST
            }

        }
//
    }


}