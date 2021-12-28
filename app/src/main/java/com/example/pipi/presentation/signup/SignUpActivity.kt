package com.example.pipi.presentation.signup

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

class SignUpActivity : AppCompatActivity() {
    private val viewModel: SignupViewModel by viewModel()
    private val phoneAuthViewModel: PhoneAuthViewModel by viewModel()

    @ExperimentalComposeUiApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "tos") {
                composable("tos") {
                    TosScreen(
                        navigate = { goPhoneAuthScreen(navController) },
                        viewModel = viewModel,
                        backToMain = { goBackToMainActivity() })
                }
                composable("phoneAuth") {
                    PhoneAuthScreen(
                        navigate = { goNickNameScreen(navController) },
                        backToMain = { goBackToMainActivity() },
                        title = "회원가입",
                        viewModel = phoneAuthViewModel
                    )
                }
                composable("nickName") {
                    SetNickNameScreen(
                        navigate = { goNickNameScreen(navController) },
                        goBack = { navController.popBackStack() },
                        viewModel = viewModel,
                        title = "회원가입"
                    )
                }
                composable("setPassword") {
                    SetPasswordScreen(
                        navController = navController,
                        viewModel = viewModel,
                        goToMainActivity = { goBackToMainActivity() })
                }
            }
        }

    }

    private fun goPhoneAuthScreen(navController: NavController) {
        navController.navigate("phoneAuth")
    }

    private fun goNickNameScreen(navController: NavController) {
        navController.navigate("nickName")
    }

    private fun goBackToMainActivity() {
        finish()
    }
}