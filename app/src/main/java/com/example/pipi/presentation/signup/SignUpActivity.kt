package com.example.pipi.presentation.signup

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pipi.global.constants.ui.Colors
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
            val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }
            val scope = lifecycleScope
            Scaffold(snackbarHost = {
                SignUpSnackbar(snackbarHostState)
            }) {
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
                            viewModel = phoneAuthViewModel,
                            snackBarHostState = snackbarHostState,
                            lifecycleScope = scope,
                            step = Pair(3,1)
                        )
                    }
                    composable("nickName") {
                        SetNickNameScreen(
                            navigate = { goNickNameScreen(navController) },
                            goBack = { navController.popBackStack() },
                            viewModel = viewModel,
                            title = "회원가입",
                            step = Pair(3,2)
                        )
                    }
                    composable("setPassword") {
                        SetPasswordScreen(
                            navController = navController,
                            viewModel = viewModel,
                            step = Pair(3,3),
                            goToMainActivity = { goBackToMainActivity() })
                    }
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

    @Composable
    fun SignUpSnackbar(snackbarHostState: MutableState<SnackbarHostState>) {
        SnackbarHost(hostState = snackbarHostState.value,
            snackbar = { data ->
                Snackbar(
                    action = {
                        TextButton(
                            content = {
                                Text(text = "확인", color = Colors.PRIMARY_BRAND)
                            },
                            onClick = {
                                snackbarHostState.value.currentSnackbarData?.dismiss()
                            }
                        )
                    },
                    backgroundColor = Colors.PRIMARY_TEXT
                ) {
                    Text(
                        text = data.message,
                        style = MaterialTheme.typography.subtitle1,
                        fontSize = 11.sp,
                        color = Color.White
                    )
                }
            })
    }
}