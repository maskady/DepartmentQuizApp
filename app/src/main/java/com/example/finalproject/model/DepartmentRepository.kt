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
            val departments = api.getDepartments()

            // Transform corsica department with letter into code department only numerical
            val modifiedDepartments = departments.map { department ->
                when (department.code) {
                    "2A", "2B" -> department.copy(code = "2")
                    else -> department
                }
            }
            modifiedDepartments
        } catch (e: Exception) {
            throw e
        }
    }


    suspend fun fetchRandomDepartments(count: Int = 10): List<Department> {
        val departments = fetchDepartments()
        println(departments[0])
        return departments.shuffled().take(count)
    }
}
