package com.duckest.duckest.ui.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.duckest.duckest.databinding.FragmentIntroBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroFragment : Fragment() {
    lateinit var binding: FragmentIntroBinding
    private val vm: IntroViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.hide()
        binding = FragmentIntroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.getUser()
        vm.needToNavigate.observe(viewLifecycleOwner) {
            when (it) {
                IntroViewModel.Endpoint.LOGIN -> findNavController().navigate(
                    IntroFragmentDirections.actionIntroFragmentToLoginFragment()
                )
                IntroViewModel.Endpoint.FEED -> {
                    findNavController().navigate(IntroFragmentDirections.actionIntroFragmentToHomeActivity())
                    requireActivity().finish()
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.show()
    }
}