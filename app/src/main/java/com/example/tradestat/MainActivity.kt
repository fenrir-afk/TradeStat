package com.example.tradestat

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.tradestat.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        initNightMode()
        val current = Locale.getDefault()
        val sharedPreferences = getSharedPreferences("Language", Context.MODE_PRIVATE)
        val language = sharedPreferences.getString("language", "en")
        if (current != Locale(language!!)){
                val newLocale = Locale(language)
                Locale.setDefault(newLocale)
                val resources = resources
                val configuration = resources.configuration
                configuration.setLocale(newLocale)
                resources.updateConfiguration(configuration, resources.displayMetrics)
                recreate()
        }
        setContentView(binding.root)
        setSupportActionBar(binding.include.myToolBar)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_trades, R.id.navigation_news
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        window.statusBarColor = ContextCompat.getColor(this, R.color.background)
    }

    private fun initNightMode() {
        val sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE)
        val nightMode = sharedPreferences.getBoolean("night", false)
        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val themeItem = menu?.findItem(R.id.Theme)
        val localeItem = menu?.findItem(R.id.Language)
        if (themeItem != null) {
            val nightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            if (nightMode == Configuration.UI_MODE_NIGHT_YES) {
                themeItem.setIcon(R.drawable.white_sun)
            } else {
                themeItem.setIcon(R.drawable.sun_light)
            }
        }
        if (localeItem != null) {
            val locale = Locale.getDefault()
            if (locale == Locale("ru")) {
                localeItem.setIcon(R.drawable.ic_ru1)
            } else {
                localeItem.setIcon(R.drawable.ic_en1)
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.Language -> {
                val sharedPreferences = getSharedPreferences("Language", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                val currentLocale = resources.configuration.locales[0]
                val newLocale = if (currentLocale.language == "ru") Locale("en") else Locale("ru")
                Locale.setDefault(newLocale)
                val resources = resources
                val configuration = resources.configuration
                configuration.setLocale(newLocale)
                resources.updateConfiguration(configuration, resources.displayMetrics)
                editor.putString("language", newLocale.language)
                editor.apply()
                // Обновляем иконку после смены языка
                if (newLocale.language == "ru") {
                    item.setIcon(R.drawable.ic_ru1)
                    recreate()
                } else {
                    item.setIcon(R.drawable.ic_en1)
                    recreate()
                }
            }
            R.id.Theme -> {
                // Ваш существующий код для смены темы
                val sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                if (item.icon!!.constantState == ContextCompat.getDrawable(this, R.drawable.white_sun)?.constantState) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    editor.putBoolean("night", false)
                    editor.apply()
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    editor.putBoolean("night", true)
                    editor.apply()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
