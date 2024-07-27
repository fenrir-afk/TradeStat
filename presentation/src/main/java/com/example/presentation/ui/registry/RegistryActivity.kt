package com.example.presentation.ui.registry

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.presentation.MainActivity
import com.example.presentation.data.model.User
import com.example.presentation.databinding.ActivityRegistryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistryBinding
    private  val registerViewModel: RegistryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                registerViewModel.userResultFow.filter { it != null }.collect { result ->
                    if (result!!) {
                        val intent = Intent(this@RegistryActivity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@RegistryActivity, "Such user is already registered", Toast.LENGTH_SHORT).show()
                    }
                }
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
        setFlags()
    }
    private fun setFlags(){
        val w = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
    private fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("^[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\$")
        return emailRegex.matches(email)
    }
}