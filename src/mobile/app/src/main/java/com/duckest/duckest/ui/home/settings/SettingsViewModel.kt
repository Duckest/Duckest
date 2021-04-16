package com.duckest.duckest.ui.home.settings

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duckest.duckest.data.DataStoreRepository
import com.duckest.duckest.data.Error
import com.duckest.duckest.data.NetworkResult
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val repository: DataStoreRepository

) : ViewModel() {
    val response: LiveData<NetworkResult<Status>>
        get() = _response
    private val _response = MutableLiveData<NetworkResult<Status>>()

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