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
import com.example.mathapp2.viewmodel.ImageDownloadViewModel

class ImageDownloadActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: ImageDownloadViewModel = viewModel()
            Scaffold(
                topBar = {
                    TopAppBar(title = { Text("Download Activity") }) // Static title
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
            ){
                ImageDownloadScreen(viewModel)
            }
        }
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
