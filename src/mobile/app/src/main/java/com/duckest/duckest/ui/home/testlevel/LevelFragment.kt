package com.duckest.duckest.ui.home.testlevel

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.duckest.duckest.R
import com.duckest.duckest.databinding.FragmentLevelBinding
import com.duckest.duckest.ui.UiUtils
import com.duckest.duckest.ui.home.testlevel.adapter.LevelAdapter

class LevelFragment : Fragment(), LevelAdapter.LevelItemListener {
    lateinit var binding: FragmentLevelBinding
    lateinit var args: LevelFragmentArgs
    private val levels = UiUtils.getLevels()
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
        (activity as AppCompatActivity).supportActionBar?.setTitle(args.testId)
        binding.photo.setImageResource(args.imageId)
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

    override fun onClickedTest(levelId: Int) {
        findNavController().navigate(
            LevelFragmentDirections.actionLevelFragmentToTestIntro(
                args.imageId,
                args.testId,
                levelId
            )
        )
    }
}