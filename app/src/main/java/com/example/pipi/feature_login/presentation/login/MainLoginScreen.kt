package com.example.pipi.feature_login.presentation.login

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import com.example.pipi.R
import com.example.pipi.global.constants.ui.*
import com.example.pipi.global.constants.ui.Colors.BLACK
import com.example.pipi.global.constants.ui.Colors.ERROR_RED
import com.example.pipi.global.constants.ui.Colors.GRAY2
import com.example.pipi.global.constants.ui.Colors.MAIN_PURPLE
import com.example.pipi.global.constants.ui.Components.showLoadingDialog
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.intellij.lang.annotations.JdkConstants
import timber.log.Timber

@ExperimentalAnimationApi
@Composable
fun MainLoginScreen(
    navController: NavController,
    viewModel: LoginViewModel,
    goSignUpActivity: () -> Unit
) {
    setProjectTheme(content = {
        Scaffold(topBar = { drawGoBackTopAppbar(false) }) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(24.dp)
            ) {
                val (button, titlebox, textfeildbox, checkbox, signupLoginBox) = createRefs()
                val id: String by viewModel.id.observeAsState("")
                val passwrod: String by viewModel.password.observeAsState("")
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(titlebox) {
                        top.linkTo(parent.top)
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
                            .height(96.dp)
                    ) {
                        drawIdInput(
                            id = id,
                            onChanged = { input -> viewModel.id.postValue(input) },
                            onCancelClicked = { viewModel.id.value = "" })
                        viewModel.password.value?.let {
                            drawPwInput(
                                passwrod,
                                onChanged = { input -> viewModel.password.postValue(input) },
                                onCancelClicked = {
                                    viewModel.password.value = ""
                                })
                        }
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
                        checked = viewModel.autoLogin
                    )
                    Spacer(modifier = Modifier.width(17.dp))
                    labeledCheckbox(
                        label = "휴대전화 번호 저장",
                        checked = viewModel.rememberPhoneNumber
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
                        onClick = { viewModel.login() }
                    )
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
                                    navController.navigate("findpassword")
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
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "아직 회원이 아니신가요",
                                style = MaterialTheme.typography.subtitle2,
                                color = GRAY2,
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
                // dialog 테스트용 버튼
//                Button(onClick = {
//                    Timber.d("button clicked")
//                    viewModel.isLoading.value = true
//                }, content = { Text(text = "showdialog") }
//                )
                showLoadingDialog(viewModel.isLoading)
            }
        }
    })

}

@Composable
fun labeledCheckbox(label: String, checked: MutableState<Boolean>, onChecked: (Boolean) -> Unit) {
    Row(
        Modifier
            .height(24.dp)
            .clickable(onClick = { onChecked(!(checked.value)) }),
        verticalAlignment = CenterVertically
    ) {
        Icon(
            imageVector = if (checked.value) ImageVector.vectorResource(id = R.drawable.ic_success)
            else ImageVector.vectorResource(id = R.drawable.ic_unchecked),
            contentDescription = "checkbox",
            modifier = Modifier.size(18.dp)
        )
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
            style = MaterialTheme.typography.subtitle1
        )
    }
}

//얘 색바꾸는 로직 밖으로 빼고 컬러 타입 error success active inactive 로 나눠서 설정하도록 하는 편이 좋을듯
//그리고 아이콘 영역을 colum으로 해서 아이톤 앞뒤로 몇개씩 넣을 수 있도록 만들어야할듯.
@ExperimentalAnimationApi
@Composable
fun drawIdInput(
    id: String,
    onChanged: (String) -> Unit,
    onCancelClicked: () -> Unit,
    endIcon: ImageVector = ImageVector.vectorResource(id = R.drawable.ic_delete)
) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .height(44.dp)
            .padding(top = 8.dp)
    )
    {
        Row(
            verticalAlignment = CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawLine(
                        color = if (id.isNotEmpty()) MAIN_PURPLE else GRAY2,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height)
                    )
                }
        ) {
            BasicTextField(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .padding(top = 8.dp, bottom = 8.dp)
                    .fillMaxWidth()
                    .align(CenterVertically),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                value = id,
                onValueChange = { if (it.length < 11) onChanged(it) },
                singleLine = true,
            )
//여기 기본 textfield 패딩으로 인한 아이콘 세로 정렬 부분 개선 필요
            AnimatedVisibility(
                visible = id.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(
                            onClick = { onCancelClicked() }
                        ),
                    imageVector = endIcon,
                    contentDescription = null
                )
            }
        }
        Box(
            modifier = Modifier
                .height(48.dp)
                .padding(top = 8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = if (id.isEmpty()) "휴대전화 번호를 입력해주세요.(-제외)" else "",
                color = GRAY2,
            )
        }
    }

//    Text(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable(
//                onClick = { onCancelClicked() }
//            )
//            .padding(top = 8.dp),
//        text = if (!id.isDigitsOnly()) "올바른 형식으로 입력해 주세요." else "",
//        color = ERROR_RED
//    )
}

@ExperimentalAnimationApi
@Composable
fun drawPwInput(
    password: String,
    onChanged: (String) -> Unit,
    onCancelClicked: () -> Unit,
    endIcon: ImageVector = ImageVector.vectorResource(id = R.drawable.ic_delete)
) {
    var passwordVisibility by remember { mutableStateOf(true) }
    val pwIcon: Int = if (passwordVisibility) R.drawable.ic_unshow else R.drawable.ic_show
    val focusManager = LocalFocusManager.current
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .height(44.dp)
            .padding(top = 8.dp)
    )
    {
        Row(
            verticalAlignment = CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawLine(
                        color = if (password.isNotEmpty()) MAIN_PURPLE else GRAY2,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height)
                    )
                },
        ) {
            BasicTextField(
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp)
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp)
                    .align(CenterVertically),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                value = password,
                onValueChange = { if (it.length < 11) onChanged(it) },
                singleLine = true,
            )
//여기 기본 textfield 패딩으로 인한 아이콘 세로 정렬 부분 개선 필요
            AnimatedVisibility(
                visible = password.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .padding(12.dp)
                        .clickable(
                            onClick = { onCancelClicked() }
                        )
                        .align(CenterVertically),
                    imageVector = ImageVector.vectorResource(id = pwIcon),
                    contentDescription = null
                )
            }
            AnimatedVisibility(
                visible = password.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(
                            onClick = { onCancelClicked() }
                        ),
                    imageVector = endIcon,
                    contentDescription = null
                )
            }
        }
        Box(
            modifier = Modifier
                .height(44.dp)
                .padding(top = 8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = if (password.isEmpty()) "비밀번호를 입력해주세요" else "",
                color = GRAY2,
            )
        }
    }

//    Text(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable(
//                onClick = { onCancelClicked() }
//            )
//            .padding(top = 8.dp),
//        text = if (!password.isDigitsOnly()) "올바른 형식으로 입력해 주세요." else "",
//        color = ERROR_RED
//    )
}
//    BasicTextField(
//        modifier = Modifier
//            .weight(1f)
//            .height(48.dp)
//            .padding(top = 16.dp, bottom = 8.dp)
//            .fillMaxWidth()
//            .align(CenterVertically),
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
//        value = id,
//        onValueChange = { if (it.length < 11) onChanged(it) },
//        singleLine = true,
//    )
//    BasicTextField(
//        value = password,
//        onValueChange = { if (it.length < 12) onChanged(it) },
//        modifier = Modifier
//            .weight(1f)
//            .height(48.dp)
//            .padding(top = 16.dp, bottom = 8.dp)
//            .fillMaxWidth()
//            .align(CenterVertically),
//        textStyle = MaterialTheme.typography.subtitle1,
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
//        colors = TextFieldDefaults.textFieldColors(
//            backgroundColor = Color.Transparent,
//            focusedIndicatorColor = colorResource(id = R.color.main_purple), //hide the indicator
//            unfocusedIndicatorColor = Color.LightGray,
//            cursorColor = Color.LightGray
//        )
//    )
//    Row() {
//        IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
//            Icon(
//                imageVector = ImageVector.vectorResource(id = pwIcon),
//                contentDescription = null,
//            )
//        }
//        IconButton(onClick = { onCancelClicked }) {
//            Icon(
//                imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete),
//                contentDescription = null,
//            )
//        }
//    }

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