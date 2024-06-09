package com.example.tradestat.utils

import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.tradestat.R

class FullscreenImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //hide status bar
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_fullscreen_image)

        // Получение изображения из Intent
        val imageUrl = intent.getStringExtra("image_url")

        // Загрузка и отображение изображения на весь экран
        val imageView = findViewById<ImageView>(R.id.fullscreen_image_view)
        Glide.with(this)
            .load(imageUrl)
            .into(imageView)
    }
}