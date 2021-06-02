package com.duckest.duckest.ui.home.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duckest.duckest.data.DataStoreRepository
import com.duckest.duckest.data.NetworkResult
import com.duckest.duckest.data.domain.Question
import com.duckest.duckest.data.domain.Test
import com.duckest.duckest.data.domain.TestResult
import com.duckest.duckest.data.domain.TypeLevelPair
import com.duckest.duckest.data.network.RemoteDataSource
import com.duckest.duckest.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class TestViewModel @Inject constructor(
    repository: DataStoreRepository,
    private val remoteRepository: RemoteDataSource
) : ViewModel() {
    var email: String? = repository.getUserEmail()
    var typeLevel: TypeLevelPair? = null

    // The current _word
    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    val response: LiveData<NetworkResult<Test>>
        get() = _response
    private val _response = MutableLiveData<NetworkResult<Test>>()

    private val _currentQuestionNum = MutableLiveData<Int>()
    val currentQuestionNum: LiveData<Int>
        get() = _currentQuestionNum

    private val _eventTestFinish = MutableLiveData<NetworkResult<Pair<Boolean, Int>>>()
    val eventTestFinish: LiveData<NetworkResult<Pair<Boolean, Int>>>
        get() = _eventTestFinish

    init {
        _currentQuestionNum.value = 0
    }

    private lateinit var test: Test
    private var score: Int = 0

    fun getTest(typeLevelPair: TypeLevelPair) = viewModelScope.launch {
        _response.value = NetworkResult.Loading()
        try {
            val res = remoteRepository.getTest(typeLevelPair)
            test = res
            typeLevel = typeLevelPair
            _response.value = NetworkResult.Success(res)
        } catch (e: IOException) {
            _response.value =
                NetworkResult.Error(message = "Невозможно подключиться к сервису, проверьте свое подключение к интернету")
        } catch (e: Exception) {
            _response.value = NetworkResult.Error(message = e.message)
        }
    }

    fun nextQuestion() {
        if (test.totalQuestions!! <= currentQuestionNum.value!!) {
            onTestFinish()
        } else {
            _question.value = test.test[currentQuestionNum.value!!]
            _currentQuestionNum.value = (_currentQuestionNum.value)?.plus(1)
        }
    }

    fun checkAnswer(userAnswerId: Int) {
        if (userAnswerId == question.value!!.rightAnswer) {
            score++
        }
    }

    private fun onTestFinish() = viewModelScope.launch {
        _eventTestFinish.value = NetworkResult.Loading()
        val res = Utils.result(score, test.totalQuestions!!)
        try {
            val isPassed = remoteRepository.sendProgress(
                TestResult(
                    email ?: "",
                    typeLevel?.testType,
                    typeLevel?.testLevel,
                    res
                )
            )
            _eventTestFinish.value = NetworkResult.Success(Pair(isPassed, res.roundToInt()))
        } catch (e: IOException) {
            _eventTestFinish.value =
                NetworkResult.Error(message = "Невозможно подключиться к сервису, проверьте свое подключение к интернету")
        } catch (e: Exception) {
            _eventTestFinish.value = NetworkResult.Error(message = e.message)
        }
    }
}