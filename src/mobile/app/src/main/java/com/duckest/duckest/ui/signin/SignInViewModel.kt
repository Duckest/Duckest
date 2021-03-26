package com.duckest.duckest.ui.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.duckest.duckest.data.Error
import com.duckest.duckest.data.NetworkResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    val response: MutableLiveData<NetworkResult<Status>>
        get() = _response
    private val _response = MutableLiveData<NetworkResult<Status>>()

    val error: MutableLiveData<Error>
        get() = _error
    private val _error = MutableLiveData<Error>()

    fun signIn(email: String, password: String) {
        response.value = NetworkResult.Loading()
        firebaseAuth.signInWithEmailAndPassword(
            email, password
        ).addOnSuccessListener {
            val user = firebaseAuth.currentUser
            if (user.isEmailVerified) {
                response.value = NetworkResult.Success(Status.VERIFIED)
            } else {
                response.value = NetworkResult.Success(Status.NOT_VERIFIED)
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


    enum class Status {
        NOT_VERIFIED, VERIFIED
    }
}