package com.example.mathapp2.data.domain

import androidx.lifecycle.LiveData
import com.example.mathapp2.data.DataRepository

class GetMathProblemsUseCase(private val dataRepository: DataRepository) {
    fun execute(): LiveData<List<String>> {
        return dataRepository.getMathProblems()
    }
}
