package com.example.finalproject.ui.screens

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.R
import com.example.finalproject.viewmodel.DepartmentViewModel
import com.example.finalproject.model.Department
import com.example.finalproject.viewmodel.DepartmentState

@Composable
fun LearningScreen(bottomHeight: Dp) {
    val departmentViewModel: DepartmentViewModel = viewModel()
    val departmentState by departmentViewModel.departments.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(text = stringResource(R.string.departments))

        Spacer(modifier = Modifier.height(16.dp))

        when (departmentState) {
            is DepartmentState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is DepartmentState.Error -> {

                Text(
                    text = stringResource(R.string.loading_error),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            is DepartmentState.Success -> {
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
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = stringResource(R.string.name, department.nom))
            Text(text = stringResource(R.string.number, department.code))
        }
    }
}
