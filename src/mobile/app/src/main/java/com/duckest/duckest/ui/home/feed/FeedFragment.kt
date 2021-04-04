package com.duckest.duckest.ui.home.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.duckest.duckest.databinding.FragmentFeedBinding
import com.duckest.duckest.ui.UiUtils
import com.duckest.duckest.ui.home.feed.adapter.TestAdapter
import com.duckest.duckest.ui.home.testlevel.LevelFragmentDirections

class FeedFragment : Fragment(), TestAdapter.TestItemListener {

    lateinit var binding: FragmentFeedBinding
    private val test = UiUtils.getTests()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.title = "Тесты"

        binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = TestAdapter(
            test,
            this
        )
        binding.items.adapter = adapter
    }

    override fun onClickedTest(imageId: Int, testId: Int) {
        findNavController().navigate(FeedFragmentDirections.actionFeedFragmentToLevelFragment(imageId, testId))
    }
}