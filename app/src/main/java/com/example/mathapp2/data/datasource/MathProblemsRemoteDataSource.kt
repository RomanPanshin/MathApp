package com.example.mathapp2.data.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MathProblemsRemoteDataSource {
    fun getMathProblems(): LiveData<List<String>> {
        val data = MutableLiveData<List<String>>()
        // Simulating a network call
        data.value = listOf("Problem 1", "Problem 2", "Concept A", "Concept B")
        return data
    }
}
