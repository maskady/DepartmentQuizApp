package com.example.finalproject.model

data class AnsweredQuestion(
    val departmentCode: String,
    val correctName: String,
    val userAnswer: String,
    val isNameToNum : Boolean,
    val isCorrect: Boolean
)