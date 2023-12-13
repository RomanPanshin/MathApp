package com.example.mathapp2.work

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.mathapp2.data.db.AppDatabase
import com.example.mathapp2.data.db.MathProblem
import kotlin.random.Random


class MathProblemsUpdateWorker(appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        try {
            val dao = AppDatabase.getDatabase(applicationContext).mathProblemDao()
            val newProblem = generateRandomMathProblem()
            dao.insertAll(newProblem)
            return Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure()
        }
    }

    private fun generateRandomMathProblem(): MathProblem {
        // Generate a simple math problem (example: addition)
        val number1 = Random.nextInt(100)
        val number2 = Random.nextInt(100)
        val problem = "$number1 + $number2"
        val answer = number1 + number2  // This can be stored or calculated later

        return MathProblem(id =Random.nextInt(100),  problem = problem + " = " + answer) // Assuming auto-generate ID
    }
}
