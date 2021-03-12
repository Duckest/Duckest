package com.duckest.duckest.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.duckest.duckest.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment() {
    private lateinit var mView: View
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var mAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        mView = binding.root
        binding.signUp.setOnClickListener {
            registerUser()
            // findNavController().popBackStack()
        }
        return mView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
    }


    private fun registerUser() {
        mAuth.createUserWithEmailAndPassword(
            binding.email.text.toString().trim(),
            binding.password.text.toString()
        ).addOnSuccessListener {
            mAuth.currentUser?.apply {
                sendEmailVerification().addOnSuccessListener {
                    Toast.makeText(
                        requireContext(),
                        "Verification Email Has Been Sent",
                        Toast.LENGTH_SHORT
                    ).show()
                }.addOnFailureListener { e ->
                    Toast.makeText(
                        requireContext(),
                        "Email not sent " + e.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }.addOnFailureListener { e ->
            Toast.makeText(
                requireContext(),
                "Email not sent " + e.message,
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}