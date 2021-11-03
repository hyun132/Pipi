package com.example.pipi.global.constants.ui

import android.graphics.Color.BLACK
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.pipi.R
import com.example.pipi.global.constants.Fonts
import com.example.pipi.global.constants.ui.Colors.FONT_GRAY
import com.example.pipi.global.constants.ui.Colors.SURFACE

object Components {
    @Composable
    fun drawDefaultButton(color: Color, text: String, onClick: () -> Unit, isEnabled: Boolean) {
        Button(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .fillMaxWidth()
                .height(52.dp),
            onClick = onClick,
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = color,
            ),
            enabled = isEnabled
        ) {
            Text(
                fontSize = 16.sp,
                text = text,
                color = Color.White,
                fontFamily = Fonts.appFontFamily,
                fontWeight = FontWeight.W700
            )
        }
    }

    @Composable
    fun drawTextTitleTopAppbar(title: String, goBack: () -> Unit) {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier
                        .padding(end = 76.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            navigationIcon = { //navigationIcon이 뒤로가기, actions는 액션메뉴
                IconButton(
                    onClick = {
                        Log.d("TAG", "goBackClicked!")
                        goBack()
                    },
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "go Back",
                        )
                    },
                )
            },
            elevation = 0.dp, backgroundColor = SURFACE,
        )
    }

    @Composable
    fun showLoadingDialog(showDialog: Boolean) {
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { },
                buttons = {
                    //여기 로딩이들어가야하지만 일단 아무거나로
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "loading"
                    )
                },
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = false
                ),
            )
        }

    }


    @Composable
    fun InputTextField(
        input: String,
        onChanged: (String) -> Unit,
        hint: String,
        errorMessage: String?,
        rightComponent: @Composable () -> Unit,
        hideInputData: Boolean,
        title: String
    ) {
        if (!title.isNullOrEmpty()){
            Text(text = title,style = MaterialTheme.typography.subtitle2,fontSize = 12.sp,modifier = Modifier.fillMaxWidth(),color = FONT_GRAY)
        }
        Box() {
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(top = 8.dp)
                .drawBehind {
                    //에러일때 색깔도
                    drawLine(
                        color = if (errorMessage.isNullOrEmpty()) {
                            Colors.GRAY2
                        } else Colors.ERROR_RED,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height)
                    )
                }) {
                BasicTextField(
                    value = input,
                    onValueChange = { onChanged(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F)
                        .padding(top = 8.dp),
                    visualTransformation = if (hideInputData) PasswordVisualTransformation() else VisualTransformation.None
                )
                rightComponent()
            }
            Box(
                modifier = Modifier
                    .height(48.dp).padding(top = 4.dp), contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = if (input.isEmpty()) hint else "",
                    color = Colors.GRAY2,
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight(400),
                )
            }
        }
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = Colors.ERROR_RED,
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight(400),
                modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }
    }
}