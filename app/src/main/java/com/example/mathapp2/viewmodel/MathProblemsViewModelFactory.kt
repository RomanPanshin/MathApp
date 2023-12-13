package com.example.mathapp2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mathapp2.data.db.MathProblemDao

class MathProblemsViewModelFactory(private val mathProblemDao: MathProblemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MathProblemsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MathProblemsViewModel(mathProblemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
