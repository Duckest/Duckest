package com.duckest.duckest.ui.home.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.duckest.duckest.databinding.FragmentFeedBinding
import com.duckest.duckest.ui.UiUtils
import com.duckest.duckest.ui.home.feed.adapter.TestAdapter

class FeedFragment : Fragment() {

    lateinit var binding: FragmentFeedBinding
    private val test = UiUtils.getTests()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.title = "Тесты"
        binding = FragmentFeedBinding.inflate(inflater, container, false)

        val adapter = TestAdapter(
            test
        )
        binding.items.adapter = adapter
        return binding.root
    }
}