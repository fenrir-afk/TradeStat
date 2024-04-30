package com.example.tradestat

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.tradestat.databinding.ActivityMainBinding

/**
* This is a fist point of app
* */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE)
        val nightMode = sharedPreferences.getBoolean("night", false)
        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.include.myToolBar)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        window.statusBarColor = ContextCompat.getColor(this, R.color.background)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    /**
     * Only in this method we can change the menu icons even after theme change
     * */
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val themeItem = menu?.findItem(R.id.Theme)
        if (themeItem != null) {
            val nightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            if (nightMode == Configuration.UI_MODE_NIGHT_YES) {
                themeItem.setIcon(R.drawable.white_sun)
            } else {
                themeItem.setIcon(R.drawable.sun_light)
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }
    /**
     * In this method we call onPrepareOptionsMenu and change the app theme
     * */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        when (item.itemId) {
            R.id.Language -> {
                if (item.icon!!.constantState == ContextCompat.getDrawable(this, R.drawable.ic_ru1)?.constantState) {
                    item.setIcon(R.drawable.ic_en1)
                } else {
                    item.setIcon(R.drawable.ic_ru1)
                }
            }
            R.id.Theme -> {
                if (item.icon!!.constantState == ContextCompat.getDrawable(this, R.drawable.white_sun)?.constantState) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    editor.putBoolean("night",false)
                    editor.apply()
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    editor.putBoolean("night",true)
                    editor.apply()
                }
                invalidateOptionsMenu() // Обновляем меню после смены темы
            }
        }
        return super.onOptionsItemSelected(item)
    }

}