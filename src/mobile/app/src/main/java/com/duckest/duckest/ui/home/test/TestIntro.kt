package com.duckest.duckest.ui.home.test

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.duckest.duckest.R
import com.duckest.duckest.Utils
import com.duckest.duckest.databinding.FragmentIntroTestBinding
import com.duckest.duckest.ui.UiUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

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