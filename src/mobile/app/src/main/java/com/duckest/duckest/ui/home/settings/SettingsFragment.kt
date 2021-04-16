package com.duckest.duckest.ui.home.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.duckest.duckest.R
import com.duckest.duckest.Utils
import com.duckest.duckest.Utils.checkName
import com.duckest.duckest.Utils.setTextChangeListener
import com.duckest.duckest.data.Error
import com.duckest.duckest.data.NetworkResult
import com.duckest.duckest.databinding.FragmentSettingsBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val vm: SettingsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTextChangeListener(binding.nameEdit, binding.name)
        setTextChangeListener(binding.surnameEdit, binding.surname)
        setTextChangeListener(binding.patronymicEdit, binding.patronymic)
        binding.saveProfileSettings.setOnClickListener {
            if (checkFields()) {
                return@setOnClickListener
            }
            // TODO: 15.04.2021 когда Глеб напишет бэк можно и дописать
        }
        binding.savePassword.setOnClickListener {
            if (checkFieldsPasswords()) {
                return@setOnClickListener
            }
            vm.changePassword(binding.oldPasswordEdit.text.toString(), binding.passwordEdit.text.toString())
        }

        vm.response.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkResult.Loading -> binding.progressBar.visibility = View.VISIBLE
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.settings_data_saved),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    when(it.typeError) {
                        Error.WEAK_PASSWORD -> Utils.setError(
                            binding.password,
                            getString(R.string.sign_up_error_title_password)
                        )
                        Error.WRONG_PASSWORD ->   Utils.setError(
                            binding.oldPassword,
                            getString(R.string.settings_wrong_password)
                        )
                        else -> Log.v("SettingsError", it.message?:"")
                    }
                }
            }
        }
    }

    private fun checkFieldsPasswords(): Boolean =
        Utils.isEmptyField(binding.passwordEdit, binding.password) or
                Utils.isEmptyField(binding.confirmPasswordEdit, binding.confirmPassword) or
                Utils.isEmptyField(binding.oldPasswordEdit, binding.oldPassword) or
                !arePasswordsSame(
                    binding.passwordEdit,
                    binding.confirmPasswordEdit,
                    binding.confirmPassword
                )

    private fun arePasswordsSame(
        pass: TextInputEditText,
        passConfirm: TextInputEditText, layoutPassConfirm: TextInputLayout
    ): Boolean {
        if (pass.text.toString().trim() != passConfirm.text.toString().trim()) {
            Utils.setError(
                layoutPassConfirm,
                getString(R.string.sign_up_error_passwords_dont_match)
            )
            return false
        }
        return true
    }

    private fun checkFields(): Boolean =
        (Utils.isEmptyField(binding.nameEdit, binding.name) ||
                checkName(binding.nameEdit, binding.name)) or
                (Utils.isEmptyField(binding.surnameEdit, binding.surname) ||
                        checkName(binding.surnameEdit, binding.surname)) or
                (Utils.isEmptyField(binding.patronymicEdit, binding.patronymic) ||
                        checkName(binding.patronymicEdit, binding.patronymic))


}