package com.example.finalproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.Department
import com.example.finalproject.model.DepartmentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class DepartmentState {
    object Loading : DepartmentState()
    data class Success(val departments: List<Department>) : DepartmentState()
    data class Error(val message: String) : DepartmentState()
}

class DepartmentViewModel : ViewModel() {

    private val repository = DepartmentRepository()

    // Manage the state to show error and loader
    private val _departments = MutableStateFlow<DepartmentState>(DepartmentState.Loading)
    val departments: StateFlow<DepartmentState> = _departments

    init {
        fetchDepartments()
    }

    fun fetchDepartments() {
        viewModelScope.launch {
            _departments.value = DepartmentState.Loading
            try {
                val result = repository.fetchDepartments()
                _departments.value = DepartmentState.Success(result)
            } catch (e: Exception) {
                _departments.value = DepartmentState.Error("${e.message}")
            }
        }
    }
}

