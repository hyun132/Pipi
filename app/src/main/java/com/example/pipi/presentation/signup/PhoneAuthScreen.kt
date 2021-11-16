package com.example.pipi.presentation.signup

import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.pipi.R
import com.example.pipi.global.constants.ui.Colors
import com.example.pipi.global.constants.ui.Colors.DARK_GRAY
import com.example.pipi.global.constants.ui.Colors.ERROR_RED
import com.example.pipi.global.constants.ui.Colors.GRAY2
import com.example.pipi.global.constants.ui.Colors.MAIN_PURPLE
import com.example.pipi.global.constants.ui.Components
import com.example.pipi.global.constants.ui.Components.drawDefaultButton
import com.example.pipi.global.constants.ui.Components.drawTextTitleTopAppbar
import com.example.pipi.global.constants.ui.setProjectTheme
import timber.log.Timber

@ExperimentalAnimationApi
@Composable
fun PhoneAuthScreen(
    navController: NavController,
    viewModel: SignupViewModel,
    backToMain: () -> Unit
) {
    val phoneNumber: String by viewModel.phoneNumber.observeAsState("")
    val authNumber: String by viewModel.authNumber.observeAsState("")
    val errorMessage: String by viewModel.dialogMessage.observeAsState("")
    val timerStarted by viewModel.timerStarted.observeAsState(false)
    val formattedTime by viewModel.formattedTime.observeAsState("")
    val isbuttonActive by viewModel.phoneAuthSuccess.observeAsState(false)

    setProjectTheme {
        Scaffold(topBar = { drawTextTitleTopAppbar("회원가입") { backToMain() } }) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(24.dp)
            ) {
                val (contentx, button, snackbar) = createRefs()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(contentx) { top.linkTo(parent.top) },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_step_1),
                        contentDescription = "step1",
                        alignment = Alignment.Center,
                        modifier = Modifier.height(24.dp)
                    )
                    Spacer(modifier = Modifier.height(22.dp))
                    Text(
                        text = "휴대전화 번호 인증을 해주세요.", style = MaterialTheme.typography.body2,
                        fontWeight = FontWeight(400),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(36.dp))
                    Components.InputTextField(
                        input = phoneNumber,
                        onChanged = { input -> viewModel.setPhoneNumber(input) },
                        hint = "휴대전화 번호(-제외)",
                        errorMessage = phoneNumberCheck(phoneNumber),
                        rightComponent = {
                            Row(verticalAlignment = CenterVertically) {
                                if (timerStarted) {
                                    Text(
                                        text = formattedTime,
                                        style = MaterialTheme.typography.subtitle2,
                                        fontSize = 11.sp,
                                        color = ERROR_RED
                                    )
                                }
                                TextButton(onClick = {
                                    viewModel.requestSendAuthMessage()
                                }, modifier = Modifier
                                    .height(32.dp)
                                    .width(86.dp)
                                    .padding(0.dp)
                                    .background(MAIN_PURPLE),
                                    shape = RoundedCornerShape(2.dp),
                                    content = {
                                        Text(
                                            text = "인증번호전송",
                                            style = MaterialTheme.typography.subtitle2,
                                            fontSize = 11.sp,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            color = Color.White,
                                        )
                                    })
                            }

                        },
                        hideInputData = false,
                        title = "휴대전화 번호"
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Components.InputTextField(
                        input = authNumber,
                        onChanged = { input -> viewModel.setAuthNumber(input) },
                        hint = "인증번호 6자리",
                        errorMessage = "",
                        rightComponent = {
                            TextButton(onClick = {
                                viewModel.checkAuthSuccess()
                            }, modifier = Modifier
                                .height(32.dp)
                                .width(86.dp)
                                .padding(0.dp)
                                .background(MAIN_PURPLE),
                                shape = RoundedCornerShape(2.dp),
                                content = {
                                    Text(
                                        text = "확인",
                                        style = MaterialTheme.typography.subtitle2,
                                        fontSize = 11.sp,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth(),
                                        color = Color.White
                                    )
                                })
                        },
                        hideInputData = false,
                        title = "인증번호"
                    )
                }

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(button) { bottom.linkTo(parent.bottom) }) {
                    drawDefaultButton(
                        color = if (isbuttonActive) MAIN_PURPLE else GRAY2,
                        text = "다음",
                        onClick = { navController.navigate("nickName") },
                        isEnabled = isbuttonActive
                    )
                }
                viewModel.countTime()
            }
        }
        showSnackbar(errorMessage)
    }
}

fun phoneNumberCheck(phone: String): String {
    return if (!phone.startsWith("010")) "올바른 형식이 아닙니다"
    else if (phone.length < 11) "휴대전화 번호는 11자로 설정해 주세요"
    else "이미 가입된 번호입니다." //이미 있는 번호 api요청
}

@Composable
fun showSnackbar(message: String) {
    if (message.isNotEmpty()) {
        Snackbar(
            action = {
                TextButton(
                    content = {
                        Text(text = "확인", color = MAIN_PURPLE)
                    },
                    onClick = { Timber.d("clicked") }
                )
            },
            backgroundColor = DARK_GRAY
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.subtitle1,
                fontSize = 11.sp,
                color = Color.White
            )
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
            .height(48.dp)
            .padding(top = 8.dp)
            .drawBehind {
                drawLine(
                    color = if (phoneNumber.isNotEmpty()) MAIN_PURPLE else GRAY2,
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
                    .padding(top = 8.dp),
            )
            //아무래도 텍스트로 바꾸는게 나을듯. 기본패딩이..너무많아
            TextButton(onClick = { onButtonClicked() }, modifier = Modifier
                .height(32.dp)
                .width(86.dp)
                .padding(0.dp)
                .background(MAIN_PURPLE), content = {
                Text(
                    text = buttonText,
                    style = MaterialTheme.typography.subtitle2,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            })
        }
        Box(
            modifier = Modifier
                .height(48.dp), contentAlignment = Alignment.CenterStart
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