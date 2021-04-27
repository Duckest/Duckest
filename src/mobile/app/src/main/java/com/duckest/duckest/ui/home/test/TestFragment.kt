package com.duckest.duckest.ui.home.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.duckest.duckest.databinding.FragmentTestBinding

class TestFragment : Fragment() {
    private lateinit var binding: FragmentTestBinding
    private lateinit var args: TestFragmentArgs
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        args = TestFragmentArgs.fromBundle(requireArguments())
        binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        findNavController().navigate(TestFragmentDirections.actionTestFragmentToTestPassedFragment(20, 9, "Senior Sql", args.testId, args.levelId))
        super.onViewCreated(view, savedInstanceState)
    }
}