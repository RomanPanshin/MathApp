package com.example.mathapp2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mathapp2.data.db.AppDatabase
import com.example.mathapp2.viewmodel.MathProblemsViewModel
import com.example.mathapp2.viewmodel.MathProblemsViewModelFactory

class DataDisplayActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MathProblemsViewModel = viewModel(
                factory = MathProblemsViewModelFactory(AppDatabase.getDatabase(this).mathProblemDao())
            )
            Scaffold(
                topBar = {
                    TopAppBar(title = { Text("Display Activity") }) // Static title
                },
                bottomBar = {
                    BottomAppBar {
                        Button(onClick = { navigateToActivity<MainActivity>() }) {
                            Text(text = "Main Activity")
                        }
//                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { navigateToActivity<ImageDownloadActivity>() }) {
                            Text(text = "Download Image")
                        }
//                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { navigateToActivity<DataDisplayActivity>() }) {
                            Text(text = "Display Data")
                        }
                    }
                }
                // drawerContent parameter removed as it's not supported here
            ) {
                MaterialTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        MathProblemsList(viewModel)
                    }
                }
            }
        }
    }
    inline fun <reified T : ComponentActivity> navigateToActivity() {
        val intent = Intent(this, T::class.java)
        startActivity(intent)
    }
}

@Composable
fun MathProblemsList(viewModel: MathProblemsViewModel) {
    val problems = viewModel.problems.observeAsState(initial = emptyList())
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(problems.value) { problem ->
            MathProblemItem(problem)
        }
    }
}

@Composable
fun MathProblemItem(problem: String) {
    Text(text = problem, modifier = Modifier.fillMaxWidth().padding(8.dp), style = MaterialTheme.typography.bodySmall)
}