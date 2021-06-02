package com.duckest.duckest.ui.home.feedback

import androidx.lifecycle.ViewModel
import com.duckest.duckest.data.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor(
    repository: DataStoreRepository
) : ViewModel() {
    var email: String? = repository.getUserEmail()
}