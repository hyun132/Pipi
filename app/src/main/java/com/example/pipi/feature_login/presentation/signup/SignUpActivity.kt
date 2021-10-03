package com.example.pipi.feature_login.presentation.signup

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpActivity : AppCompatActivity() {
    private val viewModel: SignupViewModel by viewModel()

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "phoneauth") {
                composable("phoneauth") {
                    PhoneAuthScreen(navController = navController, viewModel = viewModel,backToMain = { goBackToMainActivity() })
                }
            }
        }

    }

    fun goBackToMainActivity(){
        finish()
    }
}