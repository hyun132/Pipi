package com.example.pipi.feature_login.presentation.signup

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.pipi.global.constants.ui.Components
import com.example.pipi.global.constants.ui.setProjectTheme

@Composable
fun SetNickNameScreen(
    navController: NavController,
    viewModel: SignupViewModel
) {
    setProjectTheme(content = {
        Scaffold(topBar = { Components.drawTextTitleTopAppbar("회원가입") }) {
            ConstraintLayout() {


            }
        }
    })
}