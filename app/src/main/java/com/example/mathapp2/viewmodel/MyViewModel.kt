package com.example.mathapp2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mathapp2.data.repository.MathRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel(private val repository: MathRepository) : ViewModel() {
    val mathProblems = repository.getMathProblems()

    fun refreshData() {
        viewModelScope.launch(Dispatchers.IO) { // Use the IO dispatcher for database operations
            try {
                repository.refreshData()
            } catch (e: Exception) {
                Log.e("MyViewModel", "Error refreshing data", e)
                // Handle exceptions
            }
        }
    }
}
