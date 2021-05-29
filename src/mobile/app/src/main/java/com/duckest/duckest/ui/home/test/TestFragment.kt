package com.duckest.duckest.ui.home.test

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.duckest.duckest.R
import com.duckest.duckest.data.NetworkResult
import com.duckest.duckest.data.domain.TypeLevelPair
import com.duckest.duckest.databinding.FragmentTestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestFragment : Fragment() {
    private lateinit var binding: FragmentTestBinding
    private lateinit var args: TestFragmentArgs
    private val vm: TestViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        args = TestFragmentArgs.fromBundle(requireArguments())
        binding = FragmentTestBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = args.testLevel
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.testViewModel = vm
        binding.lifecycleOwner = viewLifecycleOwner
        vm.getTest(
            TypeLevelPair(
                args.testType,
                args.testLevel
            )
        )

        vm.eventTestFinish.observe(viewLifecycleOwner) {
            if (it.first) {
                findNavController().navigate(
                    TestFragmentDirections.actionTestFragmentToTestPassedFragment(
                        it.second,
                        args.testType,
                        args.testLevel
                    )
                )
            } else {
                findNavController().navigate(
                    TestFragmentDirections.actionTestFragmentToTestFailFragment(
                        it.second,
                        args.testType,
                        args.testLevel,
                        args.imageUrl
                    )
                )
            }
        }

        vm.response.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Loading -> binding.progressBar.visibility = View.VISIBLE
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    vm.nextQuestion()
                }
                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        it.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        binding.nextQuestion.setOnClickListener {
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId
            // Do nothing if nothing is checked (id == -1)
            if (-1 != checkedId) {
                var answerIndex = 0
                when (checkedId) {
                    R.id.firstAnswerRadioButton -> answerIndex = 0
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                    R.id.thirdAnswerRadioButton -> answerIndex = 2
                }
                vm.checkAnswer(answerIndex)
                binding.questionRadioGroup.clearCheck()
                vm.nextQuestion()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.test_message_no_answer),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            findNavController().previousBackStackEntry != null
        ) {
            showDialog()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                showDialog()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onPause() {
        findNavController().navigate(TestFragmentDirections.actionGlobalFeedFragment())
        super.onPause()
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            setTitle(getString(R.string.exit))
            setMessage(getString(R.string.exit_message))
            setPositiveButton("Да") { _, _ ->
                findNavController().navigate(TestFragmentDirections.actionGlobalFeedFragment())
            }
            setNegativeButton("Нет") { _, _ ->
            }
            show()
        }
    }
}