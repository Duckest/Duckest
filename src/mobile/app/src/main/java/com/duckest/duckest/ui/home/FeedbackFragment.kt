package com.duckest.duckest.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.duckest.duckest.databinding.FragmentFeedbackBinding

class FeedbackFragment : Fragment() {
    lateinit var binding: FragmentFeedbackBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedbackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sendFeedback.setOnClickListener {
            composeEmail(
                arrayOf("duckest@gmail.com"),
                binding.topicEdit.text.toString().trim(),
                binding.feedbackEdit.text.toString().trim()
            )
        }
    }

    private fun composeEmail(addresses: Array<String>, subject: String, message: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, message)
        }
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(
                requireContext(),
                "Нет почтового клиента",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}