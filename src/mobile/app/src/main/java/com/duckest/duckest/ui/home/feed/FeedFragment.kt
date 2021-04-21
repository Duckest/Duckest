package com.duckest.duckest.ui.home.feed

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.duckest.duckest.R
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
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.admin_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_test -> findNavController().navigate(FeedFragmentDirections.actionFeedFragmentToLevelFragment(0, 0))
        }
        return super.onOptionsItemSelected(item)
    }
}