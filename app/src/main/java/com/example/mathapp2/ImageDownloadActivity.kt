package com.example.mathapp2

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
import com.example.mathapp2.ui.theme.MathApp2Theme
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class ImageDownloadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val urlEditText: EditText = findViewById(R.id.urlEditText)
        val downloadButton: Button = findViewById(R.id.downloadButton)

        downloadButton.setOnClickListener {
            val url = urlEditText.text.toString()
            if (url.isNotEmpty()) {
                downloadAndSaveImage(url)
            } else {
                Toast.makeText(this, "Please enter a URL", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun downloadAndSaveImage(url: String) {
        // Network thread to download the image
        Thread {
            try {
                val imageUrl = URL(url)
                val connection: HttpURLConnection = imageUrl.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val inputStream: InputStream = connection.inputStream
                val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)

                // Disk thread to save the image
                Thread {
                    saveImageToInternalStorage(bitmap)
                }.start()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun saveImageToInternalStorage(bitmap: Bitmap) {
        val filename = "downloaded_image.png"
        val fos: FileOutputStream
        try {
            fos = openFileOutput(filename, Context.MODE_PRIVATE)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.close()

            runOnUiThread {
                Toast.makeText(this, "Image saved successfully", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
