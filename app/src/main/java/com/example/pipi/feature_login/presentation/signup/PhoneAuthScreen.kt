package com.example.pipi.feature_login.presentation.signup

import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.pipi.R
import com.example.pipi.feature_login.presentation.login.drawPwInput
import com.example.pipi.global.constants.ui.Colors
import com.example.pipi.global.constants.ui.Colors.GRAY2
import com.example.pipi.global.constants.ui.Colors.MAIN_PURPLE
import com.example.pipi.global.constants.ui.Components
import com.example.pipi.global.constants.ui.Components.drawDefaultButton
import com.example.pipi.global.constants.ui.Components.drawTextTitleTopAppbar
import com.example.pipi.global.constants.ui.setProjectTheme

@ExperimentalAnimationApi
@Composable
fun PhoneAuthScreen(
    navController: NavController,
    viewModel: SignupViewModel,
    backToMain: () -> Unit
) {
    setProjectTheme {
        Scaffold(topBar = { drawTextTitleTopAppbar("회원가입") { backToMain() } }) {
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
                    InputTextFeildWithButton(
                        phoneNumber = viewModel.authNumber.value,
                        onChanged = { input -> viewModel.authNumber.value = input },
                        onButtonClicked = { /*TODO*/ },
                        hint = "인증번호 6자리",
                        buttonText = "확인"
                    )
                }

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(button) { bottom.linkTo(parent.bottom) }) {
                    drawDefaultButton(
                        color = if (viewModel.isPhoneNumberAuthSuccessed.value == true) MAIN_PURPLE else GRAY2,
                        text = "다음",
                        onClick = { /* 여기서 인증 성공했는지 체크하고 네비게이션 해야함*/ },
                        isEnabled = viewModel.isPhoneNumberAuthSuccessed.value == true
                    )
                }
            }

        }
    }
}

@Composable
fun InputTextFeildWithButton(
    phoneNumber: String,
    onChanged: (String) -> Unit,
    onButtonClicked: () -> Unit,
    hint: String,
    buttonText: String
) {
    Box() {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .padding(top = 8.dp)
            .drawBehind {
                drawLine(
                    color = if (phoneNumber.isNotEmpty()) Colors.MAIN_PURPLE else Colors.GRAY2,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height)
                )
            }) {
            BasicTextField(
                value = phoneNumber,
                onValueChange = { onChanged(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
            )
            //아무래도 텍스트로 바꾸는게 나을듯. 기본패딩이..너무많아
            Button(onClick = { onButtonClicked() }, modifier = Modifier
                .height(32.dp)
                .width(86.dp).
                padding(0.dp), content = {
                Text(
                    text = buttonText,
                    style = MaterialTheme.typography.subtitle2,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center
                )
            })
        }
        Box(
            modifier = Modifier
                .height(48.dp)
                .padding(top = 8.dp), contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = if (phoneNumber.isEmpty()) hint else "",
                color = Colors.GRAY2,
                style = MaterialTheme.typography.subtitle2,
            )
            Toast.LENGTH_SHORT
        }
    }

}