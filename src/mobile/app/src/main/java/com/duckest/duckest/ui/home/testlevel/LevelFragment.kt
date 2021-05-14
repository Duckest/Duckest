package com.duckest.duckest.ui.home.testlevel

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.duckest.duckest.R
import com.duckest.duckest.databinding.FragmentLevelBinding
import com.duckest.duckest.ui.home.testlevel.adapter.LevelAdapter
import com.duckest.duckest.ui.home.testlevel.adapter.LevelItem
import kotlin.math.roundToInt

class LevelFragment : Fragment(), LevelAdapter.LevelItemListener {
    lateinit var binding: FragmentLevelBinding
    lateinit var args: LevelFragmentArgs
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLevelBinding.inflate(inflater, container, false)
        args = LevelFragmentArgs.fromBundle(requireArguments())

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(LevelFragmentDirections.actionLevelFragmentToFeedFragment())
                }
            }
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = args.testType
        Glide.with(binding.photo)
            .load(args.imgUrl)
            .placeholder(
                ResourcesCompat.getDrawable(
                    binding.root.resources,
                    R.mipmap.img_no_photo,
                    null
                )
            )
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.photo)
        val levels = args.testLevels.mapIndexed { indx, item ->
            val imgRes = when (indx) {
                0 -> R.mipmap.img_first
                1 -> R.mipmap.img_second
                2 -> R.mipmap.img_third
                else -> R.mipmap.img_no_photo
            }
            LevelItem(
                title = item.testLevel,
                imgRes,
                item.isTestCompleted,
                item.progress
            )
        }
        val adapter = LevelAdapter(
            levels,
            this
        )
        binding.items.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.drawer_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onClickedTest(testLevel: String, testPassed: Boolean, progress: Double) {
        if (args.testType.isNullOrEmpty()) {
            Toast.makeText(
                requireContext(),
                "Not implemented yet",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            if (testPassed) {
                findNavController().navigate(
                    LevelFragmentDirections.actionLevelFragmentToTestPassedFragment(progress.roundToInt(), testLevel, args.testType!!)
                )
            } else {
                findNavController().navigate(
                    LevelFragmentDirections.actionLevelFragmentToTestIntro(
                        args.imgUrl,
                        args.testType!!,
                        testLevel
                    )
                )
            }
        }
    }
}