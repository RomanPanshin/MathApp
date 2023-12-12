package com.example.mathapp2

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.mathapp2.R
import kotlinx.coroutines.*
import java.io.*
import java.net.URL

class ImageDownloadActivity : AppCompatActivity() {

    private lateinit var savedImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_download)

        savedImageView = findViewById(R.id.savedImageView)
        val linkEditText = findViewById<EditText>(R.id.linkEditText)
        val downloadButton = findViewById<Button>(R.id.downloadButton)

        downloadButton.setOnClickListener {
            val url = linkEditText.text.toString()
            downloadAndSaveImage(url)
        }
    }

    private fun downloadAndSaveImage(url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val image = downloadImage(url)
            val savedImagePath = saveImageToInternalStorage(image)

            runOnUiThread {
                if (savedImagePath != null) {
                    displayImage(savedImagePath)
                } else {
                    // Handle error
                }
            }
        }
    }
    private suspend fun downloadImage(urlString: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
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

    private fun saveImageToInternalStorage(bitmap: Bitmap?): String? {
        return try {
            bitmap?.let {
                val fileName = "downloaded_image.png"
                openFileOutput(fileName, MODE_PRIVATE).use { stream ->
                    if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)) {
                        getFileStreamPath(fileName).absolutePath
                    } else {
                        null
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun displayImage(imagePath: String) {
        val bitmap = BitmapFactory.decodeFile(imagePath)
        savedImageView.setImageBitmap(bitmap)
        savedImageView.visibility = View.VISIBLE
    }
}
