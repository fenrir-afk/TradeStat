package com.example.tradestat.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.tradestat.MainActivity
import com.example.tradestat.databinding.ActivityLoginBinding
import com.example.tradestat.ui.registry.RegistryActivity
import com.example.tradestat.utils.BaseViewModelFactory
import java.util.Locale


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels{ BaseViewModelFactory}
    override fun onCreate(savedInstanceState: Bundle?) {
        setLanguage()
        checkCurrentLoginSession()
        super.onCreate(savedInstanceState)
        //we need this to set transparent status bar
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
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