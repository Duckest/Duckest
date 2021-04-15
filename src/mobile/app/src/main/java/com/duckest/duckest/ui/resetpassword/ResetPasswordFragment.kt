package com.duckest.duckest.ui.resetpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.duckest.duckest.Utils
import com.duckest.duckest.data.NetworkResult
import com.duckest.duckest.databinding.FragmentResetPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordFragment : Fragment() {
    private lateinit var mView: View
    private lateinit var binding: FragmentResetPasswordBinding
    private val vm: ResetPasswordViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.hide()
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        mView = binding.root
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.header.close.setOnClickListener {
            findNavController().navigate(ResetPasswordFragmentDirections.actionLostPasswordFragmentToLoginFragment())
        }
        binding.resetPassword.setOnClickListener {
            if (Utils.isEmptyField(binding.emailEdit, binding.email) || Utils.checkEmailPattern(
                    binding.emailEdit,
                    binding.email
                )
            ) {
                return@setOnClickListener
            }
            vm.resetPassword(binding.emailEdit.text.toString().trim())
        }

        binding.rememberPassword.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.emailEdit.addTextChangedListener {
            if (!it.isNullOrEmpty()) {
                binding.email.isErrorEnabled = false
            }
        }
        vm.response.observe(viewLifecycleOwner, {
            when (it) {
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    findNavController().navigate(ResetPasswordFragmentDirections.actionLostPasswordFragmentToCheckEmailFragment())
                }
                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onStop() {
        super.onStop()
    }

}