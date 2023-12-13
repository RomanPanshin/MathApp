package com.example.mathapp2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mathapp2.data.db.AppDatabase
import com.example.mathapp2.data.repository.MathRepository
import com.example.mathapp2.ui.theme.AlgebraFragment
import com.example.mathapp2.ui.theme.GeometryFragment
import com.example.mathapp2.ui.theme.MathBasicsFragment
import com.example.mathapp2.viewmodel.MyViewModel
import com.example.mathapp2.viewmodel.MyViewModelFactory

class MainActivity : ComponentActivity() {
    private val viewModel: MyViewModel by viewModels {
        val dao = AppDatabase.getDatabase(application).mathProblemDao()
        val repository = MathRepository(dao)
        MyViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val currentFragment = remember { mutableStateOf("Unknown") }
            navController.addOnDestinationChangedListener { _, destination, _ ->
                currentFragment.value = destination.route ?: "Unknown"
            }
            Scaffold(
                topBar = {
                    TopAppBar(title = { Text("Main Activity") }) // Static title
                },
                bottomBar = { BottomAppBarContent() }
                // drawerContent parameter removed as it's not supported here
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    NavHost(navController = navController, startDestination = "mathBasicsFragment") {
                        composable("mathBasicsFragment") { MathBasicsFragment() }
                        composable("algebraFragment") { AlgebraFragment() }
                        composable("geometryFragment") { GeometryFragment() }
                    }
                    MathAppScreen(navController, viewModel, this@MainActivity, currentFragment)
                }
            }
        }
    }
    @Composable
    fun BottomAppBarContent() {
        BottomAppBar {
            androidx.compose.material.Button(onClick = { navigateToActivity<MainActivity>() }) {
                androidx.compose.material.Text(text = "Main Activity")
            }
            androidx.compose.material.Button(onClick = { navigateToActivity<ImageDownloadActivity>() }) {
                androidx.compose.material.Text(text = "Download Image")
            }
            androidx.compose.material.Button(onClick = {  navigateToActivity<DataDisplayActivity>()}) {
                androidx.compose.material.Text(text = "Display Data")
            }
        }
    }
    inline fun <reified T : ComponentActivity> navigateToActivity() {
        val intent = Intent(this, T::class.java)
        startActivity(intent)
    }
}


@Composable
fun MathAppScreen(navController: NavHostController, viewModel: MyViewModel, activity: MainActivity, currentFragment: MutableState<String>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Current Fragment: ${currentFragment.value}")
        Spacer(modifier = Modifier.height(16.dp))
        NavigationButton(text = "Math Basics", onClick = { navController.navigate("mathBasicsFragment") })
        Spacer(modifier = Modifier.height(8.dp))
        NavigationButton(text = "Algebra", onClick = {  navController.navigate("algebraFragment")})
        Spacer(modifier = Modifier.height(8.dp))
        NavigationButton(text = "Geometry", onClick = { navController.navigate("geometryFragment") })
        Spacer(modifier = Modifier.height(16.dp))
    }
}


@Composable
fun NavigationButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text = text)
    }
}


