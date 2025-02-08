package com.example.finalproject.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.viewmodel.DepartmentViewModel
import com.example.finalproject.model.Department
import com.example.finalproject.viewmodel.DepartmentState

@Composable
fun LearningScreen(navController: NavController, bottomHeight: Dp) {
    val departmentViewModel: DepartmentViewModel = viewModel()
    val departmentState by departmentViewModel.departments.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Departments")

        Spacer(modifier = Modifier.height(16.dp))

        when (departmentState) {
            is DepartmentState.Loading -> {
                // Affiche un indicateur de chargement pendant le chargement des données
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is DepartmentState.Error -> {
                // Affiche un message d'erreur en cas de problème de chargement
                val errorMessage = "Il y a eu une erreur lors du chargement des données."
                Text(
                    text = "Erreur : $errorMessage",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            is DepartmentState.Success -> {
                // Affiche la liste des départements lorsque les données sont chargées avec succès
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items((departmentState as DepartmentState.Success).departments) { department ->
                        DepartmentItem(department = department)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(bottomHeight))
    }
}

@Composable
fun DepartmentItem(department: Department) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nom: ${department.nom}")
            Text(text = "Numéro: ${department.code}")
        }
    }
}

