package com.example.presentation.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import java.io.File
import java.io.FileOutputStream

class ImageHelper(private val activity: ComponentActivity){
    enum class Tokens {
        Note, Trade
    }

    /**
     * In this method we are getting images from gallery and then in the saveImageToDevice method save it to the special folder
     * */
    fun pickImage(filesDir: File,token:Tokens, callback:(String) -> Unit): ActivityResultLauncher<Intent> {
        return activity.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri = result.data?.data
                if (imageUri != null) {
                    val savedImagePath = saveImageToDevice(imageUri,filesDir,token)
                    callback(savedImagePath)
                }
            }
        }
    }

    private fun saveImageToDevice(imageUri: Uri, filesDir: File,token:Tokens): String {
        val imageName = when(token){
            Tokens.Note -> "note_image_${System.currentTimeMillis()}.jpg"
            Tokens.Trade -> "trade_image_${System.currentTimeMillis()}.jpg"
        }
        // Save the image to your device and return the file path
        val inputStream = activity.contentResolver.openInputStream(imageUri)
        val file = File(filesDir, imageName)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.flush()
        outputStream.close()
        return file.absolutePath
    }
}