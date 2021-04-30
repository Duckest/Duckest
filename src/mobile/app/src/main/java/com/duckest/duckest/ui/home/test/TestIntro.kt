package com.duckest.duckest.ui.home.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.duckest.duckest.R
import com.duckest.duckest.databinding.FragmentIntroTestBinding
import com.duckest.duckest.util.UiUtils

class TestIntro : Fragment() {
    lateinit var binding: FragmentIntroTestBinding
    lateinit var args: TestIntroArgs
    private val levels = UiUtils.getLevels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        args = TestIntroArgs.fromBundle(requireArguments())
        (activity as AppCompatActivity).supportActionBar?.title = getString(args.testId)
        binding = FragmentIntroTestBinding.inflate(inflater, container, false)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(
                        TestIntroDirections.actionTestIntroToLevelFragment(
                            args.imageId,
                            args.testId
                        )
                    )
                }

            }
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.photo.setImageResource(args.imageId)
        binding.levelTitle.text = getString(args.levelId, R.string.intro_test_title)
        binding.startTest.setOnClickListener {
            findNavController().navigate(TestIntroDirections.actionTestIntroToTestFragment(args.testId, args.levelId))
      }
    }
}