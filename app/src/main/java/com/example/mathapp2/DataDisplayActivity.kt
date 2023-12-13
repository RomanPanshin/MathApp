package com.example.mathapp2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mathapp2.data.db.AppDatabase
import com.example.mathapp2.viewmodel.MathProblemsViewModel
import com.example.mathapp2.viewmodel.MathProblemsViewModelFactory
import com.example.mathapp2.work.MathProblemsUpdateWorker

class DataDisplayActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MathProblemsViewModel = viewModel(
                factory = MathProblemsViewModelFactory(AppDatabase.getDatabase(this).mathProblemDao())
            )

            Scaffold(
                topBar = { TopAppBar(title = { Text("Display Activity") }) },
                bottomBar = { BottomAppBarContent() }
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MathProblemsList(viewModel = viewModel, onTriggerWorker = { triggerMathProblemsUpdate() })
                }
            }
        }
    }

    private fun triggerMathProblemsUpdate() {
        val workRequest = OneTimeWorkRequestBuilder<MathProblemsUpdateWorker>().build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }

    @Composable
    fun BottomAppBarContent() {
        BottomAppBar {
            Button(onClick = { navigateToActivity<MainActivity>() }) {
                Text(text = "Main Activity")
            }
            Button(onClick = { navigateToActivity<ImageDownloadActivity>() }) {
                Text(text = "Download Image")
            }
            Button(onClick = { /* Stay in the current activity */ }) {
                Text(text = "Display Data")
            }
        }
    }

    inline fun <reified T : ComponentActivity> navigateToActivity() {
        val intent = Intent(this, T::class.java)
        startActivity(intent)
    }
}

@Composable
fun MathProblemsList(viewModel: MathProblemsViewModel, onTriggerWorker: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = onTriggerWorker) {
            Text("Update Math Problems")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.refreshData() }) {
            androidx.compose.material3.Text(text = "Fetch Data")
        }
        Spacer(modifier = Modifier.height(16.dp))
        val problems = viewModel.problems.observeAsState(initial = emptyList())
        LazyColumn {
            items(problems.value) { problem ->
                MathProblemItem(problem)
            }
        }
    }
}

@Composable
fun MathProblemItem(problem: String) {
    Text(
        text = problem,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        style = MaterialTheme.typography.bodySmall
    )
}
