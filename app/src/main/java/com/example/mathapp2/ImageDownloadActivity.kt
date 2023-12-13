package com.example.mathapp2

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mathapp2.viewmodel.ImageDownloadViewModel
import com.example.mathapp2.work.MathProblemsUpdateWorker

class ImageDownloadActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: ImageDownloadViewModel = viewModel()
            Button(onClick = { triggerMathProblemsUpdate() }) {
                Text("Update Math Problems")
            }
            Scaffold(
                topBar = {
                    TopAppBar(title = { Text("Download Activity") }) // Static title
                },
                bottomBar = { BottomAppBarContent() }
                // drawerContent parameter removed as it's not supported here
            ){
                ImageDownloadScreen(viewModel)
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
    private fun triggerMathProblemsUpdate() {
        val workRequest = OneTimeWorkRequestBuilder<MathProblemsUpdateWorker>().build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }
    inline fun <reified T : ComponentActivity> navigateToActivity() {
        val intent = Intent(this, T::class.java)
        startActivity(intent)
    }
}

@Composable
fun ImageDownloadScreen(viewModel: ImageDownloadViewModel) {
    var imageUrl by remember { mutableStateOf("") }
    val downloadedImage by viewModel.downloadedImage.collectAsState()
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.Center) {
        BasicTextField(
            value = imageUrl,
            onValueChange = { imageUrl = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { viewModel.downloadAndSaveImage(imageUrl) }) {
            Text("Download Image")
        }
        Spacer(modifier = Modifier.height(8.dp))
        downloadedImage?.let { bitmap ->
            Image(bitmap = bitmap.asImageBitmap(), contentDescription = "Downloaded Image")
        }
    }

}
