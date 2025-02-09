package com.example.finalproject.model

import retrofit2.http.GET

interface ApiService {
    @GET("departements")
    suspend fun getDepartments(): List<Department>
}
