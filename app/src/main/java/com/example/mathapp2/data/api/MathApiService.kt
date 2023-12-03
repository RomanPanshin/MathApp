package com.example.mathapp2.data.api

import com.example.mathapp2.data.db.MathProblem
import retrofit2.http.GET

interface MathApiService {
    @GET("math_problems")
    suspend fun getMathData(): List<MathProblem>
}