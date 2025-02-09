package com.example.finalproject.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.viewmodel.DepartmentState
import com.example.finalproject.viewmodel.DepartmentViewModel

@Composable
fun InverseQuizScreen(
    navController: NavController,
    departmentViewModel: DepartmentViewModel = viewModel()
) {
    val departmentState by departmentViewModel.departments.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    // Récupérer la liste complète des départements
    val departmentList = when (departmentState) {
        is DepartmentState.Success -> (departmentState as DepartmentState.Success).departments // Liste des départements obtenue
        else -> emptyList() // Liste vide si les données ne sont pas encore chargées
    }

    // Filtrer les départements en fonction de la saisie
    val filteredDepartments = departmentList.filter { department ->
        department.nom.contains(searchQuery, ignoreCase = true)
    }.take(10)

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Champ de saisie pour la recherche
        TextField(
            value = searchQuery,
            onValueChange = { newQuery -> searchQuery = newQuery },
            label = { Text("Recherche du département") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Affichage des départements filtrés
        if (filteredDepartments.isNotEmpty()) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(filteredDepartments) { department ->
                    Text(
                        text = department.nom,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                searchQuery = department.nom
                            }
                    )
                }
            }
        } else {
            Text(
                text = "Aucun département trouvé",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}
