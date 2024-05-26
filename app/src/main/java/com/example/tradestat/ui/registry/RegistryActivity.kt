package com.example.tradestat.ui.registry

import android.app.Application
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.tradestat.MainActivity
import com.example.tradestat.R
import com.example.tradestat.data.database.TradeDatabase
import com.example.tradestat.data.model.User
import com.example.tradestat.databinding.ActivityRegistryBinding
import com.example.tradestat.repository.TradesRepository
import com.example.tradestat.ui.login.LoginViewModelFactory

class RegistryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistryBinding
    private lateinit var registerViewModel: RegistryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistryBinding.inflate(layoutInflater)
        val repository = TradesRepository(TradeDatabase.getDatabase(this))
        val viewModelProvideFactory = RegistryViewModelFactory(Application(),repository)
        registerViewModel = ViewModelProvider(this,viewModelProvideFactory)[RegistryViewModel::class.java]
        setContentView(binding.root)
        registerViewModel.addUserResult.observe(this){
            if (it){
                startActivity(Intent(this, MainActivity::class.java))
                registerViewModel.getUsers()
            }else{
                Toast.makeText(this,"Such user is already registered", Toast.LENGTH_SHORT).show()
            }
        }
        binding.registryButton.setOnClickListener{
            val email = binding.email.text.toString()
            val pass = binding.password.text.toString()
            val login = binding.login.text.toString()
            if (email.isNotBlank() && pass.isNotBlank() && login.isNotBlank()){
                if (isValidEmail(email)){
                    registerViewModel.addUser(User(0,login,email,pass))
                }else{
                    Toast.makeText(this,"Email is not valid", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Fields mush not be empty", Toast.LENGTH_SHORT).show()
            }
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
    fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("^[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\$")
        return emailRegex.matches(email)
    }
}