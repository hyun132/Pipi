package com.example.pipi.presentation.login

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.pipi.R
import com.example.pipi.global.constants.ui.Colors.BRAND_SECOND
import com.example.pipi.global.constants.ui.Colors.PRIMARY_TEXT
import com.example.pipi.global.constants.ui.Colors.SECONDARY_TEXT_GHOST
import com.example.pipi.global.constants.ui.Components
import com.example.pipi.global.constants.ui.Components.TextFieldWithErrorMessage
import com.example.pipi.global.constants.ui.Components.showLoadingDialog
import com.example.pipi.global.constants.ui.setProjectTheme
import com.example.pipi.global.constants.utils.passwordErrorMessage
import com.example.pipi.global.constants.utils.passwordValidation
import com.example.pipi.global.constants.utils.phoneNumberErrorMessage
import com.example.pipi.global.constants.utils.phoneNumberValidation
import timber.log.Timber

@ExperimentalAnimationApi
@Composable
fun MainLoginScreen(
    viewModel: LoginViewModel,
    goSignUpActivity: () -> Unit,
    goMainActivity: () -> Unit,
    goFindPasswordActivity: () -> Unit
) {
//    viewModel.autoLogin()
    val id: String by viewModel.id
    val password: String by viewModel.password
    val isLoginSuccess: Boolean by viewModel.isLoginSuccess
    val isLoading: Boolean by viewModel.isLoading
    val errorMessage: String by viewModel.errorMessage
    val snackbarHostState = remember { SnackbarHostState() }
    setProjectTheme {
        Scaffold() {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(24.dp)
            ) {
                val (icon, button, titlebox, textfeildbox, checkbox, signupLoginBox, snackbar) = createRefs()

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = CenterHorizontally)
                    .constrainAs(icon) {
                        top.linkTo(parent.top)
                    }) {
                    DrawLoginTopAppbar()
                }

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(titlebox) {
                        top.linkTo(icon.bottom)
                    }) {
                    drawTitle("???????????????\n???????????????:)", "????????? ??? ??????????????????")
                }

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 49.dp)
                    .constrainAs(textfeildbox) {
                        top.linkTo(titlebox.bottom)
                    }) {
                    DrawLoginContents(id, password, errorMessage, viewModel)
                }
                //??????????????? ???????????? ??????????????????, ???????????? ?????????????????? sharedpreference??? ???????????? ?????? ???????????? ?????? ????????? ?????? ??????????????????.
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(checkbox) { top.linkTo(textfeildbox.bottom) }
                        .padding(top = 24.dp)
                ) {
                    DrawCheckBoxArea(viewModel)
                }

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .constrainAs(button) {
                        top.linkTo(checkbox.bottom)
                    }) {
                    Components.drawDefaultButton(
                        color = colorResource(id = R.color.main_purple),
                        text = "?????????",
                        isEnabled = phoneNumberValidation(id) && passwordValidation(password),
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
                            text = "??????????????????",
                            Modifier
                                .clickable(onClick = {
                                    Timber.d("???????????? ??????")
                                    goFindPasswordActivity()
                                })
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally),
                            style = MaterialTheme.typography.h5,
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
                                text = "?????? ????????? ???????????????",
                                style = MaterialTheme.typography.h5,
                                color = SECONDARY_TEXT_GHOST,
                                fontSize = 12.sp,
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "????????????",
                                style = MaterialTheme.typography.h5,
                                color = MaterialTheme.colors.primary,
                                fontSize = 12.sp,
                                modifier = Modifier
                                    .clickable(onClick = {
                                        Timber.d("??????????????????")
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
                SnackbarHost(hostState = snackbarHostState, snackbar = { snackbarData ->
                    if (snackbarData.message.isNotEmpty()) {
                        Text(
                            text = snackbarData.message,
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colors.onBackground,
                            style = MaterialTheme.typography.subtitle1
                        )
                    }
                })
            }
        }
    }

}

@Composable
fun DrawCheckBoxArea(viewModel: LoginViewModel) {
    labeledCheckbox(
        label = {
            Text(
                text = "???????????????",
                style = MaterialTheme.typography.subtitle1,
                color = PRIMARY_TEXT
            )
        },
        onChecked = { ischecked -> viewModel.autoLogin.value = ischecked },
        checked = viewModel.autoLogin.value,
        icon = { checked ->
            Icon(
                imageVector = ImageVector.vectorResource(id = if (checked) R.drawable.ic_checked else R.drawable.ic_unchecked),
                contentDescription = "checkbox",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
            )
        }
    )
    Spacer(modifier = Modifier.width(17.dp))
    labeledCheckbox(
        label = {
            Text(
                text = "???????????? ?????? ??????",
                style = MaterialTheme.typography.subtitle1,
                color = PRIMARY_TEXT
            )
        },
        checked = viewModel.rememberPhoneNumber.value,
        icon = { checked ->
            Icon(
                imageVector = ImageVector.vectorResource(id = if (checked) R.drawable.ic_checked else R.drawable.ic_unchecked),
                contentDescription = "checkbox",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
            )
        }
    ) { ischecked ->
        viewModel.rememberPhoneNumber.value = ischecked
    }
}

@Composable
fun DrawLoginContents(
    id: String,
    password: String,
    errorMessage: String,
    viewModel: LoginViewModel
) {
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        var passwordVisibility by remember { mutableStateOf(true) }
        Components.TextFieldWithErrorMessage(
            value = id,
            onValueChange = { input -> viewModel.id.value = input },
            placeholder = "???????????? ????????? ????????? ?????????(-??????)",
            errorMessage = phoneNumberErrorMessage(id).let { if (it.isEmpty()) errorMessage else it },
            rightComponent = {
                if (id.isNotEmpty()) {
                    Icon(
                        modifier = Modifier
                            .clickable(
                                onClick = { viewModel.id.value = "" },
                            ),
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                } else {
                }
            },
            hideInputData = false,
            title = "",
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextFieldWithErrorMessage(
            value = password,
            onValueChange = { input -> viewModel.password.value = input },
            placeholder = "??????????????? ????????? ?????????",
            errorMessage = passwordErrorMessage(password),
            rightComponent =
            {
                Row(modifier = Modifier.wrapContentSize()) {
                    Icon(
                        modifier = Modifier
                            .clickable(
                                onClick = {
                                    passwordVisibility = !passwordVisibility
                                }
                            ),
                        imageVector = if (passwordVisibility) ImageVector.vectorResource(
                            id = R.drawable.ic_unshow
                        ) else ImageVector.vectorResource(id = R.drawable.ic_show),
                        contentDescription = "???????????? ?????????"
                    )
                    if (password.isNotEmpty()) {
                        Icon(
                            modifier = Modifier
                                .clickable(
                                    onClick = { viewModel.password.value = "" }
                                ),
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete),
                            contentDescription = "?????????",
                            tint = Color.Unspecified
                        )
                    } else {
                    }
                }
            },
            hideInputData = passwordVisibility
        )
    }
}

@Composable
fun DrawLoginTopAppbar() {
    Column(Modifier.padding(top = 10.dp, bottom = 51.dp)) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_pipi),
            contentDescription = "pipi",
            modifier = Modifier
                .height(22.dp)
                .width(42.dp),
            tint = BRAND_SECOND
        )
    }
}

@Composable
fun labeledCheckbox(
    label: @Composable () -> Unit,
    checked: Boolean,
    icon: @Composable (Boolean) -> Unit,
    onChecked: (Boolean) -> Unit
) {
    Row(
        Modifier
            .height(24.dp)
            .clickable(onClick = { onChecked(!(checked)) }),
        verticalAlignment = CenterVertically
    ) {
        icon(checked)
        Spacer(modifier = Modifier.width(4.dp))
        label()
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
//            drawTitle("???????????????\n???????????????:)", "????????? ??? ??????????????????")
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
//                "?????????",
//                onClick = {},
//                isEnabled = true
//            )
//            Spacer(modifier = Modifier.height(20.dp))
//        }
//    }
//
//}