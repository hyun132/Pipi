package com.example.pipi.presentation.duplicated

import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.pipi.R
import com.example.pipi.global.constants.ui.Colors
import com.example.pipi.global.constants.ui.Colors.ALERT
import com.example.pipi.global.constants.ui.Colors.BRAND_SECOND
import com.example.pipi.global.constants.ui.Colors.PRIMARY_BRAND
import com.example.pipi.global.constants.ui.Colors.PRIMARY_TEXT
import com.example.pipi.global.constants.ui.Colors.SECONDARY_TEXT_GHOST
import com.example.pipi.global.constants.ui.Components
import com.example.pipi.global.constants.ui.Components.DrawStep
import com.example.pipi.global.constants.ui.Components.TextFieldWithErrorMessage
import com.example.pipi.global.constants.ui.Components.drawDefaultButton
import com.example.pipi.global.constants.ui.setProjectTheme
import com.example.pipi.global.constants.utils.phoneNumberErrorMessage
import com.example.pipi.global.constants.utils.phoneNumberValidation
import timber.log.Timber

@ExperimentalAnimationApi
@Composable
fun PhoneAuthScreen(
    navigate: () -> Unit,
    backToMain: () -> Unit,
    title: String,
    viewModel: PhoneAuthViewModel,
    snackBarHostState: MutableState<SnackbarHostState>,
    lifecycleScope: LifecycleCoroutineScope,
    step: Pair<Int, Int> = Pair(3,1)
) {
    val phoneNumber: String by viewModel.phoneNumber
    val authNumber: String by viewModel.authNumber.observeAsState("")
    val errorMessage: String by viewModel.errorMessage
    val timerStarted by viewModel.timerStarted
    val formattedTime by viewModel.formattedTime.observeAsState("")
    val isbuttonActive by viewModel.phoneAuthSuccess.observeAsState(false)


    setProjectTheme {
        Scaffold(topBar = {
            Components.DefaultTopAppbar(
                title = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.subtitle1,
                        color = PRIMARY_TEXT,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                },
                navComponent = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_cancel),
                        tint = Color.Unspecified,
                        modifier = Modifier.clickable {
                            viewModel.stopCount(); backToMain()
                        },
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
                val (contentx, button, snackbar) = createRefs()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(contentx) { top.linkTo(parent.top) },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    /**
                     * TODO : step ????????? ??????????????? ???????????? ????????????
                     */
                    DrawStep(step.first, step.second)
                    Spacer(modifier = Modifier.height(22.dp))
                    Text(
                        text = "???????????? ?????? ????????? ????????????.", style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight(400),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(36.dp))
                    TextFieldWithErrorMessage(
                        value = phoneNumber,
                        onValueChange = { input -> viewModel.setPhoneNumber(input) },
                        placeholder = "???????????? ??????(-??????)",
                        errorMessage = phoneNumberErrorMessage(phoneNumber),
                        rightComponent = {
                            Row(verticalAlignment = CenterVertically) {
                                if (timerStarted) {
                                    Text(
                                        text = formattedTime,
                                        style = MaterialTheme.typography.h5,
                                        fontSize = 11.sp,
                                        color = ALERT
                                    )
                                }
                                TextButton(onClick = {
                                    viewModel.requestSendAuthMessage()
                                }, modifier = Modifier
                                    .height(32.dp)
                                    .width(86.dp)
                                    .padding(0.dp)
                                    .background(if (phoneNumberValidation(phoneNumber)) BRAND_SECOND else SECONDARY_TEXT_GHOST),
                                    shape = RoundedCornerShape(2.dp),
                                    content = {
                                        Text(
                                            text = "??????????????????",
                                            style = MaterialTheme.typography.h5,
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
                        title = "???????????? ??????"
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    TextFieldWithErrorMessage(
                        value = authNumber,
                        onValueChange = { input -> viewModel.setAuthNumber(input) },
                        placeholder = "???????????? 6??????",
                        errorMessage = "",
                        rightComponent = {
                            TextButton(onClick = {
                                viewModel.checkAuthSuccess()
                            }, modifier = Modifier
                                .height(32.dp)
                                .width(86.dp)
                                .padding(0.dp)
                                /**
                                 * TODO : ?????? ???????????? ???????????????.
                                 */
                                .background(BRAND_SECOND),
                                shape = RoundedCornerShape(2.dp),
                                content = {
                                    Text(
                                        text = "??????",
                                        style = MaterialTheme.typography.h5,
                                        fontSize = 11.sp,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth(),
                                        color = Color.White
                                    )
                                })
                        },
                        hideInputData = false,
                        title = "????????????"
                    )
                }

                Box(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(button) { bottom.linkTo(parent.bottom) }) {
                    drawDefaultButton(
                        color = if (isbuttonActive) BRAND_SECOND else SECONDARY_TEXT_GHOST,
                        text = "??????",
                        onClick = {
                            navigate()
                            viewModel.timerStarted.value = false
                        },
                        isEnabled = isbuttonActive
                    )
                }
                viewModel.countTime()

            }
        }
        if (errorMessage.isNotEmpty()) {
            lifecycleScope.launchWhenResumed {
                if (errorMessage.isNotEmpty()) {
                    Timber.d("snack bar is called! in phone auth screen!!!")
                    snackBarHostState.value.showSnackbar(
                        message = errorMessage,
                        actionLabel = "Hide",
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }
}