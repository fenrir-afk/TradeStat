package com.example.tradestat.ui.login

import android.app.Application
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.tradestat.MainActivity
import com.example.tradestat.R
import com.example.tradestat.data.database.TradeDatabase
import com.example.tradestat.databinding.ActivityLoginBinding
import com.example.tradestat.repository.TradesRepository
import com.example.tradestat.ui.registry.RegistryActivity


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = TradesRepository(TradeDatabase.getDatabase(this))
        //window.statusBarColor = ContextCompat.getColor(this, R.color.transparentFull)
        val viewModelProvideFactory = LoginViewModelFactory(Application(),repository)
        loginViewModel = ViewModelProvider(this,viewModelProvideFactory)[LoginViewModel::class.java]
        loginViewModel.checkUserResult.observe(this){
            if (it){
                Toast.makeText(this,"No such user",Toast.LENGTH_SHORT).show()
            }else{
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
        binding.loginButton.setOnClickListener{
            val email = binding.email.text.toString()
            val pass = binding.password.text.toString()
            if (email.isNotBlank() && pass.isNotBlank()){
                loginViewModel.checkUser(email,pass)
            }else{
                Toast.makeText(this,"Fields mush not be empty",Toast.LENGTH_SHORT).show()
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

}