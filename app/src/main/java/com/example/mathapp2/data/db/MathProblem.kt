package com.example.mathapp2.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MathProblem(
    @PrimaryKey val id: Int,
    val problem: String // Assuming 'problem' is a string representation of the math problem
)