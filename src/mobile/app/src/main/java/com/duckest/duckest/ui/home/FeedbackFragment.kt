package com.duckest.duckest.ui.home

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.duckest.duckest.R
import com.duckest.duckest.databinding.FragmentFeedbackBinding

class FeedbackFragment : Fragment() {
    lateinit var binding: FragmentFeedbackBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.title = "Обратная связь"
        binding = FragmentFeedbackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sendFeedback.setOnClickListener {
            composeEmail(
                arrayOf(getString(R.string.feedback_company_email)),
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
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                requireContext(),
                getString(R.string.feedback_error_title_no_mail_app),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}