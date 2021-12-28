package com.example.pipi.presentation.setting

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pipi.presentation.duplicated.PhoneAuthScreen
import com.example.pipi.presentation.duplicated.PhoneAuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResetPasswordActivity : AppCompatActivity() {
    private val viewModel: ResetUserPasswordViewModel by viewModel()
    private val phoneAuthViewModel: PhoneAuthViewModel by viewModel()

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "phoneAuth") {
                composable("phoneAuth") {
                    PhoneAuthScreen(
                        navigate = { goResetPasswordScreen(navController) },
                        backToMain = { goToMainActivity() },
                        title = "회원가입",
                        viewModel=phoneAuthViewModel
                    )
                }
                composable("resetPassword") {
                    ReSetPasswordScreen(
                        navigate = { goToMainActivity() },
                        viewModel = viewModel
                    ) { navController.navigateUp() }
                }
            }
        }

    }

    private fun goResetPasswordScreen(navController: NavController) {
        navController.navigate("resetPassword")
    }

    private fun goToMainActivity() {
        finish()
    }
}