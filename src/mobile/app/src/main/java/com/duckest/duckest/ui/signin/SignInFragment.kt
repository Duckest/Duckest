package com.duckest.duckest.ui.signin

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.duckest.duckest.Utils.setError
import com.duckest.duckest.data.Error
import com.duckest.duckest.data.NetworkResult
import com.duckest.duckest.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var mAuth: FirebaseAuth
    private val vm: SignInViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.title = "Авторизация"
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.response.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    when (response.data) {
                        SignInViewModel.Status.VERIFIED -> {
                            findNavController().navigate(SignInFragmentDirections.actionLoginFragmentToHomeActivity())
                        }

                        SignInViewModel.Status.NOT_VERIFIED -> Toast.makeText(
                            requireContext(),
                            "Вы не подтвердили почту",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }

                is NetworkResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }

        vm.error.observe(viewLifecycleOwner) { e ->
            binding.progressBar.visibility = View.GONE
            when (e) {
                Error.WRONG_PASSWORD -> {
                    setError(binding.password, "Вы ввели неверный пароль")
                }
                Error.WRONG_EMAIL ->
                    setError(binding.email, "Пользователь не найден")
                else -> {
                    Toast.makeText(
                        requireContext(),
                        "Что-то пошло не так, поробуйте позже",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        binding.emailEdit.addTextChangedListener {
            if (!it.isNullOrEmpty()) {
                binding.email.isErrorEnabled = false
            }
        }

        binding.passwordEdit.addTextChangedListener {
            if (!it.isNullOrEmpty()) {
                binding.password.isErrorEnabled = false
            }
        }
        binding.signUp.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionLoginFragmentToSignUpFragment())
        }
        binding.signIn.setOnClickListener {
            if (checkFields()) {
                return@setOnClickListener
            }
            vm.signIn(
                binding.emailEdit.text.toString().trim(),
                binding.passwordEdit.text.toString().trim()
            )
        }
        binding.forgotPassword.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionLoginFragmentToLostPasswordFragment())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()

    }

    private fun checkFields(): Boolean {
        var flag = false
        val email = binding.emailEdit.text.toString().trim()
        val pass = binding.passwordEdit.text.toString().trim()
        if (email.isEmpty()) {
            setError(binding.email, "Введите e-mail")
            flag = true
        } else if (!Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches()
        ) {
            setError(binding.email, "Введите правильный e-mail")
            flag = true
        }
        if (pass.isEmpty()) {
            setError(binding.password, "Введите пароль")
            flag = true
        }

        return flag
    }

}