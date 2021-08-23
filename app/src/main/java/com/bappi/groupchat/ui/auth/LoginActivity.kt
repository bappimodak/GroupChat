package com.bappi.groupchat.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bappi.groupchat.ui.MainActivity
import com.bappi.groupchat.data.entity.User
import com.bappi.groupchat.databinding.ActivityLoginBinding
import com.bappi.groupchat.ui.ViewModelFactory
import com.bappi.groupchat.utils.Status

class LoginActivity : AppCompatActivity() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this, ViewModelFactory(this))
            .get(LoginViewModel::class.java)


        loginButtonClick()
        setUpObserver()
    }

    private fun setUpObserver() {
        loginViewModel.getLoggedInUser().observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.loading.visibility = View.GONE
                    it.data?.let { user -> gotoHome(user) }
                }

                Status.LOADING -> {
                    binding.loading.visibility = View.VISIBLE

                }
                Status.ERROR -> {
                    //Handle Error
                    binding.loading.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun gotoHome(user: User) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("userId", user.uid)
        startActivity(intent)
    }

    private fun loginButtonClick() {
        binding.loginButton.setOnClickListener {
            binding.loading.visibility = View.VISIBLE
            loginViewModel.login(
                binding.username.text.toString(),
            )
        }
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
//        val appContext = applicationContext ?: return
//        Toast.makeText(this@LoginActivity, errorString, Toast.LENGTH_LONG).show()
    }
}