package com.example.pipi.presentation.login

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.pipi.R
import com.example.pipi.global.constants.ui.Colors.SECONDARY_TEXT_GHOST
import com.example.pipi.global.constants.ui.Components
import com.example.pipi.global.constants.ui.Components.showLoadingDialog
import com.example.pipi.global.constants.ui.setProjectTheme

@ExperimentalAnimationApi
@Composable
fun MainLoginScreen(
    navController: NavController,
    viewModel: LoginViewModel,
    goSignUpActivity: () -> Unit,
    goMainActivity: () -> Unit,
    goFindPasswordActivity: () -> Unit
) {
    val id: String by viewModel.id.observeAsState("")
    val password: String by viewModel.password.observeAsState("")
    val isLoginSuccess: Boolean by viewModel.isLoginSuccess.observeAsState(false)
    val isLoading: Boolean by viewModel.isLoading.observeAsState(false)
    var passwordVisibility by remember { mutableStateOf(true) }
    setProjectTheme(content = {
        Scaffold() {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(24.dp)
            ) {
                val (icon, button, titlebox, textfeildbox, checkbox, signupLoginBox) = createRefs()

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = CenterHorizontally)
                    .constrainAs(icon) {
                        top.linkTo(parent.top)
                    }) {
                    Column(Modifier.padding(top = 10.dp, bottom = 51.dp)) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_pipi),
                            contentDescription = "pipi",
                            modifier = Modifier
                                .height(22.dp)
                                .width(42.dp)
                        )
                    }
                }

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(titlebox) {
                        top.linkTo(icon.bottom)
                    }) {
                    drawTitle("트레이너님\n반갑습니다:)", "로그인 후 이용해주세요")
                }

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 49.dp)
                    .constrainAs(textfeildbox) {
                        top.linkTo(titlebox.bottom)
                    }) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                    ) {
                        Components.InputTextField(
                            input = id,
                            onChanged = { input -> viewModel.id.value = input },
                            hint = "휴대전화 번호를 입력해 주세요(-제외)",
                            errorMessage = null,
                            rightComponent = {
                                if (id.isNotEmpty()) {
                                    Icon(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clickable(
                                                onClick = { viewModel.id.value = "" }
                                            ),
                                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete),
                                        contentDescription = null
                                    )
                                } else {
                                }
                            },
                            hideInputData = false,
                            title = ""
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Components.InputTextField(
                            input = password,
                            onChanged = { input -> viewModel.password.value = input },
                            hint = "비밀번호를 입력해 주세요",
                            errorMessage = null,
                            rightComponent = {
                                Row(verticalAlignment = CenterVertically) {
                                    Icon(
                                        modifier = Modifier
//                                            .size(48.dp)
//                                            .padding(12.dp)
                                            .clickable(
                                                onClick = {
                                                    passwordVisibility = !passwordVisibility
                                                }
                                            ),
//                                        .align(CenterVertically),
                                        imageVector = if (passwordVisibility) ImageVector.vectorResource(
                                            id = R.drawable.ic_unshow
                                        ) else ImageVector.vectorResource(id = R.drawable.ic_show),
                                        contentDescription = null
                                    )
                                    if (id.isNotEmpty()) {
                                        Icon(
                                            modifier = Modifier
//                                                .size(24.dp)
                                                .clickable(
                                                    onClick = { viewModel.password.value = "" }
                                                ),
                                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete),
                                            contentDescription = null
                                        )
                                    } else {
                                    }
                                }
                            },
                            hideInputData = passwordVisibility,
                            title = ""
                        )
                    }
                }
                //자동로그인 체크박스 넣어야하는데, 체크되면 로그인성공시 sharedpreference에 저장하고 체크 해제하는 즉시 저장된 내용 삭제하는걸로.
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(checkbox) { top.linkTo(textfeildbox.bottom) }
                        .padding(top = 24.dp)
                ) {

                    labeledCheckbox(
                        label = "자동로그인",
                        onChecked = { ischecked -> viewModel.autoLogin.value = ischecked },
                        checked = viewModel.autoLogin.value
                    )
                    Spacer(modifier = Modifier.width(17.dp))
                    labeledCheckbox(
                        label = "휴대전화 번호 저장",
                        checked = viewModel.rememberPhoneNumber.value
                    ) { ischecked ->
                        viewModel.rememberPhoneNumber.value = ischecked
                    }
                }

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .constrainAs(button) {
                        top.linkTo(checkbox.bottom)
                    }) {
                    Components.drawDefaultButton(
                        color = colorResource(id = R.color.main_purple),
                        text = "로그인",
                        isEnabled = (viewModel.password.value?.length ?: 0) >= 6,
                        onClick = {
                            viewModel.login()
                        }
                    )
                    if (isLoginSuccess) {
                        goMainActivity()
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }

                Box(modifier = Modifier.constrainAs(signupLoginBox) { bottom.linkTo(parent.bottom) }) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Text(
                            text = "비밀번호찾기",
                            Modifier
                                .clickable(onClick = {
                                    Log.d("TAG", "비밀번호 찾기")
                                    goFindPasswordActivity()
                                })
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally),
                            style = MaterialTheme.typography.subtitle2,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(22.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "아직 회원이 아니신가요",
                                style = MaterialTheme.typography.subtitle2,
                                color = SECONDARY_TEXT_GHOST,
                                fontSize = 12.sp,
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "회원가입",
                                style = MaterialTheme.typography.subtitle2,
                                color = MaterialTheme.colors.primary,
                                fontSize = 12.sp,
                                modifier = Modifier
                                    .clickable(onClick = {
                                        Log.d("TAG", "회원가입하기")
                                        goSignUpActivity()
                                    })
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.ic_vector_right_arrow),
                                contentDescription = "right arrow"
                            )
                        }
                    }
                }
                showLoadingDialog(isLoading)
            }
        }
    })

}

@Composable
fun labeledCheckbox(label: String, checked: Boolean, onChecked: (Boolean) -> Unit) {
    Row(
        Modifier
            .height(24.dp)
            .clickable(onClick = { onChecked(!(checked)) }),
        verticalAlignment = CenterVertically
    ) {
        Icon(
            imageVector = if (checked) ImageVector.vectorResource(id = R.drawable.ic_success)
            else ImageVector.vectorResource(id = R.drawable.ic_unchecked),
            contentDescription = "checkbox",
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = label, style = MaterialTheme.typography.body2, color = Color.Black)
    }
}

@Composable
fun drawTitle(title: String, subTitle: String) {
    Column() {
        var title1 = title.substringBefore('\n')
        var title2 = title.substringAfter('\n')
        Text(text = title1, style = MaterialTheme.typography.h6)
        Text(text = title2, style = MaterialTheme.typography.h6, fontWeight = FontWeight(400))
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = subTitle,
            modifier = Modifier.height(22.dp),
            style = MaterialTheme.typography.subtitle1,
            fontSize = 14.sp
        )
    }
}


//
//@Composable
//@Preview
//fun defaultPreview() {
//    val viewModel = LoginViewModel(LoginRepositoryImpl(PipiApi))
//    ConstraintLayout(
//        modifier = Modifier
//            .fillMaxWidth()
//            .fillMaxHeight()
//            .padding(24.dp)
//    ) {
//        val (button, titlebox, textfeildbox) = createRefs()
//
//        Box(modifier = Modifier
//            .fillMaxWidth()
//            .constrainAs(titlebox) {
//                top.linkTo(parent.top)
//            }) {
//            drawTitle("트레이너님\n반갑습니다:)", "로그인 후 이용해주세요")
//        }
//
//        Box(modifier = Modifier
//            .fillMaxWidth()
//            .constrainAs(textfeildbox) {
//                top.linkTo(titlebox.bottom)
//            }) {
//            viewModel.id.value?.let {
////                drawIdInput("", {}, {},null)
//            }
//            viewModel.password.value?.let {
//                drawPwInput("", onChanged = {}, {})
//            }
//        }
//
//        Box(modifier = Modifier
//            .fillMaxWidth()
//            .constrainAs(button) {
//                bottom.linkTo(parent.bottom)
//            }) {
//            Components.drawDefaultButton(
//                color = colorResource(id = R.color.main_purple),
//                "로그인",
//                onClick = {},
//                isEnabled = true
//            )
//            Spacer(modifier = Modifier.height(20.dp))
//        }
//    }
//
//}