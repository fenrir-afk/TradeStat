package com.example.presentation.ui.usefullMaterials

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.presentation.R

class MaterialsActivity : AppCompatActivity() {
    private var url = "https://smart-money.trading/smart-money-concept/"
    private var url1 = "https://www.livelib.ru/book/1001340225-tehnicheskij-analiz-polnyj-kurs-dzhek-shvager"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_materials)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var image1 = findViewById<CardView>(R.id.image2)
        image1.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
        var image2 = findViewById<CardView>(R.id.image3)
        image2.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url1))
            startActivity(intent)
        }
    }
}