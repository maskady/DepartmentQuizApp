package com.example.finalproject.model

import com.example.finalproject.utils.Constants
import retrofit2.http.GET

interface ApiService {
    @GET("departements")
    suspend fun getDepartments(): List<Department>
}
