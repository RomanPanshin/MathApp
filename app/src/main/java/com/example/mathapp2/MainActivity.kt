package com.example.mathapp2

import android.os.Bundle
import android.widget.Button
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
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.mathapp2.ui.theme.AlgebraFragment
import com.example.mathapp2.ui.theme.GeometryFragment
import com.example.mathapp2.ui.theme.MathApp2Theme
import com.example.mathapp2.ui.theme.MathBasicsFragment


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Setup navigation with buttons
        setupButtonNavigation()
    }

    private fun setupButtonNavigation() {
        findViewById<Button>(R.id.mathBasicsButton).setOnClickListener {
            navController.navigate(R.id.mathBasicsFragment)
        }

        findViewById<Button>(R.id.algebraButton).setOnClickListener {
            navController.navigate(R.id.algebraFragment)
        }

        findViewById<Button>(R.id.geometryButton).setOnClickListener {
            navController.navigate(R.id.geometryFragment)
        }
    }
}

