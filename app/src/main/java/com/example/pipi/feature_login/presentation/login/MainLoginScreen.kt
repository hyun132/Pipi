package com.example.pipi.feature_lo

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import com.example.pipi.R
import com.example.pipi.feature_login.presentation.login.LoginViewModel
import com.example.pipi.global.constants.ui.*

@ExperimentalAnimationApi
@Composable
fun MainLoginScreen(
    navController: NavController,
    viewModel: LoginViewModel
) {
    setProjectTheme(content = {
        Scaffold(topBar = { drawGoBackTopAppbar() }) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(24.dp)
            ) {
                val (button, titlebox, textfeildbox, checkbox, signbutton) = createRefs()
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
                    .padding(top = 8.dp)
                    .constrainAs(textfeildbox) {
                        top.linkTo(titlebox.bottom)
                    }) {
                    Column(Modifier.fillMaxWidth()) {
                        drawIdInput(
                            id = id,
                            onChanged = { input -> viewModel.id.postValue(input) },
                            onCancelClicked = { viewModel.id.value = "" })
                        Spacer(modifier = Modifier.height(16.dp))
                        viewModel.password.value?.let {
                            drawPwInput(
                                passwrod,
                                onChanged = { input -> viewModel.id.postValue(input) },
                                onCancelClicked = {
                                    viewModel.password.value = ""
                                })
                        }
                    }
                }

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(button) {
                        bottom.linkTo(parent.bottom)
                    }) {
                    Components.drawDefaultButton(
                        color = colorResource(id = R.color.main_purple),
                        text = "로그인",
                        isEnabled = (viewModel.password.value?.length ?: 0) >= 6,
                        onClick = {}
                    )
                    Spacer(modifier = Modifier.height(20.dp))
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
                    labeledCheckbox(
                        label = "휴대전화 번호 저장",
                        checked = viewModel.rememberPhoneNumber
                    ) { ischecked ->
                        viewModel.rememberPhoneNumber.value = ischecked
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 34.dp)
                        .constrainAs(signbutton) { top.linkTo(checkbox.bottom) }) {
                    Text(text = "아이디 찾기", Modifier.clickable(onClick = { Log.d("TAG", "비밀번호 찾기") }))
                    Spacer(modifier = Modifier.weight(1F))
                    Text(
                        text = "회원가입",
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .clickable(onClick = { Log.d("TAG", "회원가입하기") })
                    )
                }
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
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked.value,
            onCheckedChange = { it -> onChecked(it); Log.d("TAG", checked.toString()) },
            enabled = true,
            modifier = Modifier
                .clip(CircleShape)
                .size(24.dp)
                .padding(5.dp)
                .clip(CircleShape)
                .background(if (checked.value) MaterialTheme.colors.primary else Color.LightGray)
        )
        Text(text = label, style = MaterialTheme.typography.body2, color = Color.Black)
    }
}

@Composable
fun drawTitle(title: String, subTitle: String) {
    Column() {
        Text(text = title, style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = subTitle,
            modifier = Modifier.height(22.dp),
            style = MaterialTheme.typography.subtitle1
        )
    }
}

//@Composable
//fun drawIdInput(id: String, onChanged: (String) -> Unit, onCancelClicked: () -> Unit) {
//    TextField(
//        value = id,
//        onValueChange = { if (it.length < 11) onChanged(it) },
//        placeholder = { Text(text = "휴대전화 번호를 입력해주세요.(-제외)") },
//        modifier = Modifier
//            .height(56.dp)
//            .fillMaxWidth(),
//        shape = MaterialTheme.shapes.large.copy(bottomEnd = ZeroCornerSize, bottomStart = ZeroCornerSize),
//        trailingIcon = {
//            IconButton(onClick = { onCancelClicked }) {
//                Icon(
//                    imageVector = Icons.Default.Clear,
//                    contentDescription = null,
//                )
//            }
//        },
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
//        colors = TextFieldDefaults.textFieldColors(
//            backgroundColor = Color.Transparent,
//            focusedIndicatorColor = colorResource(id = R.color.main_purple), //hide the indicator
//            unfocusedIndicatorColor = Color.LightGray,
//            cursorColor = Color.LightGray
//        )
//    )
//}

//얘 색바꾸는 로직 밖으로 빼고 컬러 타입 error success active inactive 로 나눠서 설정하도록 하는 편이 좋을듯
//그리고 아이콘 영역을 colum으로 해서 아이톤 앞뒤로 몇개씩 넣을 수 있도록 만들어야할듯.
@ExperimentalAnimationApi
@Composable
fun drawIdInput(
    id: String,
    onChanged: (String) -> Unit,
    onCancelClicked: () -> Unit,
    endIcon: ImageVector = Icons.Default.Clear
) {

    Box(
        contentAlignment = Alignment.CenterStart
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
                    .padding(top = 16.dp, bottom = 8.dp)
                    .background(ERROR_RED)
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
                Image(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(
                            onClick = { onCancelClicked() }
                        ),
                    imageVector = endIcon,
                    contentDescription = null,
                )
            }
        }
        Box(
            modifier = Modifier
                .height(48.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = if (id.isEmpty()) "휴대전화 번호를 입력해주세요.(-제외)" else "",
                color = GRAY2,
            )
        }
    }

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = { onCancelClicked() }
            )
            .padding(top = 8.dp),
        text = if (!id.isDigitsOnly()) "올바른 형식으로 입력해 주세요." else "",
        color = ERROR_RED
    )
}

@Composable
fun drawPwInput(password: String, onChanged: (String) -> Unit, onCancelClicked: (String) -> Unit) {
    var passwordVisibility by remember { mutableStateOf(true) }
    val pwIcon = if (passwordVisibility) Icons.Default.Person else Icons.Default.Home

    TextField(
        value = password,
        onValueChange = { if (it.length < 12) onChanged(it) },
        placeholder = { Text(text = "비밀번호를 입력해주세요.") },
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth(),
        trailingIcon = {
            Row() {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(
                        imageVector = pwIcon,
                        contentDescription = null,
                    )
                }
                IconButton(onClick = { onCancelClicked }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                    )
                }
            }
        },
        textStyle = MaterialTheme.typography.subtitle1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = colorResource(id = R.color.main_purple), //hide the indicator
            unfocusedIndicatorColor = Color.LightGray,
            cursorColor = Color.LightGray
        )
    )
}

@Composable
@Preview
fun defaultPreview() {
    val viewModel = LoginViewModel()
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(24.dp)
    ) {
        val (button, titlebox, textfeildbox) = createRefs()

        Box(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(titlebox) {
                top.linkTo(parent.top)
            }) {
            drawTitle("트레이너님\n반갑습니다:)", "로그인 후 이용해주세요")
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(textfeildbox) {
                top.linkTo(titlebox.bottom)
            }) {
            viewModel.id.value?.let {
//                drawIdInput("", {}, {},null)
            }
            viewModel.password.value?.let {
                drawPwInput("", onChanged = {}, {})
            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(button) {
                bottom.linkTo(parent.bottom)
            }) {
            Components.drawDefaultButton(
                color = colorResource(id = R.color.main_purple),
                "로그인",
                onClick = {},
                isEnabled = true
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

}