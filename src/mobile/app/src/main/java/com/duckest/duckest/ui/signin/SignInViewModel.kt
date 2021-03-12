package com.duckest.duckest.ui.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.duckest.duckest.data.NetworkResult
import com.google.firebase.auth.FirebaseAuth

class SignInViewModel : ViewModel() {
    val firebaseResponse: MutableLiveData<NetworkResult<Status>>
        get() = _firebaseResponse
    private val _firebaseResponse = MutableLiveData<NetworkResult<Status>>()

    fun signIn(email: String, password: String) {
        firebaseResponse.value = NetworkResult.Loading()
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            email, password
        ).addOnSuccessListener {
            val user = FirebaseAuth.getInstance().currentUser
            if (user.isEmailVerified) {
                firebaseResponse.value = NetworkResult.Success(Status.VERIFIED)
            } else {
                firebaseResponse.value = NetworkResult.Success(Status.NOT_VERIFIED)
            }
        }.addOnFailureListener { e ->
            firebaseResponse.value = NetworkResult.Error(e.message, Status.ERROR)
        }
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val user = FirebaseAuth.getInstance().currentUser
//                    if (user.isEmailVerified) {
//                        Toast.makeText(
//                            requireContext(),
//                            "verified and can sign in",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    } else {
//                        Toast.makeText(
//                            requireContext(),
//                            "not verified",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//
//                }

    }


    enum class Status {
        NOT_VERIFIED, VERIFIED, ERROR
    }
}