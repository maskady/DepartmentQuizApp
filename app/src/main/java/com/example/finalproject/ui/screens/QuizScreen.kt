package com.example.finalproject.ui.screens

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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.finalproject.R
import com.example.finalproject.viewmodel.QuizState
import com.example.finalproject.viewmodel.QuizViewModel

@Composable
fun QuizScreen(
    navController: NavController,
    quizViewModel: QuizViewModel = viewModel()
) {
    val quizState by quizViewModel.quizState.collectAsState()
    var userAnswer by remember { mutableStateOf("") }
    var score by remember { mutableIntStateOf(0) }
    var isQuizFinished by remember { mutableStateOf(false) }

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
                val errorMessage = stringResource(R.string.loading_error)
                Text(
                    text = stringResource(R.string.error, errorMessage),
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
                        text = "Question ${quizViewModel.currentIndex + 1}/$totalQuestions",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = "Quel est le numéro du département de ${currentQuestion.name} ?",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    TextField(
                        value = userAnswer,
                        onValueChange = { userAnswer = it },
                        label = { Text("Entrez le numéro du département") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Button(
                        onClick = {
                            if (userAnswer == currentQuestion.number.toString()) {
                                score++
                            }
                            quizViewModel.nextQuestion()
                            if (quizViewModel.getNextQuestion() == null) {
                                isQuizFinished = true
                            } else {
                                userAnswer = "" // Réinitialiser l'input
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Vérifier")
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Score actuel : $score",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                if (isQuizFinished) {
                    Text(
                        text = "Quiz terminé ! Votre score final est : $score",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(top = 32.dp)
                    )
                    Text(
                        text = "Score final : $score/$totalQuestions",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

