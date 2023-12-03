package com.example.mathapp2.data

import androidx.lifecycle.LiveData
import com.example.mathapp2.data.datasource.MathProblemsRemoteDataSource

class DataRepository(private val mathProblemsRemoteDataSource: MathProblemsRemoteDataSource) {
    fun getMathProblems(): LiveData<List<String>> {
        return mathProblemsRemoteDataSource.getMathProblems()
    }
}
