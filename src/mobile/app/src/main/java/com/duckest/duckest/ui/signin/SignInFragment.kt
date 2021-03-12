package com.duckest.duckest.ui.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.duckest.duckest.data.NetworkResult
import com.duckest.duckest.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth


class SignInFragment : Fragment() {
    private lateinit var mView: View
    private lateinit var binding: FragmentSignInBinding
    private lateinit var mAuth: FirebaseAuth
    private val vm: SignInViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        mView = binding.root

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.firebaseResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.sendingGroup.visibility = View.GONE
                    when (response.data) {
                        SignInViewModel.Status.VERIFIED -> Toast.makeText(
                            requireContext(),
                            "verified",
                            Toast.LENGTH_SHORT
                        ).show()
                        SignInViewModel.Status.ERROR -> Toast.makeText(
                            requireContext(),
                            response.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        SignInViewModel.Status.NOT_VERIFIED -> Toast.makeText(
                            requireContext(),
                            "Вы не подтвердили почту",
                            Toast.LENGTH_SHORT
                        ).show()


                    }
                }

                is NetworkResult.Loading -> {
                    binding.sendingGroup.visibility = View.VISIBLE
                }
                is NetworkResult.Error -> {
                    binding.sendingGroup.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
        binding.signUp.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionLoginFragmentToSignUpFragment())
        }
        binding.signIn.setOnClickListener {
            vm.signIn(binding.email.text.toString().trim(), binding.password.text.toString().trim())
        }
        binding.forgotPassword.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionLoginFragmentToLostPasswordFragment())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()

    }

//    private fun signIn() {
//        mAuth.signInWithEmailAndPassword(
//            binding.email.text.toString().trim(), binding.password.text.toString().trim()
//        )
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
//
//            }
//
//    }

}