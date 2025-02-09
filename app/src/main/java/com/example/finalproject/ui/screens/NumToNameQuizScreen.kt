package com.example.finalproject.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.R
import com.example.finalproject.model.AnsweredQuestion
import com.example.finalproject.model.Department
import com.example.finalproject.model.Score
import com.example.finalproject.viewmodel.DepartmentState
import com.example.finalproject.viewmodel.DepartmentViewModel

@SuppressLint("MutableCollectionMutableState")
@Composable
fun NumToNameQuizScreen(
    departmentViewModel: DepartmentViewModel = viewModel(),
    bottomHeight: Dp
) {
    val departmentState by departmentViewModel.departments.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    val score = remember { mutableStateOf(Score()) }
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var isQuizFinished by remember { mutableStateOf(false) }
    var selectedDepartment by remember { mutableStateOf("") }
    var questions by remember { mutableStateOf(listOf<Department>()) }
    var showError by remember { mutableStateOf(false) }
    val answeredQuestions by remember { mutableStateOf(mutableListOf<AnsweredQuestion>()) }

    LaunchedEffect(departmentState) {
        if (departmentState is DepartmentState.Success) {
            questions = (departmentState as DepartmentState.Success).departments
                .shuffled()
                .take(10)
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        when (departmentState) {
            is DepartmentState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is DepartmentState.Error -> {
                Text(
                    text = stringResource(R.string.loading_error),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            is DepartmentState.Success -> {
                if (!isQuizFinished && questions.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.question_10, currentQuestionIndex + 1),
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = stringResource(R.string.score, score.value.currentScore),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    Text(
                        text = stringResource(
                            R.string.what_is_department_name_associated_to,
                            questions[currentQuestionIndex].code
                        ),
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    TextField(
                        value = searchQuery,
                        onValueChange = {
                            searchQuery = it
                            showError = false
                        },
                        label = { Text(stringResource(R.string.enter_department_name)) },
                        modifier = Modifier.fillMaxWidth(),
                        isError = showError
                    )

                    if (showError) {
                        Text(
                            text = stringResource(R.string.please_select_a_department_in_the_list),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    val filteredDepartments = (departmentState as DepartmentState.Success).departments
                        .filter { it.nom.contains(searchQuery, ignoreCase = true) }
                        .take(5)

                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 8.dp)
                    ) {
                        items(filteredDepartments) { department ->
                            Text(
                                text = department.nom,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        searchQuery = department.nom
                                        selectedDepartment = department.nom
                                        showError = false
                                    }
                                    .padding(vertical = 8.dp)
                            )
                        }
                    }

                    Button(
                        onClick = {
                            if (selectedDepartment.isNotEmpty()) {
                                val currentQuestion = questions[currentQuestionIndex]
                                val isCorrect = selectedDepartment == currentQuestion.nom
                                score.value.increment(isCorrect)

                                answeredQuestions.add(AnsweredQuestion(
                                    departmentCode = currentQuestion.code,
                                    correctName = currentQuestion.nom,
                                    userAnswer = selectedDepartment,
                                    isNameToNum = false,
                                    isCorrect = isCorrect
                                ))

                                if (currentQuestionIndex < 9) {
                                    currentQuestionIndex++
                                    searchQuery = ""
                                    selectedDepartment = ""
                                } else {
                                    isQuizFinished = true
                                }
                            } else {
                                showError = true
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Text(stringResource(R.string.submit))
                    }

                    Spacer(Modifier.padding(bottomHeight))
                }

                if (isQuizFinished) {
                    ScoreScreen(answeredQuestions, score.value, bottomHeight)
                }
            }
        }
    }
}