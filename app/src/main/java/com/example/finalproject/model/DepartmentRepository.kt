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
            api.getDepartments()
        } catch (e: Exception) {
            throw e
        }
    }
}
