package com.duckest.duckest.ui.home.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.duckest.duckest.R
import com.duckest.duckest.databinding.FragmentIntroTestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestIntro : Fragment() {
    lateinit var binding: FragmentIntroTestBinding
    lateinit var args: TestIntroArgs
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        args = TestIntroArgs.fromBundle(requireArguments())
        (activity as AppCompatActivity).supportActionBar?.title = args.testType
        binding = FragmentIntroTestBinding.inflate(inflater, container, false)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
//                    findNavController().navigate(
//                        TestIntroDirections.actionTestIntroToLevelFragment(
//                            args.imageUrl,
//                            args.testType,
//                            args.testLevel
//                        )
//                    )
                }

            }
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(binding.photo)
            .load(args.imageUrl)
            .placeholder(
                ResourcesCompat.getDrawable(
                    binding.root.resources,
                    R.mipmap.img_no_photo,
                    null
                )
            ).transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.photo)
        binding.levelTitle.text = args.testLevel
        binding.startTest.setOnClickListener {
            findNavController().navigate(
                TestIntroDirections.actionTestIntroToTestFragment(
                    args.testType,
                    args.testLevel,
                    args.imageUrl
                )
            )
        }
    }
}