package com.example.presentation.utils

import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.presentation.R

/**
 *We use this class to make images of the app scalable
 * */
class FullscreenImageActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //hide status bar
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_fullscreen_image)
        // Getting images from intent
        val imageUrl = intent.getStringExtra("image_url")

        // Load and display image for full screen
        val imageView = findViewById<ImageView>(R.id.fullscreen_image_view)
        Glide.with(this)
            .load(imageUrl)
            .into(imageView)
    }
}