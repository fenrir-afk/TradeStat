package com.example.tradestat.ui.login

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tradestat.MainActivity
import com.example.tradestat.data.database.TradeDatabase
import com.example.tradestat.databinding.ActivityLoginBinding
import com.example.tradestat.repository.TradesRepository
import com.example.tradestat.ui.registry.RegistryActivity
import com.example.tradestat.utils.BaseViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import java.util.Locale


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels {
        val repository = TradesRepository(TradeDatabase.getDatabase(this))
        BaseViewModelFactory(repository, Application())
    }
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
            lifecycleScope.launch(Dispatchers.Main){
                repeatOnLifecycle(Lifecycle.State.STARTED){
                    loginViewModel.checkUserResultFlow.filter { it != null }.collect{
                        if (it!!){
                            Toast.makeText(applicationContext,"No such user",Toast.LENGTH_SHORT).show()
                        }else{
                            startActivity(Intent(applicationContext, MainActivity::class.java))
                            finish()
                        }
                    }
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
            this.createConfigurationContext(configuration)
            recreate()
        }
    }

}