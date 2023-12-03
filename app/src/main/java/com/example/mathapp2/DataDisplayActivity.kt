package com.example.mathapp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mathapp2.data.db.AppDatabase
import com.example.mathapp2.data.db.MathProblemDao
import com.example.mathapp2.ui.theme.MathApp2Theme

class DataDisplayActivity : AppCompatActivity() {
    private lateinit var database: AppDatabase
    private lateinit var mathProblemDao: MathProblemDao
    private lateinit var adapter: MathProblemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_display)

        database = AppDatabase.getDatabase(this)
        mathProblemDao = database.mathProblemDao()
        adapter = MathProblemsAdapter()

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        loadData()
    }

    private fun loadData() {
        // Observe LiveData from the database
        mathProblemDao.getAll().observe(this, Observer { problems ->
            // Assuming MathProblem has a 'problem' property which is a String
            val problemStrings = problems.map { it.problem }
            adapter.setProblems(problemStrings)
        })
    }
}
