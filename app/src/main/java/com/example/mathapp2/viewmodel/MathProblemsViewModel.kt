package com.example.mathapp2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathapp2.data.db.MathProblemDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.mathapp2.data.repository.MathRepository

class MathProblemsViewModel(private val mathProblemDao: MathProblemDao) : ViewModel() {
    private val _problems = MutableLiveData<List<String>>()
    val problems: LiveData<List<String>> get() = _problems
    private val repository =  MathRepository(mathProblemDao)

    fun refreshData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Update the list of math problems
                repository.refreshData()
                val mathProblems = mathProblemDao.getAll()
                mathProblems.observeForever { problemsList ->
                    val problemStrings = problemsList.map { it.problem }
                    _problems.postValue(problemStrings)
                }

            } catch (e: Exception) {
                // Handle exceptions
            }
        }
    }

    init {
        loadMathProblems()
    }

    private fun loadMathProblems() {
        viewModelScope.launch {
            val mathProblems = mathProblemDao.getAll()
            mathProblems.observeForever { problemsList ->
                val problemStrings = problemsList.map { it.problem }
                _problems.postValue(problemStrings)
            }
        }
    }

}
