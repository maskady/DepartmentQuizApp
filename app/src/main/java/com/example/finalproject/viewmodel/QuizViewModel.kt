package com.example.finalproject.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.DepartmentRepository
import com.example.finalproject.model.QuizQuestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class QuizState {
    object Loading : QuizState()
    data class Success(val questions: List<QuizQuestion>) : QuizState()
    data class Error(val message: String) : QuizState()
}

class QuizViewModel : ViewModel() {
    private val departmentRepository = DepartmentRepository()
    private val _questions = mutableStateListOf<QuizQuestion>()
    var currentIndex = 0
    private val _quizState = MutableStateFlow<QuizState>(QuizState.Loading)
    val quizState: StateFlow<QuizState> = _quizState

    init {
        viewModelScope.launch {
            try {
                val randomDepartments = departmentRepository.fetchRandomDepartments()

                randomDepartments.forEach { department ->
                    val quizQuestion = QuizQuestion(
                        name = department.nom,
                        number = department.code.toInt()
                    )
                    _questions.add(quizQuestion)
                }

                _quizState.value = QuizState.Success(_questions)
            } catch (e: Exception) {
                _quizState.value = QuizState.Error("${e.message}")
            }
        }
    }

    fun getNextQuestion(): QuizQuestion? {
        return if (currentIndex < _questions.size) {
            _questions[currentIndex]
        } else {
            println("Fin du quiz")
            null
        }
    }

    fun nextQuestion() {
        if (currentIndex < _questions.size) {
            currentIndex++
            println("Current index" + currentIndex + "size" + _questions.size)
        }
    }
}

