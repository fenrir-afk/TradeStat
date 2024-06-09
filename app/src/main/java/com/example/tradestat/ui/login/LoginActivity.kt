package com.example.tradestat.ui.login

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.tradestat.MainActivity
import com.example.tradestat.data.database.TradeDatabase
import com.example.tradestat.databinding.ActivityLoginBinding
import com.example.tradestat.repository.TradesRepository
import com.example.tradestat.ui.registry.RegistryActivity
import java.time.Duration
import java.time.LocalDateTime
import java.util.Locale


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        setLanguage()
        checkCurrentLoginSession()
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = TradesRepository(TradeDatabase.getDatabase(this))
        val viewModelProvideFactory = LoginViewModelFactory(Application(),repository)
        loginViewModel = ViewModelProvider(this,viewModelProvideFactory)[LoginViewModel::class.java]
        binding.loginButton.setOnClickListener{
            val email = binding.email.text.toString()
            val pass = binding.password.text.toString()
            if (email.isNotBlank() && pass.isNotBlank()){
                loginViewModel.checkUser(email,pass)
            }else{
                Toast.makeText(this,"Fields mush not be empty",Toast.LENGTH_SHORT).show()
            }
            loginViewModel.checkUserResult.observe(this){
                if (it){
                    Toast.makeText(this,"No such user",Toast.LENGTH_SHORT).show()
                }else{
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
        binding.sendToSighUp.setOnClickListener{
            startActivity(Intent(this, RegistryActivity::class.java))
        }

        //we need this to set transparent status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }

    private fun checkCurrentLoginSession() {
        val sharedPreferences = getSharedPreferences("TIME", Context.MODE_PRIVATE)
        val exitTime = sharedPreferences.getLong("EXIT_TIME", 0L)
        if (exitTime != 0L) {
            val currentTime = System.currentTimeMillis()
            val duration = currentTime - exitTime
            if (duration < 30 * 60 * 1000) { // 30 minutes in milliseconds
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    private fun setLanguage(){
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
    }

}