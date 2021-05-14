package com.duckest.duckest.ui.home.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.duckest.duckest.R
import com.duckest.duckest.databinding.FragmentTestFailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestFailFragment : Fragment() {
    private lateinit var binding: FragmentTestFailBinding
    private lateinit var args: TestFailFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestFailBinding.inflate(inflater, container, false)
        args = TestFailFragmentArgs.fromBundle(requireArguments())
        (activity as AppCompatActivity).supportActionBar?.title = args.testType
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.result.text =
            getString(R.string.test_result, args.result)
        binding.tryAgainButton.setOnClickListener {
            findNavController().navigate(
                TestFailFragmentDirections.actionTestFailFragmentToTestIntro(
                    args.imageUrl,
                    args.testType,
                    args.testLevel
                )
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigate(TestFailFragmentDirections.actionGlobalFeedFragment())
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        }

    }
}