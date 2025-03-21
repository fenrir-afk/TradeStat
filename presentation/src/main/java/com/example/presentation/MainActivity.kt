package com.example.presentation

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.presentation.databinding.ActivityMainBinding
import com.example.presentation.utils.SendEmail
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        initNightMode()
        setLocale(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.include.myToolBar)

        val navView: BottomNavigationView = binding.navView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController: NavController = navHostFragment.navController

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
    @Suppress("DEPRECATION")
    private fun setLocale(activity: Activity, languageCode: String = "none") {
        var locale = Locale(languageCode)
        if (languageCode == "none"){
            val sharedPreferences = getSharedPreferences("Language", Context.MODE_PRIVATE)
            val locale1 = sharedPreferences.getString("language", "en")
            locale = Locale(locale1!!)
        }
        if (locale != Locale.getDefault()){
            Locale.setDefault(locale)
            val resources = activity.resources
            val config = resources.configuration
            config.setLocale(locale)
            resources.updateConfiguration(config, resources.displayMetrics)
            recreate()
        }else{
            Locale.setDefault(locale)
            val resources = activity.resources
            val config = resources.configuration
            config.setLocale(locale)
            resources.updateConfiguration(config, resources.displayMetrics)
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
                //binding.navView.setBackgroundColor(ContextCompat.getColor(this,R.color.green))
            } else {
                themeItem.setIcon(R.drawable.sun_light)
                binding.navView.setBackgroundColor(ContextCompat.getColor(this,R.color.specialWhite))
                binding.navView.itemIconTintList = ColorStateList.valueOf(ContextCompat.getColor(this,R.color.black))
                binding.navView.itemTextColor = ColorStateList.valueOf(ContextCompat.getColor(this,R.color.black))
            }
        }
        if (localeItem != null) {
            val locale = Locale.getDefault()
            if (locale.language == "ru") {
                localeItem.setIcon(R.drawable.ic_ru1)
            }
            if(locale.language == "en"){
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
                val currentLocale = Locale.getDefault()
                val newLocale = if (currentLocale.language == "ru") {
                    Locale("en")
                }
                else {
                    Locale("ru")
                }
                Locale.setDefault(newLocale)
                setLocale(this,newLocale.language)
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
            R.id.help -> {
                SendEmail(this).showAlertDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }



    override fun onPause() {
        super.onPause()
        val sharedPreferences = getSharedPreferences("TIME", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("EXIT_TIME", System.currentTimeMillis())
        editor.apply()
    }

}
