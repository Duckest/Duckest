package com.duckest.duckest.ui.resetpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.duckest.duckest.databinding.FragmentResetPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordFragment : Fragment() {
    private lateinit var mView: View
    private lateinit var binding: FragmentResetPasswordBinding
    private lateinit var mAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        mView = binding.root
        binding.resetPassword.setOnClickListener {
            resetPassword()
        }
        return mView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
    }

    private fun resetPassword() {
        mAuth = FirebaseAuth.getInstance()
        mAuth.sendPasswordResetEmail(binding.email.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Reset password Has Been Sent", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        task.exception?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}