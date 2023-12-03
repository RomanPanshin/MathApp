package com.example.mathapp2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mathapp2.data.db.AppDatabase
import com.example.mathapp2.data.repository.MathRepository
import com.example.mathapp2.ui.theme.AlgebraFragment
import com.example.mathapp2.ui.theme.GeometryFragment
import com.example.mathapp2.ui.theme.MathApp2Theme
import com.example.mathapp2.ui.theme.MathBasicsFragment
import com.example.mathapp2.viewmodel.MyViewModel
import com.example.mathapp2.viewmodel.MyViewModelFactory


class MainActivity : AppCompatActivity() {
    private lateinit var problemsRecyclerView: RecyclerView
    private lateinit var adapter: MathProblemsAdapter
    private lateinit var navController: NavController
    private val viewModel: MyViewModel by viewModels {
        val dao = AppDatabase.getDatabase(application).mathProblemDao()
        val repository = MathRepository(dao)
        MyViewModelFactory(repository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController

        findViewById<Button>(R.id.mathBasicsButton).setOnClickListener {
            navController.navigate(R.id.mathBasicsFragment)
        }

        findViewById<Button>(R.id.algebraButton).setOnClickListener {
            navController.navigate(R.id.algebraFragment)
        }

        findViewById<Button>(R.id.geometryButton).setOnClickListener {
            navController.navigate(R.id.geometryFragment)
        }
        val navigateToImageDownloadButton: Button = findViewById(R.id.btnNavigateToImageDownload)
        navigateToImageDownloadButton.setOnClickListener {
            val intent = Intent(this, ImageDownloadActivity::class.java)
            startActivity(intent)
        }

        val fetchDataButton: Button = findViewById(R.id.fetchDataButton)
        fetchDataButton.setOnClickListener {
            viewModel.refreshData()
        }
        val navigateButton: Button = findViewById(R.id.btnNavigateToDataDisplay)
        navigateButton.setOnClickListener {
            val intent = Intent(this, DataDisplayActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        problemsRecyclerView = findViewById(R.id.mathProblemsRecyclerView)
        adapter = MathProblemsAdapter()
        problemsRecyclerView.adapter = adapter
        problemsRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}
