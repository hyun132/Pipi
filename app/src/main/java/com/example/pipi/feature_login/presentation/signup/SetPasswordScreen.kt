package com.example.pipi.feature_login.presentation.signup

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.pipi.R
import com.example.pipi.feature_login.presentation.login.drawPwInput
import com.example.pipi.global.constants.ui.Colors
import com.example.pipi.global.constants.ui.Components
import com.example.pipi.global.constants.ui.Components.drawTextTitleTopAppbar
import com.example.pipi.global.constants.ui.setProjectTheme

@ExperimentalAnimationApi
@Composable
fun SetPasswordScreen(
    navController: NavController,
    viewModel: SignupViewModel
) {
    setProjectTheme {
        Scaffold(topBar = { drawTextTitleTopAppbar("회원가입") { navController.navigateUp() } }) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(24.dp)
            ) {
                val (contentx, button) = createRefs()
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(contentx) { top.linkTo(parent.top) }) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_step_1),
                        contentDescription = "step1",
                        alignment = Alignment.Center
                    )
                    Spacer(modifier = Modifier.height(22.dp))
                    Text(text = "휴대전화 번호 인증을 해주세요.")
                    Spacer(modifier = Modifier.height(36.dp))
                    InputTextFeildWithButton(
                        phoneNumber = viewModel.phoneNumber.value,
                        onChanged = { input -> viewModel.phoneNumber.value = input },
                        onButtonClicked = { /*TODO*/ },
                        hint = "휴대전화 번호(-제외)",
                        buttonText = "인증번호전송"
                    )
                }

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(button) { bottom.linkTo(parent.bottom) }) {
                    Components.drawDefaultButton(
                        color = if (viewModel.isPhoneNumberAuthSuccessed.value == true) Colors.MAIN_PURPLE else Colors.GRAY2,
                        text = "다음",
                        onClick = { /* 여기서 인증 성공했는지 체크하고 네비게이션 해야함*/ },
                        isEnabled = viewModel.isPhoneNumberAuthSuccessed.value == true
                    )
                }
            }

        }
    }
}