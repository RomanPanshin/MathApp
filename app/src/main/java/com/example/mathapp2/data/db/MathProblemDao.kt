package com.example.mathapp2.data.db
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MathProblemDao {
    @Query("SELECT * FROM MathProblem")
    fun getAll(): LiveData<List<MathProblem>>

    @Query("DELETE FROM MathProblem")
    fun clearAll()

    @Insert
    fun insertAll(vararg problems: MathProblem)
}