package com.duckest.duckest.ui.home.feedback

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.duckest.duckest.R
import com.duckest.duckest.Utils.isEmptyField
import com.duckest.duckest.Utils.setError
import com.duckest.duckest.Utils.setTextChangeListener
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
        setTextChangeListener(binding.topicEdit, binding.topic)
        setTextChangeListener(binding.feedbackEdit, binding.feedback)

        binding.sendFeedback.setOnClickListener {
            if (isEmptyField(binding.topicEdit, binding.topic, requireContext()) or
                (isEmptyField(binding.feedbackEdit, binding.feedback, requireContext()) ||
                checkSizeFeedback())
            ) {
                return@setOnClickListener
            }
            composeEmail(
                arrayOf(getString(R.string.feedback_company_email)),
                binding.topicEdit.text.toString().trim(),
                binding.feedbackEdit.text.toString().trim()
            )
        }
    }

    private fun checkSizeFeedback(): Boolean {
        if (binding.feedbackEdit.text.toString().trim().length <= MIN_FEEDBACK_LENGTH) {
            setError(binding.feedback, getString(R.string.feedback_not_enough_symbols))
            return true
        }
        return false
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

    companion object {
        const val MIN_FEEDBACK_LENGTH = 30
    }
}