package com.example.finalproject.model

import com.example.finalproject.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DepartmentRepository {

    private val api: ApiService = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(ApiService::class.java)

    suspend fun fetchDepartments(): List<Department> {
        return try {
            // Récupérer la liste des départements depuis l'API
            val departments = api.getDepartments()

            // Modifier les départements 2A et 2B en leur attribuant le code 2
            val modifiedDepartments = departments.map { department ->
                when (department.code) {
                    "2A", "2B" -> department.copy(code = "2") // Modifier le code des départements 2A et 2B
                    else -> department
                }
            }

            // Retourner la liste modifiée
            modifiedDepartments
        } catch (e: Exception) {
            throw e
        }
    }


    suspend fun fetchRandomDepartments(count: Int = 10): List<Department> {
        val departments = fetchDepartments() // Récupérer tous les départements
        println(departments[0])
        return departments.shuffled().take(count) // Mélanger et prendre les premiers 10
    }
}
