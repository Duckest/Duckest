package com.duckest.duckest.ui.home.feed

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.duckest.duckest.R
import com.duckest.duckest.data.NetworkResult
import com.duckest.duckest.data.domain.TestLevelProgress
import com.duckest.duckest.data.domain.TestLevelProgresses
import com.duckest.duckest.databinding.FragmentFeedBinding
import com.duckest.duckest.ui.home.feed.adapter.TestAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : Fragment(), TestAdapter.TestItemListener {

    lateinit var binding: FragmentFeedBinding
    val vm: FeedViewModel by viewModels()
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
        vm.getProgress()
        val adapter = TestAdapter(
            listOf(),
            this
        )
        binding.items.adapter = adapter
        vm.response.observe(viewLifecycleOwner) { res ->
            when (res) {
                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        res.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    res.data?.let {
                        adapter.items = it
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.admin_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.add_test -> findNavController().navigate(
//                FeedFragmentDirections.actionFeedFragmentToLevelFragment(
//                    0,
//                    0
//                )
//            )
//        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClickedTest(
        testLevels: List<TestLevelProgress>?,
        testType: String?,
        imgUrl: String?
    ) {
        val testLevelsProgresses = TestLevelProgresses()
        testLevelsProgresses.addAll(testLevels!!.toTypedArray())
        findNavController().navigate(
            FeedFragmentDirections.actionFeedFragmentToLevelFragment(
                imgUrl,
                testType,
                testLevelsProgresses
            )
        )
    }
}