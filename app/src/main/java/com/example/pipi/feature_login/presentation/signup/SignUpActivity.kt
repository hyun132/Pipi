package com.example.pipi.feature_login.presentation.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pipi.feature_login.presentation.main.MainActivity
import com.example.pipi.feature_login.presentation.start.StartActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpActivity : AppCompatActivity() {
    private val viewModel: SignupViewModel by viewModel()

    @ExperimentalComposeUiApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "phoneAuth") {
                composable("phoneAuth") {
                    PhoneAuthScreen(navController = navController, viewModel = viewModel,backToMain = { goBackToMainActivity() })
                }
                composable("nickName") {
                    SetNickNameScreen(navController = navController, viewModel = viewModel)
                }
                composable("setPassword"){
                    SetPasswordScreen(navController = navController, viewModel = viewModel,goToMainActivity = { goToMainActivity() })
                }
            }
        }

    }

    fun goBackToMainActivity(){
        finish()
    }

    fun goToMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}