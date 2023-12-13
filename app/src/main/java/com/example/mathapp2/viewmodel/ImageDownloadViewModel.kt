package com.example.mathapp2.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.URL

class ImageDownloadViewModel : ViewModel() {
    private val _downloadedImage = MutableStateFlow<Bitmap?>(null)
    val downloadedImage = _downloadedImage.asStateFlow()

    fun downloadAndSaveImage(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val image = downloadImage(url)
            _downloadedImage.emit(image)
        }
    }

    private suspend fun downloadImage(urlString: String): Bitmap? {
        return try {
            val url = URL(urlString)
            val connection = url.openConnection()
            connection.connect()
            val inputStream = connection.getInputStream()
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
