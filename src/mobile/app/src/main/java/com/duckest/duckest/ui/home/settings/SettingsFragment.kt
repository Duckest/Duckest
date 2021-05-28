package com.duckest.duckest.ui.home.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.duckest.duckest.R
import com.duckest.duckest.data.Error
import com.duckest.duckest.data.NetworkResult
import com.duckest.duckest.databinding.FragmentSettingsBinding
import com.duckest.duckest.ui.home.HomeViewModel
import com.duckest.duckest.util.Utils
import com.duckest.duckest.util.Utils.checkName
import com.duckest.duckest.util.Utils.setTextChangeListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val vm: SettingsViewModel by viewModels()
    private val vmActivity: HomeViewModel by activityViewModels()
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
        binding.progressBar.visibility = View.VISIBLE
        binding.settingsScroll.visibility = View.GONE
        vm.getUserProfile()
        vm.user.observe(viewLifecycleOwner) {
            it?.let {
                binding.nameEdit.setText(it.name)
                binding.surnameEdit.setText(it.surname)
                binding.patronymicEdit.setText(it.patronymic)
            }
            binding.settingsScroll.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }

        vm.userResponse.observe(viewLifecycleOwner) {
            it?.let {
                when (it) {
                    is NetworkResult.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is NetworkResult.Success -> {
                        vmActivity.getUser()
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.settings_data_saved),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is NetworkResult.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        setTextChangeListener(binding.nameEdit, binding.name)
        setTextChangeListener(binding.surnameEdit, binding.surname)
        setTextChangeListener(binding.patronymicEdit, binding.patronymic)
        binding.saveProfileSettings.setOnClickListener {
            Utils.hideKeyboard(requireContext(), it)
            if (checkFields()) {
                return@setOnClickListener
            }
            vm.updateUserProfile(
                binding.nameEdit.text.toString().trim(),
                binding.surnameEdit.text.toString().trim(),
                binding.patronymicEdit.text.toString().trim()
            )
        }

        binding.savePassword.setOnClickListener {
            if (checkFieldsPasswords()) {
                return@setOnClickListener
            }
            vm.changePassword(
                binding.oldPasswordEdit.text.toString(),
                binding.passwordEdit.text.toString()
            )
        }

        vm.response.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
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
                    when (it.typeError) {
                        Error.WEAK_PASSWORD -> Utils.setError(
                            binding.password,
                            getString(R.string.sign_up_error_title_password)
                        )
                        Error.WRONG_PASSWORD -> Utils.setError(
                            binding.oldPassword,
                            getString(R.string.settings_wrong_password)
                        )
                        else -> Timber.v(it.message)
                    }
                }
            }
        }
    }

    private fun checkFieldsPasswords(): Boolean =
        Utils.isEmptyField(binding.passwordEdit, binding.password, requireContext()) or
                Utils.isEmptyField(
                    binding.confirmPasswordEdit,
                    binding.confirmPassword,
                    requireContext()
                ) or
                Utils.isEmptyField(
                    binding.oldPasswordEdit,
                    binding.oldPassword,
                    requireContext()
                ) or
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
        (Utils.isEmptyField(binding.nameEdit, binding.name, requireContext()) ||
                checkName(binding.nameEdit, binding.name, requireContext())) or
                (Utils.isEmptyField(binding.surnameEdit, binding.surname, requireContext()) ||
                        checkName(binding.surnameEdit, binding.surname, requireContext())) or
                (Utils.isEmptyField(binding.patronymicEdit, binding.patronymic, requireContext()) ||
                        checkName(binding.patronymicEdit, binding.patronymic, requireContext()))

}