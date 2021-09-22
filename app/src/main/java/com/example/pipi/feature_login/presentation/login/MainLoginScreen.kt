package com.example.pipi.feature_login.presentation.login

import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.pipi.R
import com.example.pipi.global.constants.ui.Components

@Composable
fun MainLoginScreen(
    navController: NavController,
    viewModel: LoginViewModel
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(24.dp)
    ) {
        val (button, titlebox, textfeildbox) = createRefs()
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
            .padding(top = 40.dp)
            .constrainAs(textfeildbox) {
                top.linkTo(titlebox.bottom)
            }) {
            Column(Modifier.fillMaxWidth()) {
                drawIdInput(
                    id,
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
            Components.drawDefaultButton(color = colorResource(id = R.color.main_purple), "로그인") {}
            Spacer(modifier = Modifier.height(20.dp))
        }

        //자동로그인 체크박스 넣어야하는데, 체크되면 로그인성공시 sharedpreference에 저장하고 체크 해제하는 즉시 저장된 내용 삭제하는걸로.
    }

}

@Composable
fun drawTitle(title: String, subTitle: String) {
    Column() {
        Text(text = title, fontSize = 18.sp, lineHeight = 36.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = subTitle, fontSize = 16.sp, modifier = Modifier.height(22.dp))
    }
}

@Composable
fun drawIdInput(id: String, onChanged: (String) -> Unit, onCancelClicked: () -> Unit) {
    TextField(
        value = id,
        onValueChange = { onChanged(it) /*id = it;onChanged(id)*/ },
        placeholder = { Text(text = "휴대전화 번호를 입력해주세요.(-제외)") },
        modifier = Modifier
            .height(44.dp)
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(0.dp),
        trailingIcon = {
            IconButton(onClick = { onCancelClicked }) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
    )
}

@Composable
fun drawPwInput(password: String, onChanged: (String) -> Unit, onCancelClicked: (String) -> Unit) {
    var passwordVisibility by remember { mutableStateOf(true) }
    val pwIcon = if (passwordVisibility) Icons.Default.Person else Icons.Default.Home

    TextField(
        value = password,
        onValueChange = { onChanged(it) },
        placeholder = { Text(text = "휴대전화 번호를 입력해주세요.(-제외)") },
        modifier = Modifier
            .height(44.dp)
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(0.dp),
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
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation()
    )
}

//@Composable
//fun drawPwInput(onChanged: (String) -> Unit) {
//    var input by remember { mutableStateOf("") }
//    var passwordVisibility by remember { mutableStateOf(true) }
//
//    val pwIcon = if (passwordVisibility) Icons.Default.Person else Icons.Default.Home
//
//    TextField(
//        value = input,
//        onValueChange = { input = it;onChanged(input) },
//        placeholder = { Text(text = "휴대전화 번호를 입력해주세요.(-제외)") },
//        modifier = Modifier
//            .height(44.dp)
//            .fillMaxWidth()
//            .background(Color.Transparent)
//            .padding(0.dp),
//        trailingIcon = {
//            Row() {
//                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
//                    Icon(
//                        imageVector = pwIcon,
//                        contentDescription = null,
//                    )
//                }
//                IconButton(onClick = { input = "" }) {
//                    Icon(
//                        imageVector = Icons.Default.Clear,
//                        contentDescription = null,
//                    )
//                }
//            }
//        },
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation()
//    )
//}
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
                drawIdInput("", {}, {})
            }
            viewModel.password.value?.let {
                drawPwInput("",onChanged = {}, {})
            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .constrainAs(button) {
                bottom.linkTo(parent.bottom)
            }) {
            Components.drawDefaultButton(color = colorResource(id = R.color.main_purple), "로그인") {}
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

}