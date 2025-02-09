package com.example.finalproject.model

data class Score(
    var currentScore: Int = 0,
    var totalQuestions: Int = 0
) {
    fun increment(correct: Boolean) {
        if (correct) {
            currentScore++
        }
        totalQuestions++
        println(currentScore.toString() + "/" + totalQuestions.toString())
    }

    fun reset() {
        currentScore = 0
        totalQuestions = 0
    }
}
