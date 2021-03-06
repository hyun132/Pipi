package com.example.pipi.presentation.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.pipi.R
import com.example.pipi.global.constants.ui.Colors
import com.example.pipi.global.constants.ui.Components
import com.example.pipi.global.constants.ui.Components.DefaultTopAppbar
import com.example.pipi.global.constants.ui.Components.DrawStep
import com.example.pipi.global.constants.ui.Components.TextFieldWithErrorMessage
import com.example.pipi.global.constants.ui.setProjectTheme

@ExperimentalComposeUiApi
@Composable
fun SetNickNameScreen(
    navigate: () -> Unit,
    goBack: () -> Unit,
    viewModel: SignupViewModel,
    title:String,
    step: Pair<Int, Int> = Pair(3,1)
) {
    val nickName: String by viewModel.nickName.observeAsState("")
    setProjectTheme {
        Scaffold(topBar = {
            DefaultTopAppbar(
                title = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.subtitle1,
                        color = Colors.PRIMARY_TEXT,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                },
                navComponent = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_cancel),
                        tint = Color.Unspecified,
                        modifier = Modifier.clickable { goBack() },
                        contentDescription = "????????????"
                    )
                })
        }) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(24.dp)
            ) {
                val (content, button) = createRefs()
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(content) { top.linkTo(parent.top) }) {
                    DrawStep(step.first, step.second)
                    Spacer(modifier = Modifier.height(22.dp))
                    Text(
                        text = "???????????? ????????? ?????????.", style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight(400),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(36.dp))
                    TextFieldWithErrorMessage(
                        value = nickName,
                        onValueChange = { viewModel.nickName.value = it },
                        placeholder = "????????? (6??? - 12???) ??? ????????? ?????????",
                        // ??????????????? ?????????????????? ????????? ???????????? ???????????? ???! ???????????? ?????? state??? ??????.. viewModel??? ??????..?
                        errorMessage = "????????? (6??? - 12???) ??? ????????? ?????????",
                        rightComponent = {
                            if (viewModel.checkNickNameValid()) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_success),
                                    contentDescription = "success"
                                )
                            } else {
                            }
                        },
                        hideInputData = false,
                        title = "?????????"
                    )
//                    Components.drawDefaultButton(
//                        color = if (viewModel.checkNickNameValid()) Colors.MAIN_PURPLE else Colors.GRAY2,
//                        text = "??????",
//                        onClick = { navController.navigate("setPassword") },
//                        isEnabled = viewModel.checkNickNameValid()
//                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(button) { bottom.linkTo(parent.bottom) }) {
                    Components.drawDefaultButton(
                        color = if (viewModel.checkNickNameValid()) Colors.PRIMARY_BRAND else Colors.SECONDARY_TEXT_GHOST,
                        text = "??????",
                        onClick = { navigate() },
                        isEnabled = viewModel.checkNickNameValid()
                    )
                }
            }
        }
    }
}