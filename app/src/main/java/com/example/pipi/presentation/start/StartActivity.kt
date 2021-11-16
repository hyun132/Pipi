package com.example.pipi.presentation.start

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pipi.R
import com.example.pipi.global.constants.ui.Components.drawDefaultButton
import com.example.pipi.presentation.login.LoginActivity
import com.example.pipi.global.constants.ui.Colors.MAIN_PURPLE

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Pipi)
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(63.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_pipi),
                        contentDescription = "",
                        tint = Color.Black,
                        modifier = Modifier
                            .width(74.dp)
                            .height(37.dp)
                    )
                    Text(
                        text = "로",
                        style = MaterialTheme.typography.h5,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W700
                    )
                }
                Spacer(modifier = Modifier.height(26.dp))
                Text(
                    text = "모두의 건강을",
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.h5,
                    fontSize = 20.sp,
                    textAlign = TextAlign.End, fontWeight = FontWeight.W700

                )
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Text(
                        text = "보다",
                        style = MaterialTheme.typography.h5,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W700
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "쉽게 섬세하게 철저하게",
                        style = MaterialTheme.typography.h5,
                        fontSize = 20.sp,
                        color = MAIN_PURPLE, fontWeight = FontWeight.W700
                    )
                }
                Spacer(modifier = Modifier.height(38.dp))
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_onboarding),
                    contentDescription = "onboarding",
                    modifier = Modifier
//                        .width(231.dp)
//                        .height(299.dp)
                        .align(CenterHorizontally)
                        .weight(1F)
                )
                Spacer(modifier = Modifier.height(38.dp))
                drawDefaultButton(
                    colorResource(R.color.main_purple),
                    text = "트레이너로 시작하기",
                    onClick = { moveMainActivity() },
                    isEnabled = true
                )
                Spacer(modifier = Modifier.height(15.dp))
                drawDefaultButton(
                    Color.Black,
                    "일반회원으로 시작하기",
                    isEnabled = true,
                    onClick = { moveMainActivity() })
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }

    private fun moveMainActivity() {
        //do something
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}

@Composable
fun DrawOnBoardingImage(modifier: Modifier) {
    Image(
        imageVector = ImageVector.vectorResource(R.drawable.ic_onboarding),
        contentDescription = "onbarding Image",
        modifier
    )
}

//@Composable
//fun drawOnBoardingText(plainText: String, boldText: String) {
//    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
//        Text(text = plainText, style = MaterialTheme.typography.subtitle1)
//        Text(
//            text = boldText,
//            style = MaterialTheme.typography.subtitle2,
//            fontWeight = FontWeight(700)
//        )
//    }
//}
//
//@Preview
//@Composable
//fun DefaultPreview() {
//    Spacer(modifier = Modifier.height(16.dp))
//    Column(
//        modifier = Modifier
//            .padding(16.dp)
//            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        drawOnBoardingImage(
//            Modifier
//                .height(168.dp)
//                .width(168.dp)
//                .fillMaxWidth()
//        )
//    }
//    Spacer(modifier = Modifier.height(20.dp))
//    drawDefaultButton(
//        colorResource(R.color.main_purple),
//        text = "트레이너로 시작하기",
//        onClick = { },
//        isEnabled = true
//    )
//    Spacer(modifier = Modifier.height(15.dp))
//    drawDefaultButton(
//        Color.Black,
//        "일반회원으로 시작하기",
//        isEnabled = true,
//        onClick = { })
//}