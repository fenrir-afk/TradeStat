package com.example.tradestat.ui.dateStatistic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tradestat.R
import com.example.tradestat.databinding.ActivityDateBinding
import com.example.tradestat.databinding.ActivityMainBinding

class DateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDateBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}