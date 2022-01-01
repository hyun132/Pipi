package com.example.pipi.presentation.signup

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.pipi.R
import com.example.pipi.global.constants.ui.Colors.PRIMARY_BRAND
import com.example.pipi.global.constants.ui.Colors.PRIMARY_TEXT
import com.example.pipi.global.constants.ui.Colors.SECONDARY_TEXT_GHOST
import com.example.pipi.global.constants.ui.Components.DefaultTopAppbar
import com.example.pipi.global.constants.ui.Components.drawDefaultButton
import com.example.pipi.global.constants.ui.setProjectTheme
import com.example.pipi.presentation.login.labeledCheckbox

@ExperimentalAnimationApi
@Composable
fun TosScreen(
    navigate: () -> Unit,
    viewModel: SignupViewModel,
    backToMain: () -> Unit
) {
    var agreeAll by remember { mutableStateOf(false) }

    val agreementComponent = listOf<AgreementItem>(
        AgreementItem(title = "피피 이용 약관", content = "", isNecessary = true),
        AgreementItem(title = "개인정보 처리방침", content = "", isNecessary = true)
    )

    setProjectTheme {
        Scaffold(topBar = {
            DrawTosTopAppbar(backToMain)
        }) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
                val (contentx, button, snackbar) = createRefs()

                Column() {
                    labeledCheckbox(
                        label = {
                            Text(
                                text = "전체동의",
                                style = MaterialTheme.typography.subtitle2,
                                color = PRIMARY_TEXT
                            )
                        },
                        onChecked = { ischecked ->
                            agreeAll = ischecked
                            agreementComponent.forEach {
                                it.checked.value = agreeAll
                            }
                        },
                        checked = agreeAll,
                        icon = { checked ->
                            Icon(
                                imageVector = ImageVector.vectorResource(id = if (checked) R.drawable.ic_checked else R.drawable.ic_unchecked),
                                contentDescription = "checkbox",
                                tint = Color.Unspecified,
                                modifier = Modifier
                                    .size(25.dp)
                                    .clip(CircleShape)
                            )
                        }
                    )
                    agreementComponent.forEach { item ->
                        Spacer(modifier = Modifier.height(16.dp))
                        labeledCheckbox(
                            label = {
                                Text(
                                    text = item.title,
                                    style = MaterialTheme.typography.body2,
                                    color = PRIMARY_TEXT
                                )
                            },
                            checked = item.checked.value,
                            onChecked = { _ ->
                                item.checked.value = !item.checked.value
                                if (!item.checked.value) agreeAll = false
                            },
                            icon = { checked ->
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = if (checked) R.drawable.ic_checked else R.drawable.ic_unchecked),
                                    contentDescription = "checkbox",
                                    tint = Color.Unspecified,
                                    modifier = Modifier
                                        .size(25.dp)
                                        .clip(CircleShape)
                                )
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(28.dp))


                Box(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(button) { bottom.linkTo(parent.bottom) }) {
                    drawDefaultButton(
                        color = if (checkAllNecessariesChecked(agreementComponent)) PRIMARY_BRAND else SECONDARY_TEXT_GHOST,
                        text = "다음",
                        onClick = { navigate() },
                        isEnabled = checkAllNecessariesChecked(agreementComponent)
                    )
                }
            }
        }
    }
}

@Composable
fun DrawTosTopAppbar(navigate: () -> Unit) {
    DefaultTopAppbar(
        title = {
            Text(
                text = "피피 약관 동의",
                style = MaterialTheme.typography.subtitle1,
                color = PRIMARY_TEXT,
                textAlign = TextAlign.Center
            )
        },
        navComponent = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_cancel),
                tint = Color.Unspecified,
                modifier = Modifier.clickable { navigate() },
                contentDescription = "뒤로가기"
            )
        })
}

fun checkAllNecessariesChecked(agreementComponent: List<AgreementItem>): Boolean {
    agreementComponent.forEach {
        if (it.isNecessary != true and it.checked.value) return false
    }
    return true
}

data class AgreementItem(
    val title: String,
    val content: String,
    val isNecessary: Boolean,
    var checked: MutableState<Boolean> = mutableStateOf(false)
)

//
//@Composable
//fun showSnackbar(showDialog: MutableState<Boolean>) {
//    //스낵바는 따로 함수로 빼고, base에 넣을지 고민해볼것.
//    if (showDialog.value == true) {
//        Snackbar(
//            action = {
//                TextButton(
//                    content = {
//                        Text(text = "확인", color = MAIN_PURPLE)
//                    },
//                    onClick = { Timber.d("clicked") }
//                )
//            },
//            backgroundColor = DARK_GRAY
//        ) {
//            Text(
//                text = "인증번호를 문자로 전송하였습니다.",
//                style = MaterialTheme.typography.subtitle1,
//                fontSize = 11.sp,
//                color = Color.White
//            )
//        }
//    }
//}
