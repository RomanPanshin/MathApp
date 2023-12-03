package com.example.mathapp2.data.repository

import androidx.lifecycle.LiveData
import com.example.mathapp2.data.api.MathApiService
import com.example.mathapp2.data.api.RetrofitService
import com.example.mathapp2.data.db.MathProblem
import com.example.mathapp2.data.db.MathProblemDao

class MathRepository(private val mathProblemDao: MathProblemDao) {
    private val retrofitService = RetrofitService.buildService(MathApiService::class.java)

    suspend fun refreshData() {
        val data = retrofitService.getMathData()
        mathProblemDao.clearAll()
        mathProblemDao.insertAll(*data.toTypedArray())
    }

    fun getMathProblems(): LiveData<List<MathProblem>> = mathProblemDao.getAll()
}
