package com.duckest.duckest.ui.signup

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.duckest.duckest.R
import com.duckest.duckest.Utils
import com.duckest.duckest.Utils.setError
import com.duckest.duckest.data.Error
import com.duckest.duckest.data.NetworkResult
import com.duckest.duckest.databinding.FragmentSignUpBinding
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private lateinit var mView: View
    private lateinit var binding: FragmentSignUpBinding
    private val vm: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.sign_up_title)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        mView = binding.root
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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


        binding.confirmPasswordEdit.addTextChangedListener {
            if (!it.isNullOrEmpty()) {
                binding.confirmPassword.isErrorEnabled = false
            }
        }
        binding.signUp.setOnClickListener {
            Utils.hideKeyboard(requireActivity())
            if (checkFields()) {
                return@setOnClickListener
            }
            vm.registerUser(
                binding.emailEdit.text.toString().trim(),
                binding.passwordEdit.text.toString().trim()
            )

        }


        vm.error.observe(viewLifecycleOwner, {
            binding.progressBar.visibility = View.GONE
            when (it) {
                Error.EMAIL_ALREADY_EXIST -> setError(binding.email, getString(R.string.sign_up_error_title_user_exist))
                Error.WEAK_PASSWORD -> setError(binding.password, getString(R.string.sign_up_error_title_password))
                else -> Toast.makeText(
                    requireContext(),
                    getString(R.string.sign_up_error_title_something_went_wrong),
                    Toast.LENGTH_SHORT
                ).show()
            }

        })


        vm.response.observe(viewLifecycleOwner, {
            when (it) {
                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    findNavController().popBackStack()
                }

                is NetworkResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE

                }
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun checkFields(): Boolean {
        var flag = false
        val pass = binding.passwordEdit.text.toString().trim()
        val confirmPass = binding.confirmPasswordEdit.text.toString().trim()
        val email = binding.emailEdit.text.toString().trim()
        if (email.isEmpty()) {
            setError(binding.email, getString(R.string.sign_up_error_title_empty_email))
            flag = true
        } else if (!Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches()
        ) {
            setError(binding.email, getString(R.string.sign_up_error_title_wrong_email))
            flag = true
        }
        if (pass.isEmpty()) {
            setError(binding.password, getString(R.string.sign_up_error_title_empty_password))
            flag = true
        }
        if (confirmPass.isEmpty()) {
            setError(binding.confirmPassword, getString(R.string.sign_up_error_title_empty_confitm_password))
            flag = true
        }
        if (pass != confirmPass) {
            setError(binding.confirmPassword, getString(R.string.sign_up_error_passwords_dont_match))
            flag = true
        }
        return flag
    }



}