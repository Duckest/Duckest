package com.duckest.duckest.ui.home.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.duckest.duckest.databinding.FragmentIntroTestBinding
import com.duckest.duckest.ui.UiUtils

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
        val header = "${getString(args.testId)}  ${getString(args.levelId)}"
        (activity as AppCompatActivity).supportActionBar?.title = header
        binding = FragmentIntroTestBinding.inflate(inflater, container, false)
        //     args = LevelFragmentArgs.fromBundle(requireArguments())

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(TestIntroDirections.actionTestIntroToLevelFragment(args.imageId, args.testId))
                }

            }
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.photo.setImageResource(args.imageId)
    }


}