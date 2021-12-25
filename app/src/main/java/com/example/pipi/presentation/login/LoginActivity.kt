package com.example.pipi.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pipi.presentation.main.MainActivity
import com.example.pipi.presentation.signup.SignUpActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : ComponentActivity() {

    private val viewModel: LoginViewModel by viewModel()

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainLoginScreen(
                viewModel = viewModel,
                goSignUpActivity = { goSignUpActivity() },
                goMainActivity = { goMainActivity() },
                goFindPasswordActivity = { goFindPasswordActivity() })
        }
    }

    private fun goMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun goSignUpActivity() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    //이걸 액티비티로 두는게 맞나?
    private fun goFindPasswordActivity() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }
}
