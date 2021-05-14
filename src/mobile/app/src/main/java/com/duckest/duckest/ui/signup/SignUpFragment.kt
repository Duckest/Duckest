package com.duckest.duckest.ui.signup

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.duckest.duckest.R
import com.duckest.duckest.data.Error
import com.duckest.duckest.data.NetworkResult
import com.duckest.duckest.data.domain.UserProfile
import com.duckest.duckest.databinding.FragmentSignUpBinding
import com.duckest.duckest.util.Utils
import com.duckest.duckest.util.Utils.checkName
import com.duckest.duckest.util.Utils.isEmptyField
import com.duckest.duckest.util.Utils.setError
import com.duckest.duckest.util.Utils.setTextChangeListener
import com.google.android.material.textfield.TextInputEditText
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
        setHasOptionsMenu(true)
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        mView = binding.root
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTextChangeListener(binding.emailEdit, binding.email)
        setTextChangeListener(binding.passwordEdit, binding.password)
        setTextChangeListener(binding.confirmPasswordEdit, binding.confirmPassword)
        setTextChangeListener(binding.nameEdit, binding.name)
        setTextChangeListener(binding.surnameEdit, binding.surname)
        setTextChangeListener(binding.patronymicEdit, binding.patronymic)

        binding.signUp.setOnClickListener {
            Utils.hideKeyboard(requireContext(), binding.emailEdit)
            if (checkFields()) {
                return@setOnClickListener
            }
            val user = UserProfile(
                binding.emailEdit.text.toString().trim(),
                binding.nameEdit.text.toString().trim(),
                binding.surnameEdit.text.toString().trim(),
                binding.patronymicEdit.text.toString().trim(),
            )
            vm.registerUser(
                user,
                binding.passwordEdit.text.toString().trim()
            )

        }

        vm.error.observe(viewLifecycleOwner, {
            binding.progressBar.visibility = View.GONE
            when (it) {
                Error.EMAIL_ALREADY_EXIST -> setError(
                    binding.email,
                    getString(R.string.sign_up_error_title_user_exist)
                )
                Error.WEAK_PASSWORD -> setError(
                    binding.password,
                    getString(R.string.sign_up_error_title_password)
                )
                else -> Toast.makeText(
                    requireContext(),
                    getString(R.string.sign_up_error_title_something_went_wrong),
                    Toast.LENGTH_SHORT
                ).show()
            }

        })

        vm.response.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    showDialog()
                }
                else -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        vm.responseFirebase.observe(viewLifecycleOwner, {
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
                    vm.registerUser(it.data)
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

    private fun checkFields(): Boolean =
        (isEmptyField(binding.nameEdit, binding.name, requireContext()) ||
                checkName(binding.nameEdit, binding.name, requireContext())) or
                isEmptyField(binding.passwordEdit, binding.password, requireContext()) or
                isEmptyField(
                    binding.confirmPasswordEdit,
                    binding.confirmPassword,
                    requireContext()
                ) or
                (isEmptyField(binding.emailEdit, binding.email, requireContext()) ||
                        Utils.checkEmailPattern(
                            binding.emailEdit,
                            binding.email,
                            requireContext()
                        )) or
                (isEmptyField(binding.surnameEdit, binding.surname, requireContext()) ||
                        checkName(binding.surnameEdit, binding.surname, requireContext())) or
                checkName(binding.patronymicEdit, binding.patronymic, requireContext()) ||
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
            setError(
                layoutPassConfirm,
                getString(R.string.sign_up_error_passwords_dont_match)
            )
            return false
        }
        return true
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setTitle(getString(R.string.dialog_title_confirm_email))
            setMessage(getString(R.string.dialog_description_confirm_email))
            setPositiveButton(getString(R.string.dialog_ok)) { _, _ ->
                findNavController().popBackStack()
            }
            show()
        }
    }
}