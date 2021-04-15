package com.duckest.duckest.ui.resetpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.duckest.duckest.databinding.FragmentCheckEmailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckEmailFragment : Fragment() {
    private lateinit var binding: FragmentCheckEmailBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.header.close.setOnClickListener {
            findNavController().navigate(CheckEmailFragmentDirections.actionCheckEmailFragmentToLoginFragment())
        }
        binding.tryAnotherEmailLabel.setOnClickListener {
            findNavController().navigate(CheckEmailFragmentDirections.actionCheckEmailFragmentToLostPasswordFragment())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


}