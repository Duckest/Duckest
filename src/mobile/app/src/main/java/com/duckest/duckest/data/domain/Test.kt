package com.duckest.duckest.data.domain

import com.google.gson.annotations.SerializedName

data class Test(
    val description: String?,
    @SerializedName("total_questions")
    val totalQuestions: Int?,
    val test: List<Question>
)

data class Question(
    val question: String?,
    val answers: List<String?>,
    @SerializedName("right_answer_index")
    val rightAnswer: Int?
)
