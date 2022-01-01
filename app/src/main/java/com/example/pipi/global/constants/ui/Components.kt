package com.example.pipi.global.constants.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.pipi.R
import com.example.pipi.global.constants.Fonts
import com.example.pipi.global.constants.ui.Colors.ALERT
import com.example.pipi.global.constants.ui.Colors.FONT_GRAY
import com.example.pipi.global.constants.ui.Colors.SECONDARY_TEXT_GHOST

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
    fun DefaultTopAppbar(
        title: @Composable () -> Unit = {},
        navComponent: @Composable () -> Unit = {},
        optionComponent: @Composable () -> Unit = {}
    ) {
        ConstraintLayout(
            Modifier
                .fillMaxWidth()
                .height(69.dp)
                .padding(start = 16.dp, end = 16.dp),
        ) {
            val (nav, title, option) = createRefs()

            Box(modifier = Modifier
                .constrainAs(nav) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }) { navComponent() }
            Box(modifier = Modifier
                .constrainAs(title) {
                    linkTo(
                        parent.start,
                        parent.top,
                        parent.end,
                        parent.bottom,
                        horizontalBias = 0.5F
                    )
                }
                .widthIn(max = 250.dp, min = 100.dp), contentAlignment = Center) {
                title()
            }
            Box(modifier = Modifier
                .constrainAs(option) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }) { optionComponent() }

        }
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
    fun TextFieldWithErrorMessage(
        value: String,
        onValueChange: (String) -> Unit,
        placeholder: String = "",
        errorMessage: String = "",
        rightComponent: @Composable () -> Unit = {},
        leftComponent: @Composable () -> Unit = {},
        hideInputData: Boolean = false,
        title: String = "",
    ) {
        Column(Modifier.fillMaxWidth()) {
            if (title.isNotEmpty()) Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.subtitle2,
                fontSize = 12.sp,
                color = SECONDARY_TEXT_GHOST
            )
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .drawBehind {
                        val y = size.height
                        drawLine(
                            if (errorMessage.isEmpty()) SECONDARY_TEXT_GHOST else ALERT,
                            Offset(0f, y),
                            Offset(size.width, y),
                            3F
                        )
                    }
                    .fillMaxWidth(),
                decorationBox = { innerTextField ->
                    if (value.isEmpty() && value.isBlank()) Box(
                        Modifier
                            .padding(8.dp)
                    ) {
                        Text(
                            text = placeholder,
                            color = SECONDARY_TEXT_GHOST,
                            style = MaterialTheme.typography.subtitle1
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        leftComponent()
                        Box(
                            Modifier
                                .weight(1f)
                                .padding(8.dp)
                        ) {
                            innerTextField()
                        }
                        rightComponent()
                    }
                },
                visualTransformation = if (hideInputData) PasswordVisualTransformation() else VisualTransformation.None,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (errorMessage.isNotEmpty()) Text(
                text = errorMessage,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.subtitle2,
                fontSize = 12.sp,
                color = ALERT
            )
        }
    }

    @Composable
    fun InputTextField(
        input: String,
        onChanged: (String) -> Unit,
        hint: String = "",
        errorMessage: String?,
        rightComponent: @Composable () -> Unit = {},
        hideInputData: Boolean,
        title: String? = null
    ) {
        if (!title.isNullOrEmpty()) {
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle2,
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth(),
                color = FONT_GRAY
            )
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
                            Colors.SECONDARY_TEXT_GHOST
                        } else Colors.ALERT,
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
                    .height(48.dp)
                    .padding(top = 4.dp), contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = if (input.isEmpty()) hint else "",
                    color = Colors.SECONDARY_TEXT_GHOST,
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight(400),
                )
            }
        }
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = Colors.ALERT,
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight(400),
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }
    }

    /**
     * [total]전체 step수 [current]현재 단계
     * 단계는 1부터 시작
     */
    @Composable
    fun DrawStep(total: Int = 3, current: Int = 1) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            LazyRow(content = {
                items(total) { i ->
                    if (i != 0) Spacer(
                        modifier = Modifier
                            .width(23.dp)
                            .background(FONT_GRAY)
                            .height(1.dp)
                    )
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(24.dp)
                            .background(if (i == current) Colors.BRAND_SECOND else FONT_GRAY)
                            .align(CenterVertically),
                        contentAlignment = Center
                    ) {
                        Text(
                            text = (i + 1).toString(),
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            color = Color.White,
                            style = MaterialTheme.typography.subtitle1
                        )
                    }
                }
            }, verticalAlignment = Alignment.CenterVertically)
        }
    }
}