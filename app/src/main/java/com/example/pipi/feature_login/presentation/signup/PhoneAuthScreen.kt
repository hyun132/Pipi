package com.example.pipi.feature_login.presentation.signup

import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.compiler.plugins.kotlin.ComposeFqNames.remember
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
import androidx.compose.ui.draw.clip
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
import androidx.lifecycle.MutableLiveData
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
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.*
import kotlin.concurrent.timer

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
                        onChanged = { input -> viewModel.phoneNumber.value = input },
                        hint = "휴대전화 번호(-제외)",
                        errorMessage = "",
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
                                    .background(MAIN_PURPLE), content = {
                                    Text(
                                        text = "인증번호전송",
                                        style = MaterialTheme.typography.subtitle2,
                                        fontSize = 11.sp,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(2.dp)),
                                        color = Color.White,
                                    )
                                })
                            }

                        },
                        hideInputData = false
                    )
                    Components.InputTextField(
                        input = authNumber,
                        onChanged = { input -> viewModel.authNumber.value = input },
                        hint = "인증번호 6자리",
                        errorMessage = "",
                        rightComponent = {
                            TextButton(onClick = {
                                viewModel.dialogMessage.value = "인증번호를 문자로 전송하였습니다."
                            }, modifier = Modifier
                                .height(32.dp)
                                .width(86.dp)
                                .padding(0.dp)
                                .background(MAIN_PURPLE), content = {
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
                        hideInputData = false
                    )
                }

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(button) { bottom.linkTo(parent.bottom) }) {
                    drawDefaultButton(
                        color = if (viewModel.checkAuthSuccess()) MAIN_PURPLE else GRAY2,
                        text = "다음",
                        onClick = { navController.navigate("nickName") },
                        isEnabled = viewModel.checkAuthSuccess()
                    )
                }
                viewModel.countTime()
            }
        }
        showSnackbar(errorMessage)
    }
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