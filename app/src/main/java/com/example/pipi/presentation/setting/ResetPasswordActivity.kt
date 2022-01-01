package com.example.pipi.presentation.setting

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

class ResetPasswordActivity : AppCompatActivity() {
    private val viewModel: ResetUserPasswordViewModel by viewModel()
    private val phoneAuthViewModel: PhoneAuthViewModel by viewModel()

    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }
            val scope = lifecycleScope
            Scaffold(snackbarHost = { ResetPasswordSnackBar(snackbarHostState) }) {
                NavHost(navController = navController, startDestination = "phoneAuth") {
                    composable("phoneAuth") {
                        PhoneAuthScreen(
                            navigate = { goResetPasswordScreen(navController) },
                            backToMain = { goToMainActivity() },
                            title = "비밀번호 재설정",
                            viewModel = phoneAuthViewModel,
                            step = Pair(2, 1),
                            snackBarHostState = snackbarHostState,
                            lifecycleScope = scope
                        )
                    }
                    composable("resetPassword") {
                        ReSetPasswordScreen(
                            navigate = { goToMainActivity() },
                            step = Pair(2, 2),
                            viewModel = viewModel
                        ) { navController.navigateUp() }
                    }
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

    @Composable
    fun ResetPasswordSnackBar(snackbarHostState: MutableState<SnackbarHostState>) {
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