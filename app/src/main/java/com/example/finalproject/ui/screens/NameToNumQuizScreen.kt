package com.example.finalproject.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.finalproject.R
import com.example.finalproject.model.AnsweredQuestion
import com.example.finalproject.model.Score
import com.example.finalproject.viewmodel.QuizState
import com.example.finalproject.viewmodel.QuizViewModel

@SuppressLint("MutableCollectionMutableState")
@Composable
fun NameToNumQuizScreen(
    quizViewModel: QuizViewModel = viewModel(),
    bottomHeight: Dp
) {
    val quizState by quizViewModel.quizState.collectAsState()
    var userAnswer by remember { mutableStateOf("") }
    val score = remember { mutableStateOf(Score()) }
    var isQuizFinished by remember { mutableStateOf(false) }
    val answeredQuestions by remember { mutableStateOf(mutableListOf<AnsweredQuestion>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (quizState) {
            is QuizState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is QuizState.Error -> {

                Text(
                    text = stringResource(R.string.loading_error),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            is QuizState.Success -> {
                val currentQuestion = (quizState as QuizState.Success).questions.getOrNull(quizViewModel.currentIndex)
                val questions = (quizState as QuizState.Success).questions
                val totalQuestions = questions.size

                if (currentQuestion != null && !isQuizFinished) {
                    Text(
                        text = stringResource(
                            R.string.question,
                            quizViewModel.currentIndex + 1,
                            totalQuestions
                        ),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = stringResource(
                            R.string.what_is_the_department_number_of,
                            currentQuestion.name
                        ),
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    TextField(
                        value = userAnswer,
                        onValueChange = { userAnswer = it },
                        label = { Text(stringResource(R.string.enter_department_number)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Button(
                        onClick = {
                            val isCorrect = userAnswer == currentQuestion.number.toString()
                            score.value.increment(isCorrect)
                            answeredQuestions.add(AnsweredQuestion(
                                departmentCode = currentQuestion.number.toString(),
                                correctName = currentQuestion.name,
                                userAnswer = userAnswer,
                                isNameToNum = true,
                                isCorrect = isCorrect
                            ))
                            quizViewModel.nextQuestion()
                            if (quizViewModel.getNextQuestion() == null) {
                                isQuizFinished = true
                            } else {
                                userAnswer = ""
                            }

                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = stringResource(R.string.submit))
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.actual_score, score.value.currentScore),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                if (isQuizFinished) {
                    ScoreScreen(answeredQuestions, score.value, bottomHeight = bottomHeight)
                }
            }
        }
    }
}

